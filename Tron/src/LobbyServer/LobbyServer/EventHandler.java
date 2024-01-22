package LobbyServer.LobbyServer;

import LobbyServer.LobbyServer.Connection;
import LobbyServer.LobbyServer.ConnectionHandler;
import LobbyServer.LobbyServer.LobbyServer;
import LobbyServer.LobbyServer.ServerHandler;
import LobbyServer.packets.Connect;
import LobbyServer.packets.ConnectReceived;
import LobbyServer.packets.CreateServer;
import LobbyServer.packets.ServerCreated;

public class EventHandler {

    private final LobbyServer server;

    public EventHandler(LobbyServer server) {
        this.server = server;
    }


    public void received(Object p, Connection connection) {
        if(p instanceof Connect packet) {
            packet.id = connection.id;

            System.out.println("LOBBY ->> join packet received from " + connection.id);

            ConnectReceived packet2 = new ConnectReceived();
            packet2.id = connection.id;
            packet2.servers = ServerHandler.Servers;
            connection.sendObject(packet2);
        } else if(p instanceof CreateServer packet) {
            System.out.println("LOBBY ->> Create server packet received from " + connection.id + " with name " + packet.name + " and ip " + packet.ip);
            ServerHandler.Servers.put(packet.name, packet.ip);
            System.out.println(ServerHandler.Servers);
            ServerCreated packet2 = new ServerCreated();
            packet2.name = packet.name;
            packet2.ip = packet.ip;
            for(int i = 0; i< ConnectionHandler.connections.size(); i++) {
                if(connection.id == i) continue;
                Connection c = ConnectionHandler.connections.get(i);
                c.sendObject(packet2);
            }
        } else {
            System.out.println("LOBBY ->> Unknown packet received");
        }
    }
}
