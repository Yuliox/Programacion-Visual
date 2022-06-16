package conexiondelpool;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolManager {

    private Pool pool = Pool.getInstance();

    public synchronized void addConn() {
        if(pool.getAvailableConnections().size() < pool.getMaxPoolSize()) {
            for (int i = 0; i < pool.getGrow(); i ++) {
                pool.createConn();
            }
        }
        else {
            System.out.println("Conexiones Maximas Alcanzadas!");
        }
    }

    public synchronized Connection getConn() {
        Connection connection = null;
        do {
            if (!pool.getAvailableConnections().isEmpty()) {
                connection = pool.getAvailableConnections().get(0);
                pool.addUsedConn(pool.getAvailableConnections().get(0));
                pool.removeAvailableConn(pool.getAvailableConnections().get(0));
                return connection;
            } else {
                try {
                    System.out.println(Thread.currentThread() + " Esperando para una conexion satisfactoria...");
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(pool.getAvailableConnections().size() < pool.getMaxPoolSize()) {
                    addConn();
                }
            }
        }while(connection == null);
        return connection;
    }

    public void returnConn(Connection connection) {
        pool.removeUsedConn(connection);
        pool.addAvailableConn(connection);
    }

    public int getAvailConn() { return pool.getAvailableConnections().size(); }
    public int getTotalConn() { return pool.getAvailableConnections().size() + pool.getUsedConnections().size(); }

    public void resetConn() {
        if (pool.getUsedConnections().isEmpty()) {
            while(pool.getAvailableConnections().size() != pool.getInitialPoolSize()) {
                try {
                    pool.getAvailableConnections().get(pool.getAvailableConnections().size() - 1).close();
                    pool.removeAvailableConn(pool.getAvailableConnections().get(pool.getAvailableConnections().size() - 1));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Reset pool");
        }
        else {
            System.out.println("No se puede resetear la conexion mientras se esta usando.");
        }
    }
}
