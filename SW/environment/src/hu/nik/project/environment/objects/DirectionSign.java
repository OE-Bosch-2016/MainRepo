package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for direction signs
 */
public class DirectionSign extends Sign {

    public enum DirectionType  {
        FORWARD,
        LEFT,
        RIGHT,
        FORWARD_LEFT,
        FORWARD_RIGHT,
        ROUNDABOUT
    }

    private DirectionType type;

    public DirectionSign(int positionX, int positionY, int rotation, DirectionType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public DirectionType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " DirectionType: " + type.toString();
    }
}
