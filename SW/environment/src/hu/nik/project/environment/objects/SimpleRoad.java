package hu.nik.project.environment.objects;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Roads
 */
public class SimpleRoad extends Road implements Serializable {

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

    // implementation of Serializable interface //////////////////////////////////////////////////////////////

    private static final long serialVersionUID = 2205482964293623424L;

    private void validateState() {
        //  throw new IllegalArgumentException("...") if dome of data-format error occurs
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        publicReadObject(aInputStream);
    }

    public void publicReadObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        int i = aInputStream.readInt();
        type = SimpleRoadType.values()[i];
        //ensure that object state has not been corrupted or tampered with maliciously
        validateState();
        super.publicReadObject(aInputStream);
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        publicWriteObject(aOutputStream);
    }

    public void publicWriteObject(ObjectOutputStream aOutputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.writeInt(type.ordinal());
        super.publicWriteObject(aOutputStream);
    }

    public boolean isPointOnTheRoad(ScenePoint point) {
        ScenePoint bottomRightPoint = new ScenePoint(getBasePosition().getX() + getTrackWidth() * 2, getBasePosition().getY() + getTrackWidth() * 2);
        ScenePoint nullRotatedPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), point, 360 - getRotation());

        return ( ScenePoint.isPointInARectangle(nullRotatedPoint, getBasePosition(), bottomRightPoint) );
    }
}
