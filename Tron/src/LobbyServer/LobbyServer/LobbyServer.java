package LobbyServer.LobbyServer;

import game.Game;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class LobbyServer implements Runnable {
    private final int port;
    private ServerSocket server;
    private int id;
    private boolean started;
    public Game game;

    public LobbyServer(int port) {
        this.port = port;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        started = true;
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
            System.out.println(address);
            if (address.equals("127.0.0.1")) {
                NetworkInterface n = NetworkInterface.getByName("en0");
                Enumeration<InetAddress> addresses = n.getInetAddresses();
                System.out.println(addresses.nextElement().getHostAddress());
                System.out.println(addresses.nextElement().getHostAddress());
            }
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Server started on port: " + server.getLocalPort());
        while (started) {
            try {
                Socket socket = server.accept();
                initSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        shutdown();
    }

    private void initSocket(Socket socket) {
        System.out.println("LOBBY ->> New connection from: " + socket.getInetAddress());
        Connection connection = new Connection(socket, id, this);
        ConnectionHandler.connections.put(id, connection);
        new Thread(connection).start();
        id++;
    }

    public void shutdown() {
        this.started = false;

        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(Object o) {
        for (Connection c : ConnectionHandler.connections.values()) {
            c.sendObject(o);
        }
    }

    public void sendToAllExcept(Object o, int id) {
        for (Connection c : ConnectionHandler.connections.values()) {
            if (c.id != id) {
                c.sendObject(o);
            }
        }
    }


}
