package Radar;

import Utils.Position;

/**
 * Created by secured on 2016. 03. 19..
 */
public class MapObjectData {
    private double _relativeSpeed;
    private double _currentDistance;
    private Position _currentPosition;

    public MapObjectData(int relativeSpeed, double currentDistance, Position currentPosition) {
        _relativeSpeed = relativeSpeed;
        _currentDistance = currentDistance;
        _currentPosition = currentPosition;
    }

    public double getRelativeSpeed() {
        return _relativeSpeed;
    }

    public void setRelativeSpeed(int relativeSpeed) {
        _relativeSpeed = relativeSpeed;
    }

    public double getCurrentDistance() {
        return _currentDistance;
    }

    public double get_relativeSpeed() {
        return _relativeSpeed;
    }

    public void set_relativeSpeed(double _relativeSpeed) {
        this._relativeSpeed = _relativeSpeed;
    }

    public double get_currentDistance() {
        return _currentDistance;
    }

    public void set_currentDistance(double _currentDistance) {
        this._currentDistance = _currentDistance;
    }

    public Position get_currentPosition() {
        return _currentPosition;
    }

    public void set_currentPosition(Position _currentPosition) {
        this._currentPosition = _currentPosition;
    }

    public void setCurrentDistance(double currentDistance) {
        _currentDistance = currentDistance;
    }

    public Position getCurrentPosition() {
        return _currentPosition;
    }

}
