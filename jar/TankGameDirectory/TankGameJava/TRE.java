/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGameJava;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static javax.imageio.ImageIO.read;

/**
 * @author carlos-lopez
 */
public class TRE extends JPanel {


    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    private BufferedImage world;
    private JFrame jf;
    private Tank t1Right;
    private Tank t2Left;
    private jPanel_Factory jPanel_factory = jPanel_Factory.getjPanel ();
    private JPanel jPanel1;
    private int val;
    private Image rescaledMiniMap, left, right;


    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE ();
        trex.init ();
        try {
            while (true) {
                trex.t1Right.update ();
                trex.t2Left.update ();
                trex.repaint ();
                Thread.sleep (1000 / 144);
            }
        } catch (InterruptedException ignored) {
        }
    }

    private void init() {
        this.jf = new JFrame ("Tank Rotation");
        BufferedImage t1img = jPanel_factory.readImage ("jar/TankGameDirectory/rsc/tank1.png");
        t2Left = new Tank (32, 32, 0, 0, 0, t1img);
        t1Right = new Tank ((SCREEN_WIDTH / 2) + (SCREEN_WIDTH / 4), SCREEN_HEIGHT / 2, 0, 0, 0, t1img);
        TankControl tc1 = new TankControl (t1Right, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD0);

        TankControl tc2 = new TankControl (t2Left, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_F, KeyEvent.VK_SPACE);

        this.jf.setLayout (new BorderLayout ());
        this.jf.add (this);

        this.jf.addKeyListener (tc1);

        this.jf.addKeyListener (tc2);

        this.jf.setSize (TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable (false);
        jf.setLocationRelativeTo (null);

        this.jf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible (true);

        // creates split screen
        jPanel1 = jPanel_factory.create_jPanelSplit_Factory (jf);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent (g2);
        world = jPanel_factory.imageCreator (jPanel1);

        bulletTankCollision ();

        // left subimage + center around tank pos
        val = withinBounds (t2Left.getX () - 32);
        left = world.getSubimage (val, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        // right subimage  + center around tank pos
        val = withinBounds (t1Right.getX () - 32);
        right = world.getSubimage (val, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);


        // minimap
        rescaledMiniMap = world.getScaledInstance (400, 400, Image.SCALE_SMOOTH);


        jPanel_factory.render (world.createGraphics ());

        // left tank && right tank
        t1Right.drawImage (world.createGraphics ());
        t2Left.drawImage (world.createGraphics ());

        // fire bullet
        t2Left.loadMagazine ();
        t1Right.loadMagazine ();

        // left world
        g2.drawImage (left, 0, 0, null);
        // right world
        g2.drawImage (right, SCREEN_WIDTH / 2, 0, null);

        // make pretty
        g2.setPaint (Color.BLACK);
        g2.fillRect ((SCREEN_WIDTH / 2) - 2, 0, 10, SCREEN_HEIGHT);
        g2.drawImage (rescaledMiniMap, SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2 + 90, null);

        //  end game
        if (t2Left.powerupcheckDeath () || t1Right.powerupcheckDeath ()) {
            BufferedImage splashScreen = jPanel_factory.readImage ("jar/TankGameDirectory/rsc/larger_image.jpg");
            g.fillRect (0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.drawImage (splashScreen, SCREEN_WIDTH / 4, 0, null);
            t2Left.terminate ();
            t1Right.terminate ();
        }
    }

    private int withinBounds(int x) {
        int xval;
        int rightcorner = x + (SCREEN_WIDTH / 2);
        if (x <= 0) {
            xval = x;
        } else if (rightcorner >= SCREEN_WIDTH) {
            xval = (SCREEN_WIDTH / 2);
        } else
            xval = x;
        return xval;
    }

    private void bulletTankCollision() {
        if (t1Right.getBulletRectangle ().intersects (t2Left.getRectangle ())) {
            t2Left.getPowerUp ().decreaseHealthBar ();
            t1Right.getBullets ().vanish ();
        }
        if (t2Left.getBulletRectangle ().intersects (t1Right.getRectangle ())) {
            t1Right.getPowerUp ().decreaseHealthBar ();
            t2Left.getBullets ().vanish ();
        }
    }
}
