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


        JButton ConnectToServer = new JButton();
        ConnectToServer.setText("connect to server");
        ConnectToServer.setSize(100, 100);


        JButton startGameButton = new JButton();
        startGameButton.setText("start game");
        startGameButton.setSize(50, 50);

        add(startGameButton);

        add(CreateServer);
        add(ConnectToServer);
        ConnectToServer.setVisible(true);
        CreateServer.setVisible(true);
        startGameButton.setVisible(false);
        setVisible(true);


        //listeners
        CreateServer.addActionListener(e -> {
            createServer();
            CreateServer.setVisible(false);
        });
        startGameButton.addActionListener(e1 -> {
            startGameButton.setVisible(false);
            StartGame startGame = new StartGame();
            client.sendObject(startGame);
            server.game.start();
        });
        ConnectToServer.addActionListener(e -> {
            connectToServer();
            Base baseGame = new Base(client);
            add(baseGame);
            baseGame.setVisible(true);
            CreateServer.setVisible(false);
            ConnectToServer.setVisible(false);
            startGameButton.setVisible(true);


        });



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
