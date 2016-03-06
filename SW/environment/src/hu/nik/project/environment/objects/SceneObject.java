package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.24..
 *
 * Base class for the scene objects
 */
public abstract class SceneObject<T> {
    private int positionX;
    private int positionY;
    private int rotation;

    public SceneObject(int positionX, int positionY, int rotation) throws SceneObjectException{

        if (positionX >=0 && positionY>=0) {
            this.positionX = positionX;
            this.positionY = positionY;
        } else
            throw new SceneObjectException("The X or Y position can't be negative! Actual values are: X = " + positionX + " Y = " + positionY);

        if ((rotation >= 0) && (rotation <= 360))
            this.rotation = rotation;
        else
            throw new SceneObjectException("The rotation range have to be (0,360)! Actual value is: " + rotation);
    }

    abstract public T getObjectType();

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public double getRotation() {
        return rotation;
    }

    public String toString() {
        return "ClassType: " + getClass().getSimpleName() + " -> " + " Position X: " + positionX + " Position Y: " + positionY + " Rotation: " + rotation;
    }
}
