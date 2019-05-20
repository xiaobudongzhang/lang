package Jvm.Ref.Phantom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.Connection;

public class PooledConnection implements InvocationHandler{
    private ConnectionPool _pool;
    private Connection  _cxt;

    public PooledConnection(ConnectionPool pool, Connection cxt){
        _pool = pool;
        _cxt = cxt;
    }
    private Connection getConnection()  {
        try
        {
            if ((_cxt == null) || _cxt.isClosed())
                throw new RuntimeException("Connection is closed");
        }
        catch (SQLException ex)
        {
            throw new RuntimeException("unable to determine if underlying connection is open", ex);
        }

        return _cxt;
    }

    public static Connection newInstance(ConnectionPool pool, Connection cxt) {
       return (Connection) Proxy.newProxyInstance(
               PooledConnection.class.getClassLoader(),
               new Class[]{Connection.class},
               new PooledConnection(pool, cxt)
       );
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try
        {
            if (method.getName().equals("close"))
            {
                close();
                return null;
            }
            else if (method.getName().equals("isClosed"))
            {
                return Boolean.valueOf(isClosed());
            }
            else
            {
                return method.invoke(getConnection(), args);
            }
        }
        catch (Throwable ex)
        {
            if (ex instanceof InvocationTargetException)
            {
                ex = ((InvocationTargetException)ex).getTargetException();
            }

            if ((ex instanceof Error) || (ex instanceof RuntimeException) || (ex instanceof SQLException))
            {
                throw ex;
            }

            // it's a checked exception, almost certainly reflection-related;
            // we need to wrap for consumption
            throw new RuntimeException("exception during reflective invocation", ex);
        }
    }

    private void close() throws SQLException {
        if (_cxt != null) {
            System.out.println("call close2");
            _pool.releaseConnection(_cxt);
            _cxt = null;
        }
    }

    private boolean isClosed() throws SQLException {
        return (_cxt == null) || (_cxt.isClosed());
    }
}
