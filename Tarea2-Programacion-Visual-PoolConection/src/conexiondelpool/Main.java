package conexiondelpool;

public class Main {

    public static void main(String[] args) {
        PoolManager pmanager = new PoolManager();
        Conexiones[] clients = new Conexiones[10000];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Conexiones(pmanager);
            clients[i].run();
        }
        Pool.getInstance().shutdown();
    }
}
