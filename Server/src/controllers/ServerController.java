package controllers;

import threads.ServerHandler;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerController {

    public interface ServerListener {

        void onLog(String poruka);

        void onClientConnected(String adresa);

        void onClientDisconnected(String adresa);
    }

    private static ServerController instance;
    private ServerHandler serverHandler;

    private ServerListener listener;
    private final List<String> aktivneVeze = Collections.synchronizedList(new ArrayList<>());

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public void setListener(ServerListener listener) {
        this.listener = listener;
    }

    public void startAsync(int port, String dbUrl) {
        serverHandler = new ServerHandler(port, dbUrl);
        serverHandler.start();
    }

    public void stop() {
        if (serverHandler != null) {
            serverHandler.ugasi();
        }
        log("Server zaustavljen.");
    }

    public boolean isRunning() {
        return serverHandler != null && serverHandler.isAlive();
    }

    public void dodajVezu(String adresa) {
        aktivneVeze.add(adresa);
        log("Klijent se povezao: " + adresa);
        if (listener != null) {
            listener.onClientConnected(adresa);
        }
    }

    public void ukloniVezu(String adresa) {
        aktivneVeze.remove(adresa);
        log("Klijent prekinuo vezu: " + adresa);
        if (listener != null) {
            listener.onClientDisconnected(adresa);
        }
    }

    public List<String> getAktivneVeze() {
        return new ArrayList<>(aktivneVeze);
    }

    public void log(String poruka) {
        String linija = "[" + LocalTime.now().toString().substring(0, 8) + "] " + poruka;
        System.out.println(linija);
        if (listener != null) {
            listener.onLog(linija);
        }
    }
}
