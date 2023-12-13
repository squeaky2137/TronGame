package client;

import packets.JoinServer;
import packets.LeaveServer;

public class EventHandler {
    public void received(Object p) {
        if(p instanceof JoinServer) {
            JoinServer packet = (JoinServer) p;
            ConnectionHandler.connections.put(packet.id, new Connection(packet.id));
            System.out.println(packet.id + " has connected");
        } else if(p instanceof LeaveServer) {
            LeaveServer packet = (LeaveServer) p;
            System.out.println("User disconnected: " + packet.id);
            ConnectionHandler.connections.remove(packet.id);
        }
    }
}
