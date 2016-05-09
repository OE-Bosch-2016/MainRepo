package hu.nik.project.visualisation.car.util;

import hu.nik.project.visualisation.car.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haxxi on 2016.05.08..
 */
public class Creator {

    public static List<Car> obstacle;

    public static void addObstacle(Car car, int rotation){
        if (obstacle == null)
            obstacle = new ArrayList<>();
        car.rotation(rotation);
        obstacle.add(car);
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public static List<Car> getObstacle() {
        return obstacle;
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public static void setObstacle(List<Car> obstacle) {
        Creator.obstacle = obstacle;
    }
}
