package TankGameJava;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

class Bullets {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private final int R = 1;
    private BufferedImage img;
    private CollisionDetection collisionDetection = new CollisionDetection ();
    private int[] newCoordinates = new int[2];
    private Rectangle rectangle = new Rectangle ();

    Bullets(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }

    Bullets() {

    }

    void update() {
        this.moveForwards ();
    }

    private void moveForwards() {
        vx = (int) Math.round (R * Math.cos (Math.toRadians (angle)) * 50);
        vy = (int) Math.round (R * Math.sin (Math.toRadians (angle)) * 50);
        x += vx;
        y += vy;
        rectangle = new Rectangle (x, y, 40, 40);
        if (x > 0 && y > 0 && x < TRE.SCREEN_WIDTH && y < TRE.SCREEN_HEIGHT) {
            newCoordinates = collisionDetection.bulletCollision (x, y, this);
            x = newCoordinates[0];
            y = newCoordinates[1];
        }
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform rotation = AffineTransform.getTranslateInstance (x, y);
        rotation.rotate (Math.toRadians (angle), this.img.getWidth () / 2.0, this.img.getHeight () / 2.0);
        g2d.drawImage (this.img, rotation, null);
        rectangle.setRect (x, y, 60, 60);

    }

    Rectangle getRectangle() {
        return rectangle;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void vanish(){
        x = -x;
        y = -y;
    }
}
