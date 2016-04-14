package hu.nik.project.visualisation;


import hu.nik.project.hmi.Hmi;
import hu.nik.project.utils.ImageLoader;
import hu.nik.project.visualisation.Car.Car;
import javax.swing.*;

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
