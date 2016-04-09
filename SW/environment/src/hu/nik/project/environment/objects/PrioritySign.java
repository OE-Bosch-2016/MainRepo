package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Priority sign
 */
public class PrioritySign extends Sign implements Serializable {

    public enum PrioritySignType  {
        GIVEAWAY,
        STOP,
        PRIORITY_ROAD
    }

    private PrioritySignType type;

    public PrioritySign(ScenePoint basePosition, int rotation, PrioritySignType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public PrioritySignType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " PrioritySignType: " + type.toString();
    }
}
