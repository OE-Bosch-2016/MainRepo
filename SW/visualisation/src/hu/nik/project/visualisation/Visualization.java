package hu.nik.project.visualisation;

import hu.nik.project.hmi.Hmi;
import hu.nik.project.utils.Config;
import hu.nik.project.utils.Vector2D;
import hu.nik.project.visualisation.car.Car;
import hu.nik.project.visualisation.car.util.Creator;
import hu.nik.project.visualisation.interfaces.IWheelVisualization;
import hu.nik.project.visualisation.interfaces.OnVehicleListener;

import javax.swing.*;

import java.awt.*;

import static hu.nik.project.utils.Transformation.transformToVector2D;
import static hu.nik.project.utils.Transformation.transformToVisulasation;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Visualization implements IWheelVisualization {

    private JPanel _drawingArea;
    private Hmi _hmi;
    private Car _car;
    private JLabel _carLabel;
    private OnVehicleListener _vehicleListener;

    public Visualization(JPanel drawingArea, Hmi hmi, Car car) {
        _drawingArea = drawingArea;
        _hmi = hmi;
        _carLabel = new JLabel();
        _carLabel.setSize(Config.carImageSizeX, Config.carImageSizeY);
        createObstacles();
        setCar(car);
    }

    public JPanel getDrawingArea() {
        return _drawingArea;
    }

    public void setDrawingArea(JPanel drawingArea) {
        _drawingArea = drawingArea;
    }

    public void setCar(Car car) {
        _car = car;
        _drawingArea.add(_carLabel);
        _drawingArea.setComponentZOrder(_carLabel, 0);
    }

    public void ModifyVehicleSpeed(float speed) {
        _hmi.mileage(speed);
        _car.move(speed);
        _vehicleListener.PositionChanged((int) (_car.getPosition().get_coordinateX()), (int) (_car.getPosition().get_coordinateY()));
    }

    public void ModifyVehicleOrientation(float degree) {
        _car.rotate(degree);
    }

    public void setOnVehicleListener(OnVehicleListener listener) {
        _vehicleListener = listener;
    }

    public void render() {
        _carLabel.setIcon(new ImageIcon(_car.getImage()));
        _carLabel.setLocation((int) (_car.getPosition().get_coordinateX()), (int) (_car.getPosition().get_coordinateY()));
    }

    private void createObstacles() {
        if(Creator.getObstacle() != null){
            for(Car car : Creator.getObstacle()){
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon(car.getImage()));
                label.setSize(Config.carImageSizeX, Config.carImageSizeY);
                //car.setPosition(transformToVisulasation(car.getPosition()));
                car.setPosition(transformToVector2D(car.getPosition()));
                label.setLocation((int)car.getPosition().get_coordinateX() + Config.carImageSizeX, (int)car.getPosition().get_coordinateY() * 2);

                _drawingArea.add(label);
                _drawingArea.setComponentZOrder(label, 0);
            }
        }
    }

    public JLabel get_carLabel() {
        return _carLabel;
    }
}
