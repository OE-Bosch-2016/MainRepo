package hu.nik.project.utils;

/**
 * Created by secured on 2016. 03. 06..
 */
public class Vector2D {
    private float _coordinateX;
    private float _coordinateY;

    public Vector2D(float coordinateX, float coordinateY) {
        _coordinateX = coordinateX;
        _coordinateY = coordinateY;
    }

    public float get_coordinateX() {
        return _coordinateX;
    }

    public void set_coordinateX(float coordinateX) {
        _coordinateX = coordinateX;
    }

    public float get_coordinateY() {
        return _coordinateY;
    }

    public void set_coordinateY(float coordinateY) {
        _coordinateY = coordinateY;
    }
}
