package server;

import game.Player;
import packets.PlayerMove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;


public class Connection implements Runnable {

    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public int id;
    private EventHandler listener;
    private boolean running = false;
    public Player player;

    public Connection(Socket socket, int id, BaseServer server) {
        this.socket = socket;
        this.id = id;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            listener = new EventHandler(server);
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
                    listener.received(data, this);
                }catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            running = false;
            in.close();
            out.close();
            socket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object packet) {
//        System.out.println("Sending packet to " + id);
//        System.out.println(packet);
        try {
            System.out.println("Sending packet to " + id);
            out.writeObject(packet);
            out.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
