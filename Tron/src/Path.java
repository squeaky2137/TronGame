import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.TreeSet;
import java.util.TreeMap;

public class Path
{
    private Color color;
    private int x;
    private int y;
    public Path(Color color, int x, int y)
    {
        this.color = color;
        this.x = x;
        this.y = y;
    }
}