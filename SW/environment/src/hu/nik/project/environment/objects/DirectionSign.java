package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for direction signs
 */
public class DirectionSign extends Sign implements Serializable {

    public enum DirectionType  {
        FORWARD,
        LEFT,
        RIGHT,
        FORWARD_LEFT,
        FORWARD_RIGHT,
        ROUNDABOUT
    }

    private DirectionType type;

    public DirectionSign(ScenePoint basePosition, int rotation, DirectionType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public DirectionType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " DirectionType: " + type.toString();
    }
}
