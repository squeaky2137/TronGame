package packets;

import java.io.Serial;
import java.io.Serializable;

public class StartGame implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public int id;
}