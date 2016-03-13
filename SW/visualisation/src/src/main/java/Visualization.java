import Interfaces.IWheelVisualization;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Visualization implements IWheelVisualization {

    private Canvas _mapArea;
    private TextField _speedoMeter;  //refactor to textbox or something
    private Car _car;

    //TODO: Vehicle listener

    public Visualization(Canvas mapArea, TextField speedoMeter, Car car) {
        _mapArea = mapArea;
        _speedoMeter = speedoMeter;
        _car=car;
    }

    public Canvas getMapArea() {
        return _mapArea;
    }

    public void setMapArea(Canvas mapArea) {
        _mapArea = mapArea;
    }

    public void setCar(Car car) {
        _car = car;
    }

    //TODO: implement ME!
    public float ModifyVehicleSpeed(float speed) {
        throw  new NotImplementedException();
    }

    public float ModifyVehicleOrientaton(float degree) {
        return _car.RotateCar(degree);
    }

    //TODO: Add vehicle listener!
}
