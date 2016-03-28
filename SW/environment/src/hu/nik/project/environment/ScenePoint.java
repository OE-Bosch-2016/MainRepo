package hu.nik.project.environment;

/**
 * Created by Róbert on 2016.03.14..
 *
 * Base point class for positions
 */
public class ScenePoint {
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

        viewAngleOfSP = viewAngleOfSP + 1000; // SHIFT: viewAngleOfSP between 1000 and 1360 degrees

        double halfViewAngle; // +/- half-viewangle = value of enabled divergence from observerRotation
        if ( viewAngle > 360)
            halfViewAngle = ((double)(viewAngle % 360)) / 2;
        else
            halfViewAngle = ((double)viewAngle) / 2;

        double rotation = observerRotation + 1000; // SHIFT: rotation between 1000 and 1360 degrees

        double minAngle = rotation - halfViewAngle;
        double maxAngle = rotation + halfViewAngle;

        if ( viewAngleOfSP > maxAngle ) return false; // not visible because above max-angle
        return viewAngleOfSP >= minAngle;
    }

}
