package hu.nik.project.environment.objects;
import hu.nik.project.environment.ScenePoint;

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

    public ParkingSign(ScenePoint basePosition, int rotation, ParkingSignType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public ParkingSignType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " ParkingSignType: " + type.toString();
    }
}
