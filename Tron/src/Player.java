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
        x += 1;
    }

    public void left()
    {
      x -= 1;
    }

    public void up()
    {
        y -= 1;
    }

    public void down()
    {
        y += 1;
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void paintComponent( Graphics window )
    {
        Graphics2D graphics2D = (Graphics2D) window;
    }
}