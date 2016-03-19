import HMI.Hmi;
import Listeners.OnVehicleListener;
import Utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel on 2016. 03. 19..
 */
public class VisualizationRenderer extends Visualization {
    public VisualizationRenderer(JPanel drawingArea, Hmi hmi, Car car) {
        super(drawingArea, hmi, car);

        //Map
        JLabel mapLabel = new JLabel();
        mapLabel.setIcon(ImageLoader.getMapImage(ImageLoader.MAP1));
        drawingArea.add(mapLabel);
    }
}
