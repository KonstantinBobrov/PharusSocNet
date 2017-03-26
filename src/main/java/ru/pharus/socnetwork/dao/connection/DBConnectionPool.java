package ru.pharus.socnetwork.dao.connection;

import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.cache.DaoCache;
import ru.pharus.socnetwork.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBConnectionPool {
    private static int maxConn;
    static private List<Connection> freeConnections = new ArrayList<>();
    static volatile private DBConnectionPool instance;

    private DBConnectionPool(int poolSize) {
        maxConn = poolSize;
    }

    public static DBConnectionPool getInstance(int poolSize) {
        DBConnectionPool daoINSTANCE = instance;
        if (instance == null) {
            synchronized (DBConnectionPool.class) {
                daoINSTANCE = instance;
                if (daoINSTANCE == null) {
                    instance = daoINSTANCE = new DBConnectionPool(poolSize);
                }
            }
        }
        return daoINSTANCE;
    }

    public Connection getConnection() throws DAOException {
        Connection con = null;
        synchronized (this) {
            if (!freeConnections.isEmpty()) {
                con = freeConnections.get(freeConnections.size() - 1);
                freeConnections.remove(con);
                con = getConnection();
            } else {
                con = newConnection();
            }
        }
        return con;
    }

    private Connection newConnection() throws DAOException{
        return DaoFactory.getInstance().getConnection();
    }

    public void freeConnections(Connection con) {
        synchronized (this) {
            if ((con != null) && (freeConnections.size() <= maxConn)) {
                freeConnections.add(con);
            }
        }
    }

    public void released() {
        synchronized (this) {
            Iterator allConnections = freeConnections.iterator();
            while (allConnections.hasNext()) {
                Connection con = (Connection) allConnections.next();
                try {
                    con.close();
                    System.out.println("Closed connection for pool");
                } catch (SQLException e) {
                    System.out.println("Can't close connection for pool");
                }
            }
            freeConnections.clear();
        }
    }
}