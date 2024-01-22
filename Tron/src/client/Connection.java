package client;

import game.Player;

public class Connection {
    public int id;
    public Player player;
    public String name;


    public Connection(int id, Player player, String name) {
        this.id = id;
        this.player = player;
        this.name = name;
    }
}
