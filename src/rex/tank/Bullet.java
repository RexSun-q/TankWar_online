package rex.tank;

import org.w3c.dom.css.Rect;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 10;
    public static final int width = ResourceMngr.missileD.getWidth();
    public static final int height = ResourceMngr.missileD.getHeight();

    private int x, y;
    private Dir dir;
    //private GameModel gm;
    public TankFrame tf;
    private Rectangle selfRect = new Rectangle();

    private boolean alive = true;
    private Group group;

    public Bullet(int x, int y, Dir dir, Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        selfRect.x = x;
        selfRect.y = y;
        selfRect.width = width;
        selfRect.height = height;

        tf.bulletList.add(this);
    }

    public void setAlive(Boolean isAlive) { alive = isAlive; }

    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return;
        // 两个rect会产生 n*m 量级的 rect
        Rectangle r2 = new Rectangle(tank.getX(), tank.getY(), tank.WIDTH, tank.HEIGHT);
        if (selfRect.intersects(r2)) {
            this.setAlive(false);
            tank.setAlive(false);
        }
    }

    public void paint(Graphics g) {
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMngr.missileL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMngr.missileR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMngr.missileU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMngr.missileD, x, y, null);
                break;
        }
        move(dir);
    }

    private void move(Dir dir) {
        if (!alive) {
            tf.bulletList.remove(this);
            return;
        }
        switch (dir) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            alive = false;
        }
        // update selfRect
        selfRect.x = x;
        selfRect.y = y;
    }


}
