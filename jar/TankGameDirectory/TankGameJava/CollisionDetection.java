package TankGameJava;


class CollisionDetection {
    private jPanel_Factory jPanel_factory = jPanel_Factory.getjPanel ();

    int[] checkCollision(Tank t, int currentx, int currenty, int oldX, int oldY) {
        int[] returnPosition = new int[2];
        // div 32 and round so we can get accurate to 2dmap (REALLY CORNY)
        int x = Math.round (currentx / 32);
        int y = Math.round (currenty / 32);

        // hit wall-- stop
        if (jPanel_factory.getMap ()[x][y] == 2 || jPanel_factory.getMap ()[x][y] == 1) {
            returnPosition[0] = oldX;
            returnPosition[1] = oldY;
            return returnPosition;
        }

        // hit power up
        if (jPanel_factory.getMap ()[x][y] == 3) {
            t.getPowerUp ().runPowerUp ();
            jPanel_factory.setTileChange (0, x, y);
        }

        if (jPanel_factory.getMap ()[x][y] == 9) {
            jPanel_factory.setTileChange (0, x, y);
            t.getPowerUp ().decreaseLife ();
        }
        returnPosition[0] = currentx;
        returnPosition[1] = currenty;
        return returnPosition;
    }

    int[] bulletCollision(int bulletx, int bullety, Bullets bullets) {
        Tank tank = new Tank ();
        int[] alterMap = new int[2];
        int x = Math.round (bulletx / 32);
        int y = Math.round (bullety / 32);
        if (jPanel_factory.getMap ()[x][y] == 2) {
            jPanel_factory.setTileChange (0, x, y);

            // just go off the screen, then remove bullet
            alterMap[0] = -bulletx;
            alterMap[1] = -bullety;
            tank.removeBullet (bullets);
            return alterMap;
        }
        alterMap[0] = bulletx;
        alterMap[1] = bullety;
        return alterMap;
    }

}
