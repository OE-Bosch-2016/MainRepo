package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.24..
 *
 * Base class for the scene objects
 */
public abstract class SceneObject<T> {
    private ScenePoint basePosition;
    private int rotation;

    public SceneObject(ScenePoint basePosition, int rotation) throws SceneObjectException{

        if (basePosition.getX() >=0 && basePosition.getY()>=0) {
            this.basePosition = new ScenePoint(basePosition.getX(), basePosition.getY());
        } else
            throw new SceneObjectException("The X or Y position can't be negative! Actual values are: X = " + basePosition.getX() + " Y = " + basePosition.getY());

        if ((rotation >= 0) && (rotation <= 360))
            this.rotation = rotation;
        else
            throw new SceneObjectException("The rotation range have to be (0,360)! Actual value is: " + rotation);
    }

    abstract public T getObjectType();

    public ScenePoint getBasePosition() { return basePosition; }
    public double getRotation() {
        return rotation;
    }

    // override it if another scene-points are importants
    public abstract boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance );

    public String toString() {
        return "ClassType: " + getClass().getSimpleName() + " -> " + " Position X: " + basePosition.getX() + " Position Y: " + basePosition.getY() + " Rotation: " + rotation;
    }
}
