package hu.nik.project.radar;

import hu.nik.project.environment.objects.Car;

import java.io.Serializable;

/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarMessagePacket implements Serializable {
    private double _relativeSpeed;
    private double _currentDistance;
    private Car _car;

    public RadarMessagePacket(int relativeSpeed, double currentDistance,Car car) {
        _relativeSpeed = relativeSpeed;
        _currentDistance = currentDistance;
        _car=car;
    }

    public double getRelativeSpeed() {
        return _relativeSpeed;
    }

    public void setRelativeSpeed(double relativeSpeed) {
        _relativeSpeed = relativeSpeed;
    }

    public void setCurrentDistance(double currentDistance) {
        _currentDistance = currentDistance;
    }

    public double getCurrentDistance() {
        return _currentDistance;
    }

    public Car get_car() {
        return _car;
    }

    public void set_car(Car _car) {
        this._car = _car;
    }
}
