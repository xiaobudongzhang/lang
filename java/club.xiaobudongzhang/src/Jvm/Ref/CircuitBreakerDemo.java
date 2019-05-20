// Copyright (c) 2007 Keith D Gregory
package Jvm.Ref;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *  Demonstrates how a <code>SoftReference</code> can be used as a circuit breaker
 *  for out-of-memory conditions. Implements a function that retrieves data from a
 *  <code>ResultSet</code>, storing it in a <code>List<List<Object>></code> that
 *  is accessed through a soft reference. The demo code creates a stub result set
 *  that will always return data, meaning that the reference will eventually be
 *  cleared.
 */
public class CircuitBreakerDemo
{
    public static void main(String[] argv)
    throws Exception
    {
        ResultSet rslt = (ResultSet)Proxy.newProxyInstance(
                                ResultSet.class.getClassLoader(),
                                new Class[] {ResultSet.class},
                                new FauxDatabaseHandler());

         List<List<Object>> data = processResults(rslt);
         //List<List<Object>> data = processResultsWithCircuitBreaker(rslt);
         if (data == null) System.out.println("data is null");
         else System.out.println("returned data has " + data.size() + " rows");
    }


    /**
     *  This method processes all rows from a ResultSet, maintaining strong
     *  references to the list. Eventually it will throw OutOfMemoryError.
     */
    public static List<List<Object>> processResults(ResultSet rslt)
    throws SQLException
    {
        try
        {
            List<List<Object>> results = new LinkedList<List<Object>>();
            ResultSetMetaData meta = rslt.getMetaData();
            int colCount = meta.getColumnCount();

            while (rslt.next())
            {
                List<Object> row = new ArrayList<Object>(colCount);
                for (int ii = 1 ; ii <= colCount ; ii++)
                    row.add(rslt.getObject(ii));

                results.add(row);
            }

            return results;
        }
        finally
        {
            closeQuietly(rslt);
        }
    }


    /**
     *  This method processes all rows from a ResultSet, throwing a local
     *  exception if it runs out of memory.
     */
    public static List<List<Object>> processResultsWithCircuitBreaker(ResultSet rslt)
    throws SQLException
    {
        try
        {
            ResultSetMetaData meta = rslt.getMetaData();
            int colCount = meta.getColumnCount();
            int rowCount = 0;

            SoftReference<List<List<Object>>> ref
                = new SoftReference<List<List<Object>>>(new LinkedList<List<Object>>());

            while (rslt.next())
            {
                rowCount++;
                List<Object> row = new ArrayList<Object>(colCount);
                for (int ii = 1 ; ii <= colCount ; ii++)
                    row.add(rslt.getObject(ii));

                List<List<Object>> results = ref.get();
                if (results == null)
                    throw new TooManyResultsException(rowCount);
                else
                    results.add(row);
                results = null;
            }

            return ref.get();
        }
        finally
        {
            closeQuietly(rslt);
        }
    }


    /**
     *  Every DB utility package should contain versions of this method for
     *  each of the database objects it's likely to close. If you don't
     *  want to write it yourself, look at Jakarta Commons DbUtils.
     */
    public static void closeQuietly(ResultSet rslt)
    {
        try
        {
            rslt.close();
        }
        catch (SQLException ignored)
        {
            // you could add logging here if you want to track the exception
        }
    }


    /**
     *  We throw this exeption when we run out of memory while processing results.
     */
    public static class TooManyResultsException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public TooManyResultsException(int rowCount)
        {
            super("failed after " + rowCount + " rows");
        }
    }


    /**
     *  Proxy handler for all of the database objects used by this program.
     */
    private static class FauxDatabaseHandler
    implements InvocationHandler
    {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            if (method.getName().equals("getMetaData"))
            {
                return Proxy.newProxyInstance(
                                ResultSetMetaData.class.getClassLoader(),
                                new Class[] {ResultSetMetaData.class},
                                this);
            }
            else if (method.getName().equals("getColumnCount"))
            {
                return Integer.valueOf(5);
            }
            else if (method.getName().equals("next"))
            {
                return Boolean.TRUE;
            }
            else if (method.getName().equals("getObject"))
            {
                return new byte[4096];
            }
            else if (method.getName().equals("close"))
            {
                return null;
            }
            else
            {
                throw new RuntimeException("unexpected method: " + method.getName());
            }
        }
    }
}
