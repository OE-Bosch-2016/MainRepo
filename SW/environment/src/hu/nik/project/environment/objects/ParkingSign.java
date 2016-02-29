package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Parking signs
 */
public class ParkingSign extends Sign {

    public enum ParkingSignType  {
        PARKING_LEFT,
        PARKING_RIGHT
    }

    private ParkingSignType type;

    public ParkingSign(int positionX, int positionY, double rotation, ParkingSignType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public ParkingSignType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " ParkingSignType: " + type.toString();
    }
}
