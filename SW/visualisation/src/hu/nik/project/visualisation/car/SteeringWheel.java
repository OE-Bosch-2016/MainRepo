package hu.nik.project.visualisation.car;

import hu.nik.project.hmi.Hmi;
import hu.nik.project.visualisation.interfaces.OnBreakSteeringWheelListener;
import hu.nik.project.utils.Config;
import hu.nik.project.utils.Scalr;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import hu.nik.project.visualisation.interfaces.OnBreakSteeringWheelListener;


/**
 * Created by Perec on 2016.03.16..
 */
public class SteeringWheel {

    JLabel label;
    Hmi hmi;
    BufferedImage scaledImage;
    double rotation = 0;
    private List<OnBreakSteeringWheelListener> listeners = new ArrayList<OnBreakSteeringWheelListener>();

    public void addListener(OnBreakSteeringWheelListener toAdd) {
        if(!listeners.contains(toAdd))
            listeners.add(toAdd);
    }

    public SteeringWheel(Hmi hmi,JLabel label) {
        this.hmi = hmi;
        this.label = label;
    }

    public ImageIcon GetSteeringWheel(double rotate)
    {
        try {
            rotation = rotation + rotate;
            scaledImage = ImageIO.read(new File(Config.pathSteeringWheel));
            scaledImage = Scalr.resize(scaledImage, 250, 250);
            scaledImage = rotate(scaledImage, rotation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (OnBreakSteeringWheelListener hl : listeners)
            hl.steeringWheelAngleChanged(rotation);

        return new ImageIcon(scaledImage);
    }

    public void control(KeyEvent e)
    {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            label.setIcon(this.GetSteeringWheel(365));
        else if(e.getKeyCode()== KeyEvent.VK_LEFT)
            label.setIcon(this.GetSteeringWheel(-365));
        else if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(hmi.getKhm() < 195)
            {
                int rpm = hmi.getRpm() + 600;
                hmi.setRpm(rpm);

                int kmh = hmi.getKhm() + 8;
                hmi.setKhm(kmh);

            }

            if(hmi.getRpm() > 4000)
            {
                int rpm = hmi.getRpm() - 2400;
                hmi.setRpm(rpm);

                int kmh = hmi.getKhm() - 1;
                hmi.setKhm(kmh);
            }

            hmi.mileage(hmi.getKhm());
            hmi.tachometer((float) hmi.getRpm());

        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if(hmi.getKhm() > 10) {
                int rpm = hmi.getRpm() - 600;
                hmi.setRpm(rpm);

                int kmh = hmi.getKhm() - 8;
                hmi.setKhm(kmh);

            }
            else
            {
                hmi.setRpm(600);
            }

            if(hmi.getRpm() < 600)
            {
                int rpm = hmi.getRpm() + 2400;
                hmi.setRpm(rpm);

                int kmh = hmi.getKhm() - 1;
                hmi.setKhm(kmh);
            }


            hmi.tachometer((float) hmi.getRpm());
            hmi.mileage(hmi.getKhm());

        }
        label.repaint();
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
