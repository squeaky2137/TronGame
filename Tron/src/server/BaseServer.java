package server;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class BaseServer implements Runnable{
    private final int port;
    private ServerSocket server;
    private int id;
    private boolean started;

    public BaseServer(int port) throws UnknownHostException {
        this.port = port;
        try {
            server = new ServerSocket(0);
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

        if (address.equals("127.0.0.1")) {
            NetworkInterface n = NetworkInterface.getByName("en0");
            Enumeration<InetAddress> addresses = n.getInetAddresses();
            System.out.println(addresses.nextElement().getHostAddress());
            System.out.println(addresses.nextElement().getHostAddress());

        }
    }catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
//        try {
//            System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
//        for (; n.hasMoreElements();)
//        {
//            NetworkInterface e = n.nextElement();
//            System.out.println("Interface: " + e.getName());
//            Enumeration<InetAddress> a = e.getInetAddresses();
//            for (; a.hasMoreElements();)
//            {
//                InetAddress addr = a.nextElement();
//                System.out.println("  " + addr.getHostAddress());
//            }
//        }

        System.out.println("Server started on port: " + server.getLocalPort());
        while(started) {
            try {
                Socket socket = server.accept();
                initSocket(socket);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        shutdown();
    }

    private void initSocket(Socket socket) {
        Connection connection = new Connection(socket,id);
        ConnectionHandler.connections.put(id,connection);
        new Thread(connection).start();
        id++;
    }

    public void shutdown() {
        this.started = false;

        try {
            server.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


}
