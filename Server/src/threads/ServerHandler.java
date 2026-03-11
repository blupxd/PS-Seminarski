package threads;

import controllers.ServerController;
import dbb.DBBroker;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler extends Thread {

    private ServerSocket serverSocket;
    private final int port;
    private final String dbUrl;
    public static List<ClientHandler> clients;

    public ServerHandler(int port, String dbUrl) {
        this.port = port;
        this.dbUrl = dbUrl;
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            DBBroker.getInstance().connect(dbUrl);
            ServerController.getInstance().log("Konekcija sa bazom uspostavljena.");
            serverSocket = new ServerSocket(port);
            ServerController.getInstance().log("Server pokrenut na portu: " + port);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler ch = new ClientHandler(socket);
                clients.add(ch);
                ch.start();
            }
        } catch (Exception e) {
            if (serverSocket == null || !serverSocket.isClosed()) {
                ServerController.getInstance().log("Greška servera: " + e.getMessage());
            }
        }
    }

    public void ugasi() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (clients != null) {
                for (ClientHandler ch : new ArrayList<>(clients)) {
                    ch.ugasi();
                }
                clients.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DBBroker.getInstance().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
