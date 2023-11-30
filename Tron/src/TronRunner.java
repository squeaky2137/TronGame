import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TronRunner extends JFrame implements KeyListener
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private Base b;
    public TronRunner()
    {
        super("Tron");

        setSize(WIDTH, HEIGHT);

        b = new Base();
        add( b);

        setVisible(true);
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main( String args[] )
    {
        TronRunner run = new TronRunner();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            //switch between keys
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}