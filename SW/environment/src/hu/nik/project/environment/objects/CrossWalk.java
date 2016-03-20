package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Crosswalks
 */
public class CrossWalk extends Road {

    private static final int crossWalkHeight = 190;

    public enum CrossWalkType {
        CROSSWALK_5
    }

    private CrossWalkType type;

    public CrossWalk(ScenePoint basePosition, int rotation, CrossWalkType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;

        ScenePoint topPoint = new ScenePoint(basePosition.getX() + getTrackWidth(), basePosition.getY());

        super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, topPoint, rotation));
        super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(topPoint.getX(), topPoint.getY() + crossWalkHeight), rotation));
    }

    public CrossWalkType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " CrossWalkType: " + type.toString();
    }
}
