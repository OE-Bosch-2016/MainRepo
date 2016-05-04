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

    float gas;
    JLabel label;
    Hmi hmi;
    BufferedImage scaledImage;
    double rotation = 0;
    private List<OnBreakSteeringWheelListener> listeners = new ArrayList<OnBreakSteeringWheelListener>();

    public void addListener(OnBreakSteeringWheelListener toAdd) {
        if(!listeners.contains(toAdd))
            listeners.add(toAdd);
    }

    public SteeringWheel(JLabel label) {
        this.hmi = Hmi.newInstance();
        this.label = label;
        loadImage();
    }

    private void loadImage(){
        try {
            scaledImage = ImageIO.read(new File(Config.pathSteeringWheel));
            scaledImage = Scalr.resize(scaledImage, 250, 250);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon GetSteeringWheel(double rotate)
    {


//        for (OnBreakSteeringWheelListener hl : listeners)
//            hl.steeringWheelAngleChanged(rotation);

        return new ImageIcon(rotate(scaledImage, rotate));
    }

    public void control(float gas,float rotate,int e)
    {
        //rotate not syncronized with car yet
        this.gas = 10*gas;
        if(e == KeyEvent.VK_RIGHT)
            label.setIcon(GetSteeringWheel(-5));
        else if(e == KeyEvent.VK_LEFT)
            label.setIcon(GetSteeringWheel(+5));
        else if(e == KeyEvent.VK_UP) {
            if(hmi.getKhm() < 195)
            {
                int rpm = hmi.getRpm() + 100;
                hmi.setRpm(rpm);

                int kmh = (int)this.gas;
                hmi.setKhm(kmh);

            }

            if(hmi.getRpm() > 4000)
            {
                int rpm = hmi.getRpm() - 2400;
                hmi.setRpm(rpm);

                int kmh = (int)this.gas;
                hmi.setKhm(kmh);
            }
        }
        else if(e == KeyEvent.VK_DOWN)
        {
            if(hmi.getRpm()>800) {
                int rpm = hmi.getRpm() - 40;
                hmi.setRpm(rpm);
            }
                if(hmi.getRpm() < 1200)
                {
                    if(hmi.getKhm()>50)
                    {
                        int mod = hmi.getRpm() + 2400;
                        hmi.setRpm(mod);
                    }else
                    {
                        int mod = hmi.getRpm() + 20;
                        hmi.setRpm(mod);
                    }
                }


            int kmh = (int)this.gas;
            hmi.setKhm(kmh);

        }
        hmi.tachometer((float) hmi.getRpm());
        hmi.mileage(hmi.getKhm());
        label.repaint();
    }


    public BufferedImage rotate(BufferedImage image, double angle) {
        angle = Math.toRadians(angle);
        int w = image.getWidth(), h = image.getHeight();
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((image.getWidth()-w)/2, (image.getHeight()-h)/2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    public void setDefaultRpm(float gas) {

        if(hmi.getRpm()>600)
        {
            int rpm = hmi.getRpm() - 20;
            hmi.setRpm(rpm);

        }
        hmi.tachometer((float) hmi.getRpm());
        hmi.mileage(hmi.getKhm());
    }
}
