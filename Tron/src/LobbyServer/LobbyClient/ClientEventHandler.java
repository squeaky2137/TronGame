package LobbyServer.LobbyClient;

import LobbyServer.packets.ConnectReceived;
import LobbyServer.packets.ServerCreated;
import client.Client;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;

public class ClientEventHandler {

    private JList<String> serverList;

    public ClientEventHandler(JList<String> list) {
        this.serverList = list;
    }

    public void received(Object p) {
//        System.out.println(client.id + " received " + p);
        if(p instanceof ConnectReceived packet) {
            System.out.println("LOBBYCLIENT ->> Connection accepted");
            DefaultListModel<String> model = (DefaultListModel<String>) serverList.getModel();
            for(Map.Entry<String, String> entry : packet.servers.entrySet()) {
                model.addElement(entry.getKey() + "'s server");
                ServerHandler.Servers.put(entry.getKey(), entry.getValue());
            }
            serverList.setModel(model);
        } else if (p instanceof ServerCreated) {
            System.out.println("LOBBYCLIENT ->> Server created");
            ServerCreated packet2 = (ServerCreated) p;
            DefaultListModel<String> model = (DefaultListModel<String>) serverList.getModel();
            model.addElement(packet2.name + "'s server");
            serverList.setModel(model);
            ServerHandler.Servers.put(packet2.name, packet2.ip);
        } else {
            System.out.println("LOBBYCLIENT ->> Unknown packet received");
        }
    }
}
