package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.24..
 *
 * Base class for the scene objects
 */
public abstract class SceneObject<T> {
    private int positionX;
    private int positionY;
    private double rotation;

    public SceneObject(int positionX, int positionY, double rotation) throws SceneObjectException{
        this.positionX = positionX;
        this.positionY = positionY;

        if ((rotation >= 0) && (rotation <= 360))
            this.rotation = rotation;
        else
            throw new SceneObjectException("The rotation range is (0,360)");
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
