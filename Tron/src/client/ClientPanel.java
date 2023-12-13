package client;

import packets.JoinServer;
import server.BaseServer;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientPanel extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    public ClientPanel()
    {
        super("Tron");

        setSize(WIDTH, HEIGHT);

        JButton button = new JButton();
        button.setText("make server");
        button.setSize(100, 100);
        button.setVisible(true);
        button.addActionListener(e -> {
            //
            Client client = new Client("localhost", 6969);
            client.connect();

            JoinServer joinPacket = new JoinServer();
            client.sendObject(joinPacket);


        });
        add(button);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws UnknownHostException {
        BaseServer server = new BaseServer(6969);
        server.start();
        ClientPanel run = new ClientPanel();
    }
}
