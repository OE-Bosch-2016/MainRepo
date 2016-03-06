import java.awt.*;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Visualization {

    Canvas _mapArea;
    TextField _speedoMeter;  //refactor to textbox or something
    Car _car;

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

    //TODO: Add vehicle listener!
}
