package rex.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            tankU = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            tankD = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            tankR = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
            tankL = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            missileU = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/missileU.gif"));
            missileD = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/missileD.gif"));
            missileR = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/missileR.gif"));
            missileL = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/missileL.gif"));
            for (int i = 0; i < exploses.length; i++) {
                exploses[i] = ImageIO.read(ResourceMngr.class.getClassLoader().getResourceAsStream("images/" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
