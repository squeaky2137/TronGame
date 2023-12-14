package packets;

import game.Player;

import java.io.Serializable;

public class SelfDeath implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public boolean alive;
}
