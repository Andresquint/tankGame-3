package TankGameJava;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author carlos-lopez
 */
public class Tank {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private final int R = 1;
    private final int ROTATIONSPEED = 2;


    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private CollisionDetection collisionDetection = new CollisionDetection ();

    private int[] returnvalues = new int[2];
    private ArrayList<Bullets> bulletsArrayList = new ArrayList<> ();
    private Bullets bullets = new Bullets ();
    private boolean firevalue;
    private Rectangle rectangle = new Rectangle ();
    private PowerUp powerUp = new PowerUp ();


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }

    Tank() {
        this.rectangle = new Rectangle (50, 50);
    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }


    void update() {
        if (this.UpPressed) {
            this.moveForwards ();
        }
        if (this.DownPressed) {

            this.moveBackwards ();
        }

        if (this.LeftPressed) {
            this.rotateLeft ();
        }
        if (this.RightPressed) {
            this.rotateRight ();
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        int oldx = x;
        int oldy = y;
        vx = (int) Math.round (R * Math.cos (Math.toRadians (angle)));
        vy = (int) Math.round (R * Math.sin (Math.toRadians (angle)));
        x -= vx;
        y -= vy;
        returnvalues = collisionDetection.checkCollision (this, x, y, oldx, oldy);
        x = returnvalues[0];
        y = returnvalues[1];
        checkBorder ();

    }

    private void moveForwards() {
        int oldx = x;
        int oldy = y;
        vx = (int) Math.round (R * Math.cos (Math.toRadians (angle)));
        vy = (int) Math.round (R * Math.sin (Math.toRadians (angle)));
        x += vx;
        y += vy;
        returnvalues = collisionDetection.checkCollision (this, x, y, oldx, oldy);
        x = returnvalues[0];
        y = returnvalues[1];
        checkBorder ();
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.SCREEN_WIDTH - 88) {
            x = TRE.SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.SCREEN_HEIGHT - 80) {
            y = TRE.SCREEN_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance (x, y);
        rotation.rotate (Math.toRadians (angle), this.img.getWidth () / 2.0, this.img.getHeight () / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage (this.img, rotation, null);
        rectangle.setRect (x, y, 70, 70);
        powerUp.render (x, y, g);
        if (firevalue) {
            for (Bullets value : bulletsArrayList) {
                bullets = value;
                bullets.drawImage (g);
            }
        }
    }

    void loadMagazine() {
        for (int i = 0; i < bulletsArrayList.size (); i++) {
            bullets = bulletsArrayList.get (i);
            if (bullets.getY () < 0 || bullets.getX () < 0 || bullets.getY () > TRE.SCREEN_HEIGHT || bullets.getX () > TRE.SCREEN_WIDTH)
                removeBullet (bullets);
            bullets.update ();
        }
    }

    void addBullet(Bullets bullets) {
        bulletsArrayList.add (bullets);
        firevalue = true;
    }

    void removeBullet(Bullets bullets) {
        bulletsArrayList.remove (bullets);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getVx() {
        return vx;
    }

    int getVy() {
        return vy;
    }

    int getAngle() {
        return angle;
    }

    void setX(int newx) {
        x = newx;
    }

    void setY(int newy) {
        y = newy;
    }

    PowerUp getPowerUp() {
        return this.powerUp;
    }

    boolean powerupcheckDeath() {
        return powerUp.getLifeCount ();
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    Bullets getBullets() {
        return bullets;
    }

    Rectangle getBulletRectangle() {
        return bullets.getRectangle ();
    }

    void terminate(){
        getPowerUp ().end ();
    }
}
