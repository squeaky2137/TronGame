package client;

import game.Player;
import packets.*;

import java.awt.*;

public class EventHandler {
    private final Client client;

    public EventHandler(Client client) {
        this.client = client;
    }

    public void received(Object p) {
        System.out.println(p);
        if(p instanceof JoinServer packet) {
            ConnectionHandler.connections.put(packet.id, new Connection(packet.id, new Player(Color.RED, 0, 0,1 )));
            System.out.println(packet.id + " has connected");
        } else if(p instanceof LeaveServer packet) {
            System.out.println("User disconnected: " + packet.id);
            ConnectionHandler.connections.remove(packet.id);
        } else if(p instanceof PlayerMove packet) {
//            System.out.println(packet.id + " has moved");
            ConnectionHandler.connections.get(packet.id).player.move();
        } else if(p instanceof SelfMove packet) {
            System.out.println(packet.id + " has moved");
            client.player.move();
        } else if(p instanceof ChangeDirection packet) {
            ConnectionHandler.connections.get(packet.id).player.setDirection(packet.direction);
        } else if(p instanceof Death packet) {
            ConnectionHandler.connections.get(packet.id).player.setAlive(false);
        } else if(p instanceof SelfDeath packet) {
            client.player.setAlive(false);
        }
    }
}
