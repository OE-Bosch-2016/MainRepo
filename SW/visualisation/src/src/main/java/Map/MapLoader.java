package Map;

import Utils.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by haxxi on 2016.03.13..
 */
public class MapLoader {

    public static int MAP1 = 0;
    public static int MAP2 = 1;

    // Getter ----------------------------------------------------------------------------------------------------------
    public static ImageIcon getImage(int mapType) {
        ImageIcon icon = null;
        try {
            BufferedImage scaledImage = ImageIO.read(new File(mapType == MAP2 ? Config.pathMap2 : Config.pathMap1));
            scaledImage = Utils.Scalr.resize(scaledImage, Config.imageSizeX, Config.imageSizeY);
            icon = new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }
}
