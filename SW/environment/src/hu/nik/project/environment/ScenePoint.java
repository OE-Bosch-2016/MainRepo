package hu.nik.project.environment;

/**
 * Created by Róbert on 2016.03.14..
 *
 * Base point class for positions
 */
public class ScenePoint {
    private int x;
    private int y;

    public ScenePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return ("X: " + x + " Y: " + y);
    }
}
