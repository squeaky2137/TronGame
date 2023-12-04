import java.awt.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Player
{
    private int x;
    private int y;
    private Color color;
    private ArrayList<Path> paths;
    public Player(Color color, int x, int y)
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.paths = new ArrayList<>();
    }

    public void right()
    {
        paths.add( new Path(x, y) );
        x += 5;
    }

    public void left()
    {
        paths.add( new Path(x, y) );
        x -= 5;
    }

    public void up()
    {
        paths.add( new Path(x, y) );
        y -= 5;
    }

    public void down()
    {
        paths.add( new Path(x, y) );
        y += 5;
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Color getColor()
    {
        return color;
    }

    public ArrayList<Path> getPaths()
    {
        return paths;
    }

    public void paintComponent( Graphics window )
    {
        Graphics2D graphics2D = (Graphics2D) window;
    }
}