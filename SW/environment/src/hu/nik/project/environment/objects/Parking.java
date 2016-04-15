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

    private static final int parkingWidth = 74;
    private static final int parkingLength = 116;

    public Parking(ScenePoint basePosition, int rotation, ParkingType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;

        int halfParkingWith = parkingWidth / 2;

        if (type == ParkingType.PARKING_0) {
            super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX(), basePosition.getY() + halfParkingWith), rotation));
            super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + parkingLength, basePosition.getY() + halfParkingWith), rotation));
        } else { // ParkingType.PARKING_90 
            super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + halfParkingWith, basePosition.getY()), rotation));
            super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + halfParkingWith, basePosition.getY() + parkingLength), rotation));
        }
    }

    public ParkingType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " ParkingType: " + type.toString();
    }
}
