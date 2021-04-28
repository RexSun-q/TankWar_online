package rex.tank;

import java.awt.*;

public class Explode {
    private int x, y;
    // private GameModel gm;
    private TankFrame tf;
    private int step = 0;
    private static final int WIDTH = ResourceMngr.exploses[0].getWidth();
    private static final int HEIGHT = ResourceMngr.exploses[0].getHeight();

    public boolean isFinished = false;

    public Explode(int x, int y, TankFrame tf) {
       this.x = x;
       this.y = y;
       this.tf = tf;
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMngr.exploses[step++], x, y, null);
        if (step >= ResourceMngr.exploses.length) tf.explodes.remove(this);
    }

}
