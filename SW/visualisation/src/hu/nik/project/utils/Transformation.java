package hu.nik.project.utils;

/**
 * Created by haxxi on 2016.05.01..
 */
public class Transformation {

    public static Vector2D transformFromVector2D(Vector2D coordinate){
        float rate[] = scaleRate();

        return new Vector2D(coordinate.get_coordinateX() / rate[0], coordinate.get_coordinateY() / rate[1]);
    }

    public static Vector2D transformToVector2D(Vector2D coordinate){
        float rate[] = scaleRate();

        return new Vector2D(coordinate.get_coordinateX() * rate[0], coordinate.get_coordinateY() * rate[1]);
    }

    private static float[] scaleRate(){
        float[] rate = new float[2];
        rate[0] = Config.originalX / Config.imageSizeX;
        rate[1] = Config.originalY / Config.imageSizeY;

        return rate;
    }
}
