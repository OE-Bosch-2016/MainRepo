package utils2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by haxxi on 2016.03.13..
 */
public class ImageLoader {

    public static int MAP1 = 0;
    public static int MAP2 = 1;

    // Getter ----------------------------------------------------------------------------------------------------------
    public static ImageIcon getMapImage(int mapType) {
        ImageIcon icon = null;
        BufferedImage scaledImage = loadImageFromPath(mapType == MAP2 ? Config.pathMap2 : Config.pathMap1);
        scaledImage = utils2.Scalr.resize(scaledImage, Config.imageSizeX, Config.imageSizeY);
        icon = new ImageIcon(scaledImage);

        return icon;
    }

    public static BufferedImage getCarImage() {
        BufferedImage scaledImage = loadImageFromPath(Config.pathCar);
        scaledImage = utils2.Scalr.resize(scaledImage, Config.carImageSizeX, Config.carImageSizeY);

        return scaledImage;
    }

    private static BufferedImage loadImageFromPath(String path)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
