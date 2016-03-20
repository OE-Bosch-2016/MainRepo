package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Road
 */
abstract public class Road<T> extends SceneObject {

    private ScenePoint topPoint;
    private ScenePoint bottomPoint;
    private static final int trackWidth = 175;

    public Road(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);
    }

    public abstract T getObjectType();

    protected void setTopPoint(ScenePoint point) {
        topPoint = point;
    }

    protected void setBottomPoint(ScenePoint point) {
        bottomPoint = point;
    }

    public ScenePoint getTopPoint() {
        return topPoint;
    }

    public ScenePoint getBottomPoint() {
        return bottomPoint;
    }

    public static int getTrackWidth() {
        return trackWidth;
    }

    @Override
    public boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance ) {
        return ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getTopPoint()) ||
                ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getBottomPoint());
    }
}
