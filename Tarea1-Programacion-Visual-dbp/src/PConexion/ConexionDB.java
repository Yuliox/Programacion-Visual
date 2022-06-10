package PConexion;

public class ConexionDB {
	
	public static void main (String [] args) {
		for(int i=0;i<10;i++) {
			Threading thread1 = new Threading();
			thread1.start();
		}
	}
}