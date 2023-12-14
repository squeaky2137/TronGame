package client;

import game.Player;

public class Connection {
    public int id;
    public Player player;


    public Connection(int id, Player player) {
        this.id = id;
        this.player = player;
    }
}
