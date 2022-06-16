package conexiondelpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pool {

    private static class PoolSingletonHelper {
        private static final Pool INSTANCE = new Pool();
    }
    public static Pool getInstance() { return PoolSingletonHelper.INSTANCE; }

    private List<Connection> availableConnections = new ArrayList<>();
    private List<Connection> usedConnections = new ArrayList<>();
    private static int maxPoolSize = 10000;
    private static int grow = 10;
    private static int initialPoolSize = 100;
    private String url = "jdbc:postgresql://localhost:5433/data";
    private String user = "postgres";
    private String pass = "julio130603";
    private Connection connection;

    public Pool() {
        for (int i = 0; i <= initialPoolSize; i++) {
            availableConnections.add(createConnection(url, user, pass));
        }
    }

    private Connection createConnection(String url, String user, String pass) {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Conxion " + (availableConnections.size() + 1) + " Creada");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void shutdown() {
        for(int i = 0; i < availableConnections.size(); i++) {
            try {
                System.out.println("Conexion " + (i + 1) + " Cerrada");
                availableConnections.get(i).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Connection> getAvailableConnections() { return availableConnections; }
    public List<Connection> getUsedConnections() { return usedConnections; }
    public int getMaxPoolSize() { return maxPoolSize; }
    public int getGrow() { return grow; }
    public int getInitialPoolSize() { return initialPoolSize; }

    public void addAvailableConn(Connection connection) { availableConnections.add(connection); }
    public void addUsedConn(Connection connection) { usedConnections.add(connection); }
    public void removeAvailableConn(Connection connection) { availableConnections.remove(connection); }
    public void removeUsedConn(Connection connection) { usedConnections.remove(connection); }
    public void createConn() { availableConnections.add(createConnection(url, user, pass)); }

}