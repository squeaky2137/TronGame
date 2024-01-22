package client;

import game.Player;
import packets.LeaveServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class Client implements Runnable {
    private final String host;
    private final int port;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private boolean running = false;
    private EventHandler listener;
    public JList<String> serverList;
    public ClientPanel clientPanel;


    public int id = -1;

    public Client(String host, int port, JList<String> list) {
        this.host = host;
        this.port = port;
        this.serverList = list;
    }

    public void connect() {
        try {
            socket = new Socket(host,port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            listener = new EventHandler(this);
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
            running = false;
            LeaveServer packet = new LeaveServer();
            sendObject(packet);
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
