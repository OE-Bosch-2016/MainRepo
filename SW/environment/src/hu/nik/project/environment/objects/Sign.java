package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract Class for Scene Objects
 */
abstract public class Sign<T> extends SceneObject {

    private ScenePoint center;
    private static final int signWidthAndHeight = 80;

    public Sign(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);

        center = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + signWidthAndHeight / 2, basePosition.getY() + signWidthAndHeight / 2), rotation);
    }

    public abstract T getObjectType();

    public ScenePoint getCenter() {
        return center;
    }
}
