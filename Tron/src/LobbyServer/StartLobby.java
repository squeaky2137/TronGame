package LobbyServer;

import LobbyServer.LobbyServer.LobbyServer;

public class StartLobby {
    public static void main(String[] args) {
        LobbyServer server = new LobbyServer(25565);
        server.start();
    }
}
