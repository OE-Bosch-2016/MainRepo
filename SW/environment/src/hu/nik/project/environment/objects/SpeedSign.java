package hu.nik.project.environment.objects;
import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Speed signs
 */
public class SpeedSign extends Sign {

    public enum SpeedSignType  {
        LIMIT_10,
        LIMIT_20,
        LIMIT_40,
        LIMIT_50,
        LIMIT_70,
        LIMIT_90,
        LIMIT_100
    }

    private SpeedSignType type;

    public SpeedSign(ScenePoint basePosition, int rotation, SpeedSignType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public SpeedSignType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " SpeedSignType: " + type.toString();
    }
}
