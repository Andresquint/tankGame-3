package TankGameJava;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

class jPanel_Factory {
    private int width = TRE.SCREEN_WIDTH;
    private int height = (TRE.SCREEN_HEIGHT + 30);
    private int[][] map;
    private Image image, deathimage, wall1, wall2, background;
    private static jPanel_Factory currentFactory;

    private jPanel_Factory() {
        this.map = convertTxtToDoubleArray ("jar/TankGameDirectory/rsc/map.txt");
        image = readImage ("jar/TankGameDirectory/rsc/Bouncing.gif");
        image = image.getScaledInstance (32, 32, Image.SCALE_SMOOTH);
        deathimage = readImage ("jar/TankGameDirectory/rsc/death.jpg");
        deathimage = deathimage.getScaledInstance (32, 32, Image.SCALE_SMOOTH);
        wall1 = readImage ("jar/TankGameDirectory/rsc/Wall1.gif");
        wall2 = readImage ("jar/TankGameDirectory/rsc/Wall2.gif");
        background = readImage ("jar/TankGameDirectory/rsc/Background.bmp");
    }

    static jPanel_Factory getjPanel() {
        if (currentFactory != null) {
            return currentFactory;
        }
        currentFactory = new jPanel_Factory ();
        return currentFactory;
    }


    JPanel create_jPanelSplit_Factory(JFrame jframe) {
        JPanel jPanel = new JPanel ();
        jPanel.setPreferredSize (new Dimension (width, height));
        jframe.add (jPanel);
        return jPanel;
    }


    void render(Graphics g) {

//        // general background
        for (int i = 0; i < width; i += 320) {
            for (int j = 0; j < height; j += 240)
                g.drawImage (background, i, j, null);
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                switch (map[i][j]) {
                    case 1:
                        g.setColor (Color.BLACK);
                        g.drawImage (wall1, i * 32, j * 32, null);
                        g.drawRect (i * 32, j * 32, 32, 32);
                        break;
                    case 2:
                        g.drawImage (wall2, i * 32, j * 32, null);
                        g.drawRect (i * 32, j * 32, 32, 32);
                        break;

                    case 3:
                        g.drawImage (image, i * 32, j * 32, null);
                        g.drawRect (i * 32, j * 32, 32, 32);
                        break;

                    case 9:
                        g.drawImage (deathimage, i * 32, j * 32, null);
                        g.drawRect (i * 32, j * 32, 32, 32);
                        break;
                }
            }
        }
    }

    BufferedImage readImage(String pictureFile) {
        BufferedImage bufferedImage = null;
        try {
//            bufferedImage = ImageIO.read (getClass ().getResourceAsStream (pictureFile));
            bufferedImage = ImageIO.read (new File (pictureFile));
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return bufferedImage;
    }

    BufferedImage imageCreator(JPanel jPanel) {
        BufferedImage bufferedImage = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics ();
        jPanel.print (g2);
        return bufferedImage;

    }

    private int[][] convertTxtToDoubleArray(String txtfile) {
        Scanner scanner = checkScanner (txtfile);
        String line;
        int numberValue;
        int j = 0;
        map = new int[40][30];
        // layout of map
        while (scanner.hasNextLine ()) {
            line = scanner.nextLine ();
            for (int i = 0; i < line.length (); i++) {
                numberValue = Integer.parseInt (String.valueOf (line.charAt (i)));
                map[i][j] = numberValue;
            }
            // increments y component of .txt file
            j++;
        }
        return map;
    }

    int[][] getMap() {
        return map;
    }

    private Scanner checkScanner(String txtfile) {

//        InputStream inputStream = jPanel_Factory.class.getResourceAsStream (txtfile);
//        InputStreamReader inputStreamReader = new InputStreamReader ((inputStream));
//        BufferedReader bufferedReader = new BufferedReader (inputStreamReader);

        File file = new File (txtfile);
        Scanner scanner = null;
        try {
            scanner = new Scanner (file);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return scanner;
    }

    void setTileChange(int value, int x, int y) {
        map[x][y] = value;
    }
}