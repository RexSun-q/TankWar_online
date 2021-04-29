package rex.tank;

public class Main {
    public static void main(String[] args) throws Exception {
        TankFrame tankFrame = TankFrame.getINSTANCE();
        tankFrame.setVisible(true);

//        for (int i = 0; i < 10; i++) {
//            tankFrame.enemyTanks.add(new Tank(30 + 80 * i, 200, Dir.DOWN, Group.BAD, tankFrame));
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tankFrame.repaint();
                }
            }
        }).start();

        // tankFrame.client.connect();
    }
}
