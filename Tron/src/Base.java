import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import java.awt.event.KeyEvent.*;

public class Base extends JComponent implements Runnable, KeyListener
{
    private final ArrayList<Player> players;
    private boolean W;
    private boolean A;
    private boolean S;
    private boolean D;
    private boolean UP;
    private boolean DOWN;
    private boolean LEFT;
    private boolean RIGHT;

    public Base()
    {
        //Instantiate Players
        players = new ArrayList<>();
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

        for(int i = 0;i<800;i+=80)
        {
            for (int j = 0; j < 800; j+=80)
            {
                //Draw Background
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(i,j, 80, 80);
                graphics2D.setColor(Color.GRAY);
                graphics2D.setStroke(new BasicStroke(2));
                graphics2D.drawRect(i, j, 80, 80);

                //draw Players and their Paths
                for (Player p : players)
                {
                    graphics2D.setColor(p.getColor());
                    graphics2D.fillRect(p.getX(), p.getY(), 10,10);
                    for (Path path : p.getPaths())
                    {
                        graphics2D.fillRect(path.getX(), path.getY(), 10, 10);
                    }
                }
            }
        }
    }

    public void run()
    {
        try
        {
            while( players.size() > 1 )
            {
                Thread.sleep(50);

                //Only supports 2 players currently
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

                for (int i = players.size()-1; i >= 0; i--)
                {
                    //Check Bounds
                    if ( !bounds(players.get(i)) )
                        players.remove(i);
                    else
                    {
                        //Check if any Player is on a Path
                        for (int j = players.size()-1; j >= 0; j--) {
                            if (!players.get(i).isAlive(players.get(j))) {
                                players.remove(i);
                                continue;
                            }
                        }
                    }
                }
                repaint();
            }
        }
        catch( Exception e )
        {

        }
    }

    public boolean bounds(Player p)
    {
        return p.getX() < 800 && p.getX() >= 0 && p.getY() < 800 && p.getY() >= 0;
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