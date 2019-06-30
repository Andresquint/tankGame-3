package TankGameJava;

import java.awt.*;

class PowerUp {

    // 3 max
    private int lifeCount = 1;
    // 50 max
    private int healthBar_status = 20;
    Rectangle powerUpRectangle;

    PowerUp(int x, int y, int width, int height) {
        this.powerUpRectangle = new Rectangle (x, y, width, height);
    }

    PowerUp() {

    }

    void runPowerUp() {
        if (healthBar_status < 50) {
            this.healthBar_status = 50;
        } else if (lifeCount < 3) {
            this.lifeCount++;
        }
    }

    void render(int x, int y, Graphics g) {
        g.setColor (Color.GREEN);
        g.fillRect (x, y - 25, healthBar_status, 12);
        g.setFont (new Font ("sans serif", Font.PLAIN, 20));
        g.drawString (healthBar_status * 2 + "%", x, y - 25);
        int j = 0;
        for (int i = 0; i < lifeCount; i++) {
            g.setColor (Color.BLUE);
            g.fillOval (x + j, y + 55, 15, 15);
            j += 20;
        }
    }

    void decreaseLife() {
        lifeCount--;
        if (lifeCount < 0) {
            lifeCount = 0;
            healthBar_status = 0;
        }
    }

    boolean getLifeCount() {
        if (lifeCount <= 0 && healthBar_status == 0) {
            return true;
        }
        return false;
    }

    void decreaseHealthBar() {
        healthBar_status = healthBar_status - 10;
        if (healthBar_status <= 0 && lifeCount > 0) {
            healthBar_status = 50;
            lifeCount--;
        }
    }
     void end(){
        lifeCount = 0;
        healthBar_status = 0;
     }

}