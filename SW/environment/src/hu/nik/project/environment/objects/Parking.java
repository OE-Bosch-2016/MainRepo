package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for parking
 */
public class Parking extends Road implements Serializable {

    public enum ParkingType {
        PARKING_0,
        PARKING_90,
    }

    private ParkingType type;

    public Parking(ScenePoint basePosition, int rotation, ParkingType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public ParkingType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " ParkingType: " + type.toString();
    }
}
