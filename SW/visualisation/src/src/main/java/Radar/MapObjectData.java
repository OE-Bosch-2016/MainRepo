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
    public void setRelativeSpeed(double relativeSpeed) {
        _relativeSpeed = relativeSpeed;
    }

    public void setCurrentDistance(double currentDistance) {
        _currentDistance = currentDistance;
    }
    public double getCurrentDistance() {
        return _currentDistance;
    }

    public Position getCurrentPosition() {
        return _currentPosition;
    }
    public void setCurrentPosition(Position currentPosition) {
        _currentPosition = currentPosition;
    }
}
