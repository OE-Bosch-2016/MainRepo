package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Misc
 */
abstract public class Misc<T> extends SceneObject {
    private ScenePoint center;
    private static final int miscWidthAndHeight = 80;

    public Misc(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);
        center = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + miscWidthAndHeight / 2, basePosition.getY() + miscWidthAndHeight / 2), rotation);
    }

    public abstract T getObjectType();

    public ScenePoint getCenter() {
        return center;
    }

    public static int getMiscWidthAndHeight() {
        return miscWidthAndHeight;
    }

    @Override
    public boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance ) {
        return ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getCenter() );
    }
}
