package client;

import game.Player;
import packets.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EventHandler {
    private final Client client;

    public EventHandler(Client client) {
        this.client = client;
    }

    public void received(Object p) {
        if(p instanceof JoinServer packet) {
            ConnectionHandler.connections.put(packet.id, new Connection(packet.id, new Player(Color.RED, 0, 0,1 ), packet.name));
            System.out.println("CLIENT ->> " + packet.name + "(" + packet.id + ")" + " has connected");
            DefaultListModel<String> model = (DefaultListModel<String>) client.serverList.getModel();
            model.addElement(packet.name);
            client.serverList.setModel(model);
        } else if(p instanceof LeaveServer packet) {
            System.out.println("CLIENT ->> User disconnected: " + packet.id);
            ConnectionHandler.connections.remove(packet.id);
        } else if(p instanceof ChangeDirection packet) {
            System.out.println("CLIENT ->> direction changed for " + packet.id);
            if(ConnectionHandler.connections.get(packet.id)!=null)
                ConnectionHandler.connections.get(packet.id).player.setDirection(packet.direction);
        } else if(p instanceof Death packet) {
            ConnectionHandler.connections.get(packet.id).player.setAlive(false);
        } else if(p instanceof JoinAccepted packet) {

            System.out.println("CLIENT ->> Join accepted");
            client.id = packet.id;
            DefaultListModel<String> model = (DefaultListModel<String>) client.serverList.getModel();
            for(Map.Entry<Integer, String> entry : packet.players.entrySet()) {
                String name = entry.getValue();
                ConnectionHandler.connections.put(entry.getKey(), new Connection(entry.getKey(), new Player(Color.RED, 0, 0,1 ), name));
                model.addElement(name);
            }
            client.serverList.setModel(model);
        } else if(p instanceof StartGame packet) {
            client.clientPanel.baseGame.setVisible(true);
            client.clientPanel.bg.setVisible(false);
            System.out.println("CLIENT ->> Game has started");
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                if(c==null) continue;
                if(i == 0)
                    c.player = new Player(Color.RED, 400,50, 1);
                else if(i == 1)
                    c.player = new Player(Color.BLUE, 50, 400, 3);
                else if(i == 2)
                    c.player = new Player(Color.GREEN, 750, 400, 2);
                else if(i == 3)
                    c.player = new Player(Color.YELLOW, 400, 750, 0);
                c.player.beginMovement();
            }

        }
    }
}
