package Visualisation;

import HMI.Hmi;
import Visualisation.Car.Car;
import Visualisation.Interfaces.IWheelVisualization;
import Visualisation.Interfaces.OnVehicleListener;

import javax.swing.*;

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
    }

    public void ModifyVehicleSpeed(float speed) {
        _hmi.mileage(speed);
        _car.move(speed);
        _vehicleListener.PositionChanged((int)(_car.getPosition().get_coordinateX()), (int)(_car.getPosition().get_coordinateY()));
    }

    public void ModifyVehicleOrientation(float degree) {
         _car.rotate(degree);
    }

    public void setOnVehicleListener(OnVehicleListener listener){
        _vehicleListener=listener;
    }

    public void render()
    {
        _carLabel.setIcon(new ImageIcon(_car.getImage()));
        _carLabel.setLocation((int)(_car.getPosition().get_coordinateX()), (int)(_car.getPosition().get_coordinateY()));
    }

    public JLabel get_carLabel() {
        return _carLabel;
    }
}
