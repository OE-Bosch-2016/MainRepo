package hu.nik.project.ultrasonicsensor;

import java.io.Serializable;
import java.util.Arrays;

/*
 * @author Patrik
 */
public class UltrasonicMessagePackage implements Serializable {
    private double[] nearestObstacles;
    public UltrasonicMessagePackage(double[] array) {
        this.nearestObstacles = Arrays.copyOf(array, array.length);
    }

    /** @return a copy of the array */
    public double[] getNearestObstacles() {
        return Arrays.copyOf(nearestObstacles, nearestObstacles.length);
    }
}