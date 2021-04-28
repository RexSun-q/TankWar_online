package rex.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceMngr {
    // Singleton design pattern
    private static ResourceMngr INSTANCE = new ResourceMngr();
    public static BufferedImage tankL, tankR, tankU, tankD;
    public static BufferedImage missileL, missileU, missileR, missileD;
    public static BufferedImage[] exploses = new BufferedImage[10];

    private ResourceMngr() { }

    public static ResourceMngr getINSTANCE() { return INSTANCE; }

    static {
        try {
            tankU = ImageIO.read(new File("src/main/java/images/tankU.gif"));
            tankD = ImageIO.read(new File("src/main/java/images/tankD.gif"));
            tankR = ImageIO.read(new File("src/main/java/images/tankR.gif"));
            tankL = ImageIO.read(new File("src/main/java/images/tankL.gif"));
            missileU = ImageIO.read(new File("src/main/java/images/missileU.gif"));
            missileD = ImageIO.read(new File("src/main/java/images/missileD.gif"));
            missileR = ImageIO.read(new File("src/main/java/images/missileR.gif"));
            missileL = ImageIO.read(new File("src/main/java/images/missileL.gif"));
            for (int i = 0; i < exploses.length; i++) {
                exploses[i] = ImageIO.read(new File("src/main/java/images/" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
