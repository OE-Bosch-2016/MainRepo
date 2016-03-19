/**
 * Created by secured on 2016. 03. 06..
 */
public class Vector2D {
    private int _coordinateX;
    private int _coordinateY;

    public Vector2D(int coordinateX, int coordinateY) {
        _coordinateX = coordinateX;
        _coordinateY = coordinateY;
    }

    public int get_coordinateX() {
        return _coordinateX;
    }

    public void set_coordinateX(int coordinateX) {
        _coordinateX = coordinateX;
    }

    public int get_coordinateY() {
        return _coordinateY;
    }

    public void set_coordinateY(int coordinateY) {
        _coordinateY = coordinateY;
    }
}
