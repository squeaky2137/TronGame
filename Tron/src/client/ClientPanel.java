package client;

import game.Base;
import packets.JoinServer;
import packets.StartGame;
import server.BaseServer;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientPanel extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private BaseServer server;
    public static Client client;

    public ClientPanel()
    {
        super("Tron");

        setSize(WIDTH, HEIGHT);

        JButton CreateServer = new JButton();
        CreateServer.setText("make server");
        CreateServer.setSize(100, 100);
        CreateServer.setVisible(true);
        CreateServer.addActionListener(e -> createServer());

        JButton ConnectToServer = new JButton();
        ConnectToServer.setText("connect to server");
        ConnectToServer.setSize(100, 100);
        ConnectToServer.setVisible(true);
        ConnectToServer.addActionListener(e -> {
            connectToServer();
            Base baseGame = new Base(client);
            add(baseGame);
            remove(CreateServer);
            remove(ConnectToServer);

            JButton startGameButton = new JButton();
            startGameButton.setText("start game");
            startGameButton.setSize(50, 50);
            startGameButton.setVisible(true);
            startGameButton.addActionListener(e1 -> {
                remove(startGameButton);
                StartGame startGame = new StartGame();
                client.sendObject(startGame);
                server.game.start();
                repaint();
            });
            add(startGameButton);
            repaint();

        });

        add(CreateServer);
        add(ConnectToServer);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ClientPanel run = new ClientPanel();
    }

    public void createServer() {
        server = new BaseServer(6969);
        server.start();

    }

    public void connectToServer() {
        client = new Client("localhost", 6969);
        client.connect();

        JoinServer joinPacket = new JoinServer();
        client.sendObject(joinPacket);
    }
}
