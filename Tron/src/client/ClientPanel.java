package client;

import game.Base;
import packets.JoinServer;
import packets.StartGame;
import server.BaseServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPanel extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private BaseServer server;
    public static Client client;
    private JList<String> serverList;
    private JButton connectToServerButton;
    public ClientPanel() {
        super("Tron");

        setSize(WIDTH, HEIGHT);

        // Title
        JLabel title = new JLabel("Tron");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBounds(350, 50, 100, 50);
        title.setForeground(Color.MAGENTA);

        // Background Image
        ImageIcon image = new ImageIcon("maxresdefault.jpg");
        Image scaledImage = image.getImage().getScaledInstance(800, 765, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        JLabel bg = new JLabel(image);
        bg.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        bg.add(title);

        // Create Server Button
        JButton createServer = createStyledButton("Make Server");
        createServer.setBounds(50, 600, 200, 50);
        createServer.addActionListener(e -> createServer());

        // Connect Server Button
        connectToServerButton = createStyledButton("Connect to Server");
        connectToServerButton.setBounds(300, 600, 200, 50);
        connectToServerButton.setEnabled(false);

        // Scrollable server list
        DefaultListModel<String> serverListModel = new DefaultListModel<>();
        serverList = new JList<>(serverListModel);
        serverList.setBackground(Color.PINK);
        serverList.setForeground(Color.WHITE);
        JScrollPane serverScrollPane = new JScrollPane(serverList);
        serverScrollPane.setBounds(550, 100, 200, 150);

        serverList.addListSelectionListener(e -> connectToServerButton.setEnabled(!serverList.isSelectionEmpty()));
        connectToServerButton.addActionListener(e -> {
            connectToServer();
            Base baseGame = new Base(client);
            add(baseGame);
            createServer.setVisible(false);
            connectToServerButton.setVisible(false);
            serverScrollPane.setVisible(false);

            JButton startGameButton = createStyledButton("Start Game");
            startGameButton.setBounds(550, 600, 200, 50);
            startGameButton.addActionListener(e1 -> {
                startGameButton.setVisible(false);
                StartGame startGame = new StartGame();
                client.sendObject(startGame);
                server.game.start();
                repaint();
            });
            add(startGameButton);
            repaint();
        });

        // Add everything
        add(bg);
        add(connectToServerButton);
        add(createServer);
        add(serverScrollPane);
        serverScrollPane.setVisible(true);
        connectToServerButton.setVisible(true);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.PINK);
        button.setBackground(Color.MAGENTA);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientPanel());
    }

    public void createServer() {
        server = new BaseServer(6969);
        server.start();
        ((DefaultListModel<String>) serverList.getModel()).addElement("Server at localhost:6969");
    }

    public void connectToServer() {
        int selectedIndex = serverList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedServer = serverList.getModel().getElementAt(selectedIndex);
            String[] parts = selectedServer.split(":");
            int port = Integer.parseInt(parts[1].trim());
            client = new Client("localhost", port);
            client.connect();

            JoinServer joinPacket = new JoinServer();
            client.sendObject(joinPacket);
        }
    }
}

