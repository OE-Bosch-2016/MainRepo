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

    private static final int parking_0_Width = 160;
    private static final int parking_0_Height = 615;
    private static final int parking_90_Width = 295;
    private static final int parking_90_Height = 465;
    private static final int parkingSlotWidth = 74;
    private static final int parkingSlotLength = 116;

    public Parking(ScenePoint basePosition, int rotation, ParkingType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;

        int halfParkingWith = parkingSlotWidth / 2;

        if (type == ParkingType.PARKING_0) {
            super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX(), basePosition.getY() + halfParkingWith), rotation));
            super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + parkingSlotLength, basePosition.getY() + halfParkingWith), rotation));
        } else { // ParkingType.PARKING_90 
            super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + halfParkingWith, basePosition.getY()), rotation));
            super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + halfParkingWith, basePosition.getY() + parkingSlotLength), rotation));
        }
    }

    public ParkingType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " ParkingType: " + type.toString();
    }

    public boolean isPointOnTheRoad(ScenePoint point) {
        ScenePoint bottomRightPoint;
        if (type == ParkingType.PARKING_0)
            bottomRightPoint = new ScenePoint(getBasePosition().getX() + parking_0_Width, getBasePosition().getY() + parking_0_Height);
        else
            bottomRightPoint = new ScenePoint(getBasePosition().getX() + parking_90_Width, getBasePosition().getY() + parking_90_Height);

        ScenePoint nullRotatedPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), point, 360 - getRotation());

        return ( ScenePoint.isPointInARectangle(nullRotatedPoint, getBasePosition(), bottomRightPoint) );
    }
}
