package rex.tank;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank {
    private static final int SPEED = 3;

    private int x;
    private int y;
    private Dir dir;
    // public GameModel gm;
    public TankFrame tf;
    private boolean moving = false;
    private boolean alive = true;
    private Group group;
    private Random random = new Random();
    private UUID uuid = UUID.randomUUID();

    public static final int WIDTH = ResourceMngr.tankD.getWidth();
    public static final int HEIGHT = ResourceMngr.tankD.getHeight();

    public Tank(int x, int y, Dir dir, Group group, boolean moving,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.moving = moving;
        this.group = group;
    }

    public void setMoving(Boolean isMoving) {
        moving = isMoving;
    }

    public void setAlive(Boolean isAlive) { alive = isAlive; }

    public void paint(Graphics g) {
        if (!alive) {
            tf.explodes.add(new Explode(x, y, tf));
            tf.enemyTanks.remove(this);
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString(uuid.toString(), x, y - 10);
        g.setColor(c);
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMngr.tankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMngr.tankR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMngr.tankU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMngr.tankD, x, y, null);
                break;
        }

        move();
    }

    private void move() {
        if (!moving) return;
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

        if (group == Group.BAD && random.nextInt(100) > 85) fire();

        if (group == Group.BAD && random.nextInt(100) > 96) randomRotate();

        boundCheck();
    }

    private void boundCheck() {
        if (x < 0) x = 2;
        if (y < 30) y = 32;
        if (x > TankFrame.GAME_WIDTH - WIDTH) x = TankFrame.GAME_WIDTH - WIDTH;
        if (y > TankFrame.GAME_HEIGHT - HEIGHT) y = TankFrame.GAME_HEIGHT - HEIGHT;
    }

    private void randomRotate() {
        dir = Dir.values()[random.nextInt(4)];
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Dir getDir() { return dir;}

    public void fire() {
        int xx = this.getX() + this.WIDTH / 2 - Bullet.width / 2;
        int yy = this.getY() + this.HEIGHT / 2 - Bullet.height / 2;
        new Bullet(xx, yy, dir, getGroup(), tf);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getGroup() { return group; }

    public UUID getUuid() {
        return uuid;
    }

    public boolean getMoving() {
        return moving;
    }
}
