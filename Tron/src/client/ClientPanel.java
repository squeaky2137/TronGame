package client;

import LobbyServer.LobbyClient.LobbyClient;
import LobbyServer.LobbyClient.ServerHandler;
import LobbyServer.packets.Connect;
import LobbyServer.packets.CreateServer;
import LobbyServer.packets.ServerCreated;
import game.Base;
import packets.JoinServer;
import packets.StartGame;
import server.BaseServer;

import javax.swing.*;
import java.awt.*;

public class ClientPanel extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private BaseServer server;
    public static Client client;
    private static JList<String> serverList;
    private JButton connectToServerButton;
    private JTextField playerNameField;
    private JButton createServer;
    private LobbyClient lobbyClient;
    public JLabel bg;
    private JLabel name;
    public Base baseGame;

    public ClientPanel() {
        super("Tron");

        setSize(WIDTH, HEIGHT);

        DefaultListModel<String> serverListModel = new DefaultListModel<>();
        serverList = new JList<>(serverListModel);

        //Connect to the games main server
        lobbyClient = new LobbyClient("localhost", 25565, serverList);
        lobbyClient.connect();
        Connect connectPacket = new Connect();
        lobbyClient.sendObject(connectPacket);

        // Title
        JLabel title = new JLabel("Tron");
        title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
        title.setBounds(350, 25, 100, 30);
        title.setForeground(Color.MAGENTA);

        // Player Name Text Field and Label
        name = new JLabel("Name: ");
        name.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        name.setBounds(255, 525, 100, 30);
        name.setForeground(Color.MAGENTA);
        playerNameField = new JTextField();
        playerNameField.setBounds(340, 525, 200, 30);
        playerNameField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        playerNameField.setBackground(Color.MAGENTA);
        playerNameField.setForeground(Color.WHITE);

        // Background Image
        ImageIcon image = new ImageIcon("/Users/squeaky2137/IdeaProjects/TronGame/Tron/maxresdefault.jpg");
        Image scaledImage = image.getImage().getScaledInstance(800, 765, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        bg = new JLabel(image);
        bg.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        bg.add(title);
        bg.add(name);

        // Make Server Button
        createServer = createStyledButton("Make Server");
        createServer.setBounds(50, 600, 200, 50);
        createServer.addActionListener(e -> createServer());

        // Connect to Server Button
        connectToServerButton = createStyledButton("Connect to Server");
        connectToServerButton.setBounds(300, 600, 200, 50);
        connectToServerButton.setEnabled(false);

        // Scrollable server list
        serverList.setBackground(Color.MAGENTA);
        serverList.setForeground(Color.PINK);
        JScrollPane serverScrollPane = new JScrollPane(serverList);
        serverScrollPane.setBounds(200, 100, 400, 400);

        // Show connect to server button and ActionListener
        serverList.addListSelectionListener(e -> connectToServerButton.setEnabled(!serverList.isSelectionEmpty()));
        connectToServerButton.addActionListener(e -> {
            if (!playerNameField.getText().isEmpty()) {
                int selectedIndex = serverList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedServer = serverList.getModel().getElementAt(selectedIndex);
                    String server = ServerHandler.Servers.get(selectedServer.split("'s server")[0]);
                    String[] parts = server.split(":");
                    int port = Integer.parseInt(parts[1].trim());
                    connectToServer(parts[0], port);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your name before connecting to the server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        baseGame = new Base();
        baseGame.setBounds(0, 0, 800, 800);




        // Add everything

        add(bg);
        add(baseGame);
        bg.add(connectToServerButton);
        bg.add(createServer);
        bg.add(serverScrollPane);
        bg.add(playerNameField);
        baseGame.setVisible(true);

        serverScrollPane.setVisible(true);
        connectToServerButton.setVisible(true);
        baseGame.setVisible(false);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        button.setForeground(Color.PINK);
        button.setBackground(Color.MAGENTA);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ClientPanel());
    }

    public void createServer() {
        if (playerNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name before connecting to the server.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            server = new BaseServer();
            server.start();
//            ((DefaultListModel<String>) serverList.getModel()).addElement("Server at localhost:6969");
            createServer.setVisible(false);
            CreateServer createServerPacket = new CreateServer();
            createServerPacket.name = playerNameField.getText();
            createServerPacket.ip = server.server.getInetAddress().getHostAddress() + ":" + server.server.getLocalPort();
            lobbyClient.sendObject(createServerPacket);

            connectToServer("localhost", server.server.getLocalPort());
            JButton startGameButton = createStyledButton("Start Game");
            startGameButton.setBounds(300, 600, 200, 50);
            startGameButton.addActionListener(e1 -> {
                startGameButton.setVisible(false);
                baseGame.setVisible(true);
                bg.setVisible(false);

                StartGame startGame = new StartGame();
                client.sendObject(startGame);
                server.game.start();
            });
            bg.add(startGameButton);
        }
    }

    public void connectToServer(String ip, int port) {
            client = new Client(ip, port, serverList);
            client.connect();
            client.clientPanel = this;
            JoinServer joinPacket = new JoinServer();
            joinPacket.name = playerNameField.getText();
            client.sendObject(joinPacket);
            baseGame.client = client;
        serverList.setModel(new DefaultListModel<>());
        ((DefaultListModel<String>) serverList.getModel()).addElement(playerNameField.getText());
        createServer.setVisible(false);
        connectToServerButton.setVisible(false);
        playerNameField.setVisible(false);
        bg.remove(name);
    }
}

