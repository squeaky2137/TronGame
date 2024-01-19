package game;

import client.Client;
import client.Connection;
import client.ConnectionHandler;
import game.Path;
import game.Player;
import packets.ChangeDirection;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Base extends JComponent implements Runnable, KeyListener
{
    private boolean W;
    private boolean A;
    private boolean S;
    private boolean D;
    private boolean UP;
    private boolean DOWN;
    private boolean LEFT;
    private boolean RIGHT;

    private Client client;

    public Base(Client client)
    {
        //Instantiate Players
        this.client = client;

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
                for (Integer id : ConnectionHandler.connections.keySet())
                {
                    Connection c = ConnectionHandler.connections.get(id);
                    Player p = c.player;
                    if(p.isAlive()) {
                        graphics2D.setColor(p.getColor());
                        graphics2D.fillRect(p.getX(), p.getY(), 10, 10);
                        for (Path path : p.getPaths()) {
                            graphics2D.fillRect(path.x(), path.y(), 10, 10);
                        }
                    }
                }

                //draw self
                if(client.player.isAlive()) {
                    graphics2D.setColor(client.player.getColor());
                    graphics2D.fillRect(client.player.getX(), client.player.getY(), 10, 10);
                    for (Path path : client.player.getPaths()) {
                        graphics2D.fillRect(path.x(), path.y(), 10, 10);
                    }
                }

            }
        }
    }

    public void run()
    {
        try
        {
            while( true)
            {
                Thread.sleep(50);
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
        if ((e.getKeyCode() == KeyEvent.VK_W) || (e.getKeyCode() == KeyEvent.VK_UP))
        {
            System.out.println("W");
            client.player.setDirection(0);
            ChangeDirection changeDirection = new ChangeDirection();
            changeDirection.direction = 0;
            client.sendObject(changeDirection);
        }
        if ((e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_LEFT))
        {
            System.out.println("A");
            client.player.setDirection(2);
            ChangeDirection changeDirection = new ChangeDirection();
            changeDirection.direction = 2;
            client.sendObject(changeDirection);
        }
        if ((e.getKeyCode() == KeyEvent.VK_S) || (e.getKeyCode() == KeyEvent.VK_DOWN))
        {
            System.out.println("S");
            client.player.setDirection(1);
            ChangeDirection changeDirection = new ChangeDirection();
            changeDirection.direction = 1;
            client.sendObject(changeDirection);
        }
        if ((e.getKeyCode() == KeyEvent.VK_D) || (e.getKeyCode() == KeyEvent.VK_RIGHT))
        {
            System.out.println("D");
            client.player.setDirection(3);
            ChangeDirection changeDirection = new ChangeDirection();
            changeDirection.direction = 3;
            client.sendObject(changeDirection);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}