package hu.nik.project.environment.objects;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Roads
 */
public class SimpleRoad extends Road {


    public enum SimpleRoadType  {
        SIMPLE_STRAIGHT
    }

    private SimpleRoadType type;

    public SimpleRoad(ScenePoint basePosition, int rotation, SimpleRoadType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;


        super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + getTrackWidth(), basePosition.getY()), rotation));
        super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + getTrackWidth(), basePosition.getY() + getTrackWidth() * 2), rotation));
    }

    public SimpleRoadType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " SimpleRoadType: " + type.toString();
    }

    @Override
    public boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance ) {
        return ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getTopPoint()) ||
                ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getBottomPoint() );
    }
}
