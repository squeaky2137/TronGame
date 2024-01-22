package LobbyServer.packets;

import java.io.Serial;
import java.io.Serializable;

public class Connect implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public int id;
}
