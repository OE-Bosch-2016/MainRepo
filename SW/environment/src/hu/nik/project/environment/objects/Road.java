package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Road
 */
abstract public class Road<T> extends SceneObject implements Serializable {

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

    // implementation of Serializable interface //////////////////////////////////////////////////////////////

    private static final long serialVersionUID = 1536627736237490631L;

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        publicReadObject(aInputStream);
        super.publicReadObject(aInputStream);
    }

    public void publicReadObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        topPoint = (ScenePoint)aInputStream.readObject();
        bottomPoint = (ScenePoint)aInputStream.readObject();
        //topPoint = new ScenePoint(0,0);
        //topPoint.publicReadObject(aInputStream);
        //bottomPoint = new ScenePoint(0,0);
        //bottomPoint.publicReadObject(aInputStream);
        super.publicReadObject(aInputStream);
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        publicWriteObject(aOutputStream);
        super.publicWriteObject(aOutputStream);
    }

    public void publicWriteObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeObject(topPoint);
        aOutputStream.writeObject(bottomPoint);
        //topPoint.publicWriteObject(aOutputStream);
        //bottomPoint.publicWriteObject(aOutputStream);
        super.publicWriteObject(aOutputStream);
    }

    public abstract boolean isPointOnTheRoad(ScenePoint point);
}
