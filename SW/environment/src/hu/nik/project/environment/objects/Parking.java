package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for parking
 */
public class Parking extends Misc {

    public enum ParkingType {
        PARKING_0,
        PARKING_90,
        PARKING_BOLLARD
    }

    private ParkingType type;

    public Parking(int positionX, int positionY, int rotation, ParkingType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public ParkingType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " ParkingType: " + type.toString();
    }
}
