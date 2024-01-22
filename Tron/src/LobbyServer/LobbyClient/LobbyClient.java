package LobbyServer.LobbyClient;

import client.ClientPanel;
import game.Player;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class LobbyClient implements Runnable {
    private final String host;
    private final int port;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private boolean running = false;
    private ClientEventHandler listener;
    private JList<String> serverList;

    public Player player;
    public int id = -1;

    public LobbyClient(String host, int port, JList<String> list) {
        this.host = host;
        this.port = port;
        this.serverList = list;
    }

    public void connect() {
        try {
            socket = new Socket(host,port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            listener = new ClientEventHandler(serverList);
            new Thread(this).start();
        }catch(ConnectException e) {
            System.out.println("Unable to connect to the server");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            System.out.println("Closing client");
            in.close();
            out.close();
            socket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object packet) {
        try {
            out.writeObject(packet);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            running = true;
            while(running) {
                try {
                    Object data = in.readObject();
                    listener.received(data);
                }catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }catch(SocketException e) {
                    System.out.println("Server has closed");
                    System.out.println(e);
                    close();
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
