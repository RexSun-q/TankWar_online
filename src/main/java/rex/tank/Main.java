package rex.tank;

public class Main {
    public static void main(String[] args) throws Exception {
        TankFrame tankFrame = TankFrame.getINSTANCE();
        tankFrame.setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tankFrame.repaint();
                }
            }
        }).start();

        tankFrame.client.connect();
    }
}
