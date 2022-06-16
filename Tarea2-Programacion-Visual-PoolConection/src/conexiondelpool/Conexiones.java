package conexiondelpool;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Conexiones {
	
	public Conexiones (PoolManager poolManager) {
        this.poolManager = poolManager;
    }

    private PoolManager poolManager;
    private Connection connection;
    private PreparedStatement std;
    private long time = System.currentTimeMillis();

    public void run() {
        try {
            connection = poolManager.getConn();
            std = connection.prepareStatement("SELECT * FROM tabla");
            std.executeQuery();
            System.out.println("Query ejecutada satisfactoriamente en: " + (System.currentTimeMillis() - time) + "ms");
            poolManager.returnConn(connection);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
