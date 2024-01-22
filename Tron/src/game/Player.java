package game;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Player
{
    private int x;
    private int y;
    private int direction;
    private final Color color;
    private final ArrayList<Path> paths;
    private boolean alive;

    public Player(Color color, int x, int y, int direction )
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.paths = new ArrayList<>();
        this.alive = true;
    }

    public void right()
    {
        //check here
        paths.add( new Path(x, y) );
        x += 10;
    }

    public void left()
    {
        paths.add( new Path(x, y) );
        x -= 10;
    }

    public void up()
    {
        paths.add( new Path(x, y) );
        y -= 10;
    }

    public void down()
    {
        paths.add( new Path(x, y) );
        y += 10;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public boolean isColliding(Player player2)
    {
        for (Path path : player2.getPaths())
        {
            if (path.x() == x && path.y() == y)
            {
                return true;
            }
        }
        return false;
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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void beginMovement() {
        while (alive) {
            move();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Direction:
    0 = up
    1 = down
    2 = left
    3 = right
     */

    public void move() {
        switch (direction) {
            case 0:
                up();
                break;
            case 1:
                down();
                break;
            case 2:
                left();
                break;
            case 3:
                right();
                break;
        }
    }

    public String toString()
    {
        return "(" + x + ", " + y + "): " + color;
    }
}