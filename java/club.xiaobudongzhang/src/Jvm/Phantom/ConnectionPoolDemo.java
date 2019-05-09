// Copyright (c) 2007 Keith D Gregory
package Jvm.Phantom;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 *  Demonstrates the connection pool, using an in-memory HSQL database,
 *  and a single main thread that requests connections and lets them
 *  go out of scope without explicitly closing. The pool will log when
 *  the connections are returned.
 *  <p>
 *  You will need HSQL in your classpath to run this example (or you
 *  can replace the connection information with your favority DBMS).
 *  It's open source: http://hsqldb.org/
 */
public class ConnectionPoolDemo
{


    public static void main(String[] argv) throws Exception
    {
        IdentityHashMap<Connection,Object> _cxt2Ref = new IdentityHashMap<Connection,Object>();

        ConnectionPool pool = new ConnectionPool(
                                "org.hsqldb.jdbcDriver",
                                "jdbc:hsqldb:mem:aname", "sa", "6666",
                                5);

        Connection cxt = null;
        for (int ii = 0 ; ii < 100000 ; ii++)
        {
            // this assignment leaves the previously-allocated connection 
            // available for collection; we get metadata to verify that
            // the connection is valid
            cxt = pool.getConnection();
            cxt.getMetaData();
            attemptGC();
        }
        System.out.println("end");
    }

    private static void attemptGC()
    {
        // in my experience, it's not enough to call System.gc(); allocating
        // chunks of memory makes it actually do some works
        ArrayList<byte[]> foo = new ArrayList<byte[]>();
        for (int ii = 0 ; ii < 10000 ; ii++)
            foo.add(new byte[10240]);
        System.gc();
    }
}
