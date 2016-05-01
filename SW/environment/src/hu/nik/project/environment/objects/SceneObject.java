package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.24..
 *
 * Base class for the scene objects
 */
public abstract class SceneObject<T> implements Serializable {

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
    public int getRotation() {
        return rotation;
    }

    // override it if another scene-points are importants
    public abstract boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance );

    public String toString() {
        return "ClassType: " + getClass().getSimpleName() + " -> " + " Position X: " + basePosition.getX() + " Position Y: " + basePosition.getY() + " Rotation: " + rotation;
    }

    // implementation of Serializable interface //////////////////////////////////////////////////////////////

    private static final long serialVersionUID = -6826276751836292310L;

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        publicReadObject(aInputStream);
    }

    public void publicReadObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        basePosition = (ScenePoint)aInputStream.readObject();
        //basePosition = new ScenePoint(0,0);
        //basePosition.publicReadObject(aInputStream);
        rotation = aInputStream.readInt();
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        publicWriteObject(aOutputStream);
    }

    public void publicWriteObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeObject(basePosition);
        //basePosition.publicWriteObject(aOutputStream);
        aOutputStream.writeInt(rotation);
    }

    protected void setBasePositonAndRotation(ScenePoint position, int rotation) {
        this.basePosition = position;
        this.rotation = rotation;
    }

}
