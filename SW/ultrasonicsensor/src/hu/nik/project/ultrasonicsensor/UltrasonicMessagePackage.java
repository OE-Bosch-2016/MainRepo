package hu.nik.project.ultrasonicsensor;

import java.io.Serializable;
import java.util.Arrays;

/*
 * @author Patrik
 */
public class UltrasonicMessagePackage implements Serializable {
    private int[] nearestObstacles;
    public UltrasonicMessagePackage(int[] array) {
        this.nearestObstacles = Arrays.copyOf(array, array.length);
    }

    /** @return a copy of the array */
    public int[] getNearestObstacles() {
        return Arrays.copyOf(nearestObstacles, nearestObstacles.length);
    }
}