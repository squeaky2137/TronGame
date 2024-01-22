package packets;

import server.Connection;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class JoinAccepted implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public int id;
    public HashMap<Integer, String> players;
}
