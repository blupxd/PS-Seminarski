package threads;

import controllers.OperationHandler;
import controllers.ServerController;
import operations.Operation;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final OperationHandler handler;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.handler = new OperationHandler();
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String clientAddr = socket.getRemoteSocketAddress().toString();
        ServerController.getInstance().dodajVezu(clientAddr);
        try {
            while (!socket.isClosed()) {
                Operation op = (Operation) in.readObject();
                ServerController.getInstance().log("Operacija [" + clientAddr + "]: " + op.getType());
                Operation result = handler.execute(op);
                out.writeObject(result);
                out.flush();
                out.reset();
            }
        } catch (Exception e) {
            // klijent se diskonektovao - normalno
        } finally {
            ServerController.getInstance().ukloniVezu(clientAddr);
            ugasi();
        }
    }

    public void ugasi() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
