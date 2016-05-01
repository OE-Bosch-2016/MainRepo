package hu.nik.project.utils;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by haxxi on 2016.05.01..
 */
public class Transformation {

    public static ScenePoint transformFromVector2D(Vector2D coordinate){
        float rate[] = scaleRate();

        return new ScenePoint((int)(coordinate.get_coordinateX() / rate[0]), (int)(coordinate.get_coordinateY() / rate[1]));
    }

    public static Vector2D transformToVector2D(ScenePoint coordinate){
        float rate[] = scaleRate();

        return new Vector2D(coordinate.getX() * rate[0], coordinate.getY() * rate[1]);
    }

    private static float[] scaleRate(){
        float[] rate = new float[2];
        rate[0] = Config.originalX / Config.imageSizeX;
        rate[1] = Config.originalY / Config.imageSizeY;

        return rate;
    }
}
