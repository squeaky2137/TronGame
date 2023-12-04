import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TronRunner extends JFrame
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main( String args[] )
    {
        TronRunner run = new TronRunner();
    }
}