package rex.tank;

public class Main {
    public static void main(String[] args) throws Exception {
        TankFrame tankFrame = new TankFrame();

        for (int i = 0; i < 10; i++) {
            tankFrame.enemyTanks.add(new Tank(30 + 80 * i, 200, Dir.DOWN, Group.BAD, tankFrame));
        }
        while (true) {
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
