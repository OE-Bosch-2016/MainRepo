package radar;

import utils.Vector2D;

import java.io.Serializable;

/**
 * Created by secured on 2016. 03. 19..
 */
public class SpeedAndDistanceObj implements Serializable {
    private double _relativeSpeed;
    private double _currentDistance;
    private Vector2D _currentPosition;

    public SpeedAndDistanceObj(int relativeSpeed, double currentDistance, Vector2D currentPosition) {
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

    public Vector2D getCurrentPosition() {
        return _currentPosition;
    }
    public void setCurrentPosition(Vector2D currentPosition) {
        _currentPosition = currentPosition;
    }
}
