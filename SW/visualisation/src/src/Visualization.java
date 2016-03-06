import java.awt.*;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Visualization {

    Canvas _mapArea;
    int _speedoMeter;  //refactor to textbox or something
    Car _car;

    //TODO: Vehicle listener


    public Visualization(Canvas mapArea, int speedoMeter) {
        _mapArea = mapArea;
        _speedoMeter = speedoMeter;
    }

    public Canvas getMapArea() {
        return _mapArea;
    }

    public void setMapArea(Canvas mapArea) {
        _mapArea = mapArea;
    }

    public int getSpeedoMeter() {
        return _speedoMeter;
    }

    public void setSpeedoMeter(int speedoMeter) {
        _speedoMeter = speedoMeter;
    }

    public void setCar(Car car) {
        _car = car;
    }

    //TODO: Add vehicle listener!
}
