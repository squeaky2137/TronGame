import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Base extends JComponent implements Runnable
{
    private Player[] players;
    private int mapsize = 80;
    public Base()
    {
        //Initiate players
        setBackground(Color.WHITE);

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
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(i,j, 80, 80);
                graphics2D.setColor(Color.GRAY);
                graphics2D.setStroke(new BasicStroke(2));
                graphics2D.drawRect(i, j, 80, 80);
            }
        }


    }

    public void run()
    {
        try
        {
            while( true )
            {
                Thread.sleep(50);
                repaint();
            }
        }
        catch( Exception e )
        {

        }
    }
}