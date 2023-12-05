import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import java.awt.event.KeyEvent.*;

public class Base extends JComponent implements Runnable, KeyListener
{
    private ArrayList<Player> players;
    private ArrayList<Path> allPaths;
    private boolean W;
    private boolean A;
    private boolean S;
    private boolean D;
    private boolean UP;
    private boolean DOWN;
    private boolean LEFT;
    private boolean RIGHT;

    private boolean gameRun;

    public Base()
    {
        //Instantiate Players
        players = new ArrayList<>();
        allPaths = new ArrayList<>();
        players.add(new Player(Color.RED, 700, 700));
        players.add(new Player(new Color(0, 255, 255), 100, 100));
        W = false;
        A = false;
        S = false;
        D = false;
        UP = false;
        LEFT = false;
        DOWN = false;
        RIGHT = false;
        gameRun = true;

        //Start Thread
        setBackground(Color.WHITE);
        addKeyListener(this);
        setFocusable(true);
        new Thread(this).start();
    }

    @Override
    public void paintComponent( Graphics window )
    {
        Graphics2D graphics2D = (Graphics2D) window;

        //Draw Background
        for(int i = 0;i<800;i+=80)
        {
            for (int j = 0; j < 800; j+=80)
            {
                //draw Background
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(i,j, 80, 80);
                graphics2D.setColor(Color.GRAY);
                graphics2D.setStroke(new BasicStroke(2));
                graphics2D.drawRect(i, j, 80, 80);

                //draw Players
                for (Player p : players)
                {
                    graphics2D.setColor(p.getColor());
                    graphics2D.fillRect(p.getX(), p.getY(), 10,10);
                    for (Path path : p.getPaths())
                    {
                        graphics2D.fillRect(path.getX(), path.getY(), 10, 10);
                        allPaths.add(new Path(path.getX(), path.getY()));
                    }
                }
            }
        }
    }

    public void run()
    {
        try
        {
            while( gameRun )
            {
                Thread.sleep(50);

                if (W)
                    players.get(0).up();
                if (A)
                    players.get(0).left();
                if (S)
                    players.get(0).down();
                if (D)
                    players.get(0).right();
                if (UP)
                    players.get(1).up();
                if (LEFT)
                    players.get(1).left();
                if (RIGHT)
                    players.get(1).right();
                if (DOWN)
                    players.get(1).down();

                for (Player player : players)
                    for(Path path : allPaths)
                        if (player.getX() == path.getX() && player.getY() == path.getY())
                            gameRun = false;

                repaint();
            }
        }
        catch( Exception e )
        {

        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            W = true;
            A = false;
            S = false;
            D = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            W = false;
            A = true;
            S = false;
            D = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            W = false;
            A = false;
            S = true;
            D = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            W = false;
            A = false;
            S = false;
            D = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            UP = true;
            LEFT = false;
            DOWN = false;
            RIGHT = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            UP = false;
            LEFT = true;
            DOWN = false;
            RIGHT = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            UP = false;
            LEFT = false;
            DOWN = true;
            RIGHT = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            UP = false;
            LEFT = false;
            DOWN = false;
            RIGHT = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}