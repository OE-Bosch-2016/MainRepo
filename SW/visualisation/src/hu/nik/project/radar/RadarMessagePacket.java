package hu.nik.project.radar;

import hu.nik.project.environment.objects.Car;

import java.io.Serializable;

/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarMessagePacket implements Serializable {
    private Double _relativeSpeed;
    private Double _currentDistance;
    private Car _car;

    public RadarMessagePacket(double relativeSpeed, double currentDistance,Car car) {
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

}
