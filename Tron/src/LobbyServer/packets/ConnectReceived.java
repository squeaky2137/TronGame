package LobbyServer.packets;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConnectReceived implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public int id;
    public HashMap<String, String> servers;
}
