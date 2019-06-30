/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGameJava;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;


/**
 * @author carlos-lopez
 */
public class TankControl implements KeyListener {

    private Tank t1;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    private jPanel_Factory jPanel_factory = jPanel_Factory.getjPanel ();
    private BufferedImage bullet = jPanel_factory.readImage ("jar/TankGameDirectory/rsc/Shell.gif");

    TankControl(Tank t1, int up, int down, int left, int right, int shoot) {
        this.t1 = t1;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode ();
        if (keyPressed == up) {
            this.t1.toggleUpPressed ();
        }
        if (keyPressed == down) {
            this.t1.toggleDownPressed ();
        }
        if (keyPressed == left) {
            this.t1.toggleLeftPressed ();
        }
        if (keyPressed == right) {
            this.t1.toggleRightPressed ();
        }
        if (keyPressed == shoot) {
            t1.addBullet (new Bullets (t1.getX (), t1.getY (), t1.getVx (), t1.getVy (), t1.getAngle (), bullet));

        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode ();
        if (keyReleased == up) {
            this.t1.unToggleUpPressed ();
        }
        if (keyReleased == down) {
            this.t1.unToggleDownPressed ();
        }
        if (keyReleased == left) {
            this.t1.unToggleLeftPressed ();
        }
        if (keyReleased == right) {
            this.t1.unToggleRightPressed ();
        }
    }
}
