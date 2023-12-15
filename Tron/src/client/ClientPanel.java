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

    public ClientPanel() {
        super("Tron");

        setSize(WIDTH, HEIGHT);

        //Title Screen
        JLabel title = new JLabel("Tron");
        title.setBounds(400, 100, 50, 50);
        title.setBackground(Color.WHITE);

        ImageIcon image = new ImageIcon("C:\\Users\\adico\\OneDrive\\Documents\\GitHub\\TronGame\\Tron\\maxresdefault.jpg"); // load the image to a imageIcon
        Image scaledImage = image.getImage(); // transform it
        Image newImg = scaledImage.getScaledInstance(800, 765,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        image = new ImageIcon(newImg);  // transform it back

        //BG Label
        JLabel bg = new JLabel();
        bg.setIcon(image);
        bg.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());

        //Create Server Button
        JButton CreateServer = new JButton();
        CreateServer.setText("make server");
        CreateServer.setBounds(100, 100, 100, 100);
        CreateServer.setVisible(true);
        CreateServer.addActionListener(e -> createServer());

        //Connect Server Button
        JButton ConnectToServer = new JButton();
        ConnectToServer.setText("Connect to Server");
        ConnectToServer.setBounds(200, 200, 100, 100);
        ConnectToServer.setVisible(true);
        ConnectToServer.addActionListener(e -> {
            connectToServer();
            Base baseGame = new Base(client);
            add(baseGame);
            remove(CreateServer);
            remove(ConnectToServer);

            JButton startGameButton = new JButton();
            startGameButton.setText("start game");
            startGameButton.setBounds(400, 400, 50, 50);
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

        add(bg);
        add(ConnectToServer);
        add(CreateServer);
        add(title);

        setLayout(null);
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
