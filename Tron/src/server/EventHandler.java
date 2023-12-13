package server;

import packets.JoinServer;
import packets.LeaveServer;

public class EventHandler {
    public void received(Object p,Connection connection) {
        if(p instanceof JoinServer) {
            JoinServer packet = (JoinServer) p;
            packet.id = connection.id;
            System.out.println("join packet recieved from " + packet.id);
            for(int i =0;i<ConnectionHandler.connections.size();i++) {
                Connection c = ConnectionHandler.connections.get(i);
                if(c!=connection) {
                    c.sendObject(packet);
                }
            }
        } else if(p instanceof LeaveServer) {
            LeaveServer packet = (LeaveServer) p;
            System.out.println("User disconnected: " + packet.id);
            ConnectionHandler.connections.get(packet.id).close();
            ConnectionHandler.connections.remove(packet.id);
        }
    }
}
