package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Crosswalks
 */
public class CrossWalk extends Misc {

    private static final int crossWalkHeight = 190;

    public enum CrossWalkType {
        CROSSWALK_5
    }

    private CrossWalkType type;

    public CrossWalk(ScenePoint basePosition, int rotation, CrossWalkType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public CrossWalkType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " CrossWalkType: " + type.toString();
    }
}
