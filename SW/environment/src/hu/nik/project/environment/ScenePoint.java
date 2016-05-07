package hu.nik.project.environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Róbert on 2016.03.14..
 *
 * Base point class for positions
 */
public class ScenePoint implements Serializable {

    private int x;
    private int y;

    public ScenePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return ("X: " + x + " Y: " + y);
    }

    public static ScenePoint rotatePointAroundPoint(ScenePoint base, ScenePoint point, int rotation) {

        if (base == null || point == null) return null;

        double rotationInRadian = (-rotation) * (Math.PI/180);

        double newPositionX = base.getX() + (point.getX()-base.getX())*Math.cos(rotationInRadian) - (point.getY()-base.getY())*Math.sin(rotationInRadian);
        double newPositionY = base.getY() + (point.getX()-base.getX())*Math.sin(rotationInRadian) + (point.getY()-base.getY())*Math.cos(rotationInRadian);

        return new ScenePoint((int)Math.round(newPositionX), (int)Math.round(newPositionY));
    }

    public static ScenePoint getPointByRotationAndRadius(ScenePoint reference, int rotation, int radius) {
        double rotationInRadian = (-rotation) * (Math.PI/180);

        double x = reference.getX() + radius * Math.cos(rotationInRadian);
        double y = reference.getY() + radius * Math.sin(rotationInRadian);

        return new ScenePoint((int)Math.round(x), (int)Math.round(y));
    }

    public static boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance, ScenePoint point ) {
        if (observerBase==null || point==null) return false;
        if (viewAngle<=0) return false;
        if (viewDistance<0) return false; // viewDistance equals zero is meaning the infinite
        double SPx = point.getX() - observerBase.getX();
        double SPy = point.getY() - observerBase.getY();
        if ((viewDistance > 0) && (Math.sqrt( (SPx*SPx) + (SPy*SPy) ) > viewDistance)) return false; // not visible because out of view-distance
        double viewAngleOfSP;
        if (SPx == 0) { // special cases
            if (SPy == 0) return false; // observer and object on same place
            if (SPy > 0)
                viewAngleOfSP = 270;     // point is above the observer
            else
                viewAngleOfSP = 90;    // point is below the observer
        }
        else {  // normal, but four different cases (4 regions of the coordinate-system)
            if (SPx > 0 && SPy < 0) // in t1 (+,-)
                viewAngleOfSP = Math.toDegrees(Math.atan(-SPy / SPx));
            else if (SPx < 0 && SPy < 0) // in t2 (-,-)
                viewAngleOfSP = 180 - Math.toDegrees(Math.atan(-SPy / -SPx));
            else if (SPx < 0 && SPy > 0) // in t3 (-,+)
                viewAngleOfSP = 180 + Math.toDegrees(Math.atan(SPy / -SPx));
            else // in t4 (+,+)
                viewAngleOfSP = 360 - Math.toDegrees(Math.atan(SPy / SPx));
        }
        if (viewAngleOfSP == 360) viewAngleOfSP = 0;
        // viewAngleOfSP between 0 and 359.9999... degrees

        // I'll shift all degree-values with 1000 to positive distance
        // so we will work with only positive numbers!!!

        // viewAngleOfSP will be normalized into interval between -180 and +180 and shifted with +1000
        if ( viewAngleOfSP >= 180 ) viewAngleOfSP = viewAngleOfSP - 360;
        viewAngleOfSP = viewAngleOfSP + 1000; // viewAngleOfSP between 1000-180 and 1000+180 degrees

        // +/- half-viewangle = value of enabled divergence from observerRotation (between 0 and +180)
        double halfViewAngle;
        if ( Math.abs(viewAngle) > 360)
            halfViewAngle = ((double)(viewAngle % 360)) / 2;
        else
            halfViewAngle = ((double)viewAngle) / 2;
        // halfViewAngle always positive number
        if ( halfViewAngle < 0 ) halfViewAngle = -halfViewAngle; // Math.abs(halfViewAngle)

        // rotation also will be normalized into interval between -180 and +180 and shifted with +1000
        double rotation;
        if ( Math.abs(observerRotation) > 360 )
            rotation = (observerRotation % 360);
        else
            rotation = observerRotation;

        if ( rotation < 0 ) rotation = 360 + rotation; // 360 - Math.abs(rotation)
        if ( rotation >= 180 ) rotation = rotation - 360;

        rotation = rotation + 1000; // SHIFT: rotation between 1000-180 and 1000+180 degrees

        // min-angle and max-angle are relative to rotation pro and contra
        double minAngle = rotation - halfViewAngle;
        double maxAngle = rotation + halfViewAngle;

        if ( viewAngleOfSP > maxAngle ) return false; // not visible because above max-angle
        return viewAngleOfSP >= minAngle;
    }

    // implementation of Serializable interface //////////////////////////////////////////////////////////////

    //private static final long serialVersionUID = 4610859544908152108L;

    //private void validateState() {
        //  throw new IllegalArgumentException("...") if dome of data-format error occurs
    //}

    //private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
    //    publicReadObject(aInputStream);
    //}

    public void publicReadObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        x = aInputStream.readInt();
        y = aInputStream.readInt();
        //ensure that object state has not been corrupted or tampered with maliciously
        //validateState();
    }

    //private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
    //    publicWriteObject(aOutputStream);
    //}

    public void publicWriteObject(ObjectOutputStream aOutputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.writeInt(x);
        aOutputStream.writeInt(y);
    }

    public static boolean isPointInARectangle(ScenePoint point, ScenePoint rectLowPoint, ScenePoint rectHighPoint) {
        return (point.getX() >= rectLowPoint.getX()) && (point.getY() >= rectLowPoint.getY()) &&
                (point.getX() <= rectHighPoint.getX()) && (point.getY() <= rectHighPoint.getY());
    }
}
