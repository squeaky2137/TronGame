package LobbyServer.packets;

import java.io.Serial;
import java.io.Serializable;

public class ServerCreated implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public String ip;
    public String name;
}
