package hu.nik.project.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by haxxi on 2016.03.13..
 */
public class ImageLoader {



    // Getter ----------------------------------------------------------------------------------------------------------
    public static ImageIcon getMapImage(String mapPath) {
        ImageIcon icon = null;

        if (mapPath == "") {
           mapPath = Config.pathMap;
        } else
            Config.pathMap = mapPath;

        BufferedImage scaledImage = loadImageFromPath(mapPath);
        scaledImage = Scalr.resize(scaledImage, Config.imageSizeX, Config.imageSizeY);
        icon = new ImageIcon(scaledImage);

        return icon;
    }

    public static BufferedImage getCarImage() {
        BufferedImage scaledImage = loadImageFromPath(Config.pathCar);
        scaledImage = Scalr.resize(scaledImage, Config.carImageSizeX, Config.carImageSizeY);

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
