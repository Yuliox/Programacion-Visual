package PConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
public class Threading extends Thread {	
	
	@Override
	public void run() {
		for(int i=0;i<10;i++) {
		try {
			
			long tiempoi = System.nanoTime();
			
			Connection connection = null;
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5433/data","postgres","julio130603");
			Statement stmt = connection.createStatement();
			stmt.executeQuery("select * from tabla");
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				int id = rs.getInt(1);
				String descrip = rs.getString(2);
				System.out.println("------------------------");
				System.out.println(id+"\t| "+descrip);
				}
			System.out.println("------------------------");
			stmt.close();
			
			long tiempof = System.nanoTime();
			long tiempototal = tiempof - tiempoi;
			
			System.out.println("Inicio: " +tiempoi/1000000+ "ms y Finalizo: " +tiempof/1000000+ "ms");
			System.out.println("Duro en total: " +tiempototal/1000000+ "ms");
			
			}
		catch(Exception e) {
			e.printStackTrace();			
		}
		}
	}
}