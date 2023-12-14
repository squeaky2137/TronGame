package server;

import game.Player;
import packets.ChangeDirection;
import packets.JoinServer;
import packets.LeaveServer;
import packets.StartGame;

import java.awt.*;

public class EventHandler {

    private BaseServer server;

    public EventHandler(BaseServer server) {
        this.server = server;
    }


    public void received(Object p,Connection connection) {
        if(p instanceof JoinServer packet) {
            packet.id = connection.id;

            System.out.println("join packet recieved from " + connection.id);
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                if(c!=connection) {
                    c.sendObject(packet);
                }
            }
        } else if(p instanceof LeaveServer packet) {
            System.out.println("User disconnected: " + packet.id);
            ConnectionHandler.connections.get(packet.id).close();
            ConnectionHandler.connections.remove(packet.id);
        } else if(p instanceof ChangeDirection packet) {
            System.out.println("direction changed for " + packet.id);
            Connection user = ConnectionHandler.connections.get(packet.id);
            user.player.setDirection(packet.direction);
            server.sendToAllExcept(packet, packet.id);
        } else if(p instanceof StartGame packet) {
            System.out.println("Game started");
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                c.player = new Player(Color.RED,0,0, 1);
                c.player.setDirection(1);
            }
            server.game.start();
        }
    }
}
