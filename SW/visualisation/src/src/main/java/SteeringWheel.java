import Utils.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Perec on 2016.03.16..
 */
public class SteeringWheel {

    BufferedImage scaledImage;
    double rotation = 0;


    public SteeringWheel() {

    }

    public ImageIcon GetSteeringWheel(double rotate)
    {
        try {
            rotation = rotation + rotate;
            scaledImage = ImageIO.read(new File(Config.getPathSteeringWheel));
            scaledImage = Utils.Scalr.resize(scaledImage, 250, 250);
            scaledImage = rotate(scaledImage, rotation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(scaledImage);
    }

    public BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(angle, w/2, h/2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
}
