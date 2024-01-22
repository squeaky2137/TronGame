package server;

import game.Player;
import packets.*;

import java.awt.*;
import java.util.HashMap;

public class EventHandler {

    private final BaseServer server;

    public EventHandler(BaseServer server) {
        this.server = server;
    }


    public void received(Object p,Connection connection) {
        if(p instanceof JoinServer packet) {
            packet.id = connection.id;

            System.out.println("SERVER ->> join packet received from " + packet.name + "(" + connection.id + ")");
            connection.name = packet.name;
            JoinAccepted accepted = new JoinAccepted();
            accepted.id = connection.id;
            HashMap<Integer, String> players = new HashMap<>();
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                players.put(c.id, c.name);
            }
            accepted.players = players;
            connection.sendObject(accepted);

            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                if(c!=connection) {
                    c.sendObject(packet);
                }
            }
        } else if(p instanceof LeaveServer packet) {
            System.out.println("SERVER ->> User disconnected: " + packet.id);
            ConnectionHandler.connections.get(packet.id).close();
            ConnectionHandler.connections.remove(packet.id);
        } else if(p instanceof ChangeDirection packet) {
            System.out.println("SERVER ->> direction changed for " + packet.id);
            LeaveServer leaveServer = new LeaveServer();
            connection.sendObject(leaveServer);
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                System.out.println("SERVER ->> sending direction change to " + i);
                Connection c = ConnectionHandler.connections.get(i);
                c.sendObject(packet);
            }
        } else if(p instanceof StartGame packet) {
            if(connection.id!=0) return;
            System.out.println("SERVER ->> Game started");
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                if(i == 0)
                    c.player = new Player(Color.RED, 400,50, 1);
                else if(i == 1)
                    c.player = new Player(Color.BLUE, 50, 400, 3);
                else if(i == 2)
                    c.player = new Player(Color.GREEN, 750, 400, 2);
                else if(i == 3)
                    c.player = new Player(Color.YELLOW, 400, 750, 0);
                c.sendObject(packet);
            }
            server.game.start();
        }
    }
}
