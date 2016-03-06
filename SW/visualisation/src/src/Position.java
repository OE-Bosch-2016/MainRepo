/**
 * Created by secured on 2016. 03. 06..
 */
public class Position {
    int _positionX;
    int _positionY;

    public Position(int positionX, int positionY) {
        _positionX = positionX;
        _positionY = positionY;
    }

    public int get_positionX() {
        return _positionX;
    }

    public void set_positionX(int positionX) {
        _positionX = positionX;
    }

    public int get_positionY() {
        return _positionY;
    }

    public void set_positionY(int positionY) {
        _positionY = positionY;
    }
}
