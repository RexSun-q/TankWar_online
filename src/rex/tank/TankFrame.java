package rex.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    Tank myTank = new Tank(50, 50, Dir.DOWN, Group.GOOD,this);
    List<Tank> enemyTanks = new ArrayList<>();
    List<Bullet> bulletList = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    public TankFrame() {
        this.setResizable(false);
        this.setTitle("Tank War");
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setVisible(true);

        this.addKeyListener(new MyKeyListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // 双缓冲解决闪烁
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage ==null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0,null);
    }

    @Override
    public void paint(Graphics g) {
        // gm.paint(g);
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("bullet count:" + bulletList.size(), 50, 50);
        g.drawString("enemy count:" + enemyTanks.size(), 50, 70);
        g.drawString("explodes count:" + explodes.size(), 50, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(g);
        }

        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).paint(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        // 碰撞检测
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < enemyTanks.size(); j++) {
                bulletList.get(i).collideWith(enemyTanks.get(j));
            }
        }

    }

    class MyKeyListener extends KeyAdapter {
        boolean BL = false;
        boolean BR = false;
        boolean BU = false;
        boolean BD = false;
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    BL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    BR = true;
                    break;
                case KeyEvent.VK_UP:
                    BU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    BD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    BL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    BR = false;
                    break;
                case KeyEvent.VK_UP:
                    BU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    BD = false;
                    break;
                case KeyEvent.VK_SPACE:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
//            Tank myTank = gm.myTank;
            if (!BD && !BL && !BR && !BU) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);
                if (BL) myTank.setDir(Dir.LEFT);
                if (BR) myTank.setDir(Dir.RIGHT);
                if (BU) myTank.setDir(Dir.UP);
                if (BD) myTank.setDir(Dir.DOWN);
            }
        }
    }
}
