package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.03.14..
 *
 * * Class for curved roads
 */
public class CurvedRoad extends Road {

    public enum CurvedRoadType  {
        SIMPLE_45_LEFT,
        SIMPLE_45_RIGHT,
        SIMPLE_65_LEFT,
        SIMPLE_65_RIGHT,
        SIMPLE_90_LEFT,
        SIMPLE_90_RIGHT,
    }

    private CurvedRoadType type;
    private ScenePoint referencePoint;
    private int radius;

    public CurvedRoad(ScenePoint basePosition, int rotation, CurvedRoadType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
        radius = 350;
        ScenePoint topPoint = null;
        ScenePoint bottomPoint = null;

        int pointNewX = 0;
        switch (type) {
            case SIMPLE_45_LEFT:
            case SIMPLE_65_LEFT:
            case SIMPLE_90_LEFT:
                pointNewX = (basePosition.getX() - 175);
                bottomPoint = new ScenePoint((basePosition.getX() + 175), basePosition.getY());
                break;
            case SIMPLE_45_RIGHT:
            case SIMPLE_65_RIGHT:
            case SIMPLE_90_RIGHT:
                pointNewX = (basePosition.getX() + 175);
                bottomPoint = new ScenePoint((basePosition.getX() - 175), basePosition.getY());
                break;
        }
        ScenePoint referenceBase = new ScenePoint(pointNewX, basePosition.getY());
        referencePoint = ScenePoint.rotatePointAroundPoint(basePosition, referenceBase, rotation);

        switch (type) {
            case SIMPLE_45_LEFT:
                topPoint = ScenePoint.getPointByRotationAndRadius(referencePoint, 45 + rotation, radius);
                break;
            case SIMPLE_65_LEFT:
                topPoint = ScenePoint.getPointByRotationAndRadius(referencePoint, 65 + rotation, radius);
                break;
            case SIMPLE_45_RIGHT:
                topPoint = ScenePoint.getPointByRotationAndRadius(referencePoint, 90 + 45 + rotation, radius);
                break;
            case SIMPLE_65_RIGHT:
                topPoint = ScenePoint.getPointByRotationAndRadius(referencePoint, 90 + 25 + rotation, radius);
                break;
            case SIMPLE_90_LEFT:
            case SIMPLE_90_RIGHT:
                //topPoint = new ScenePoint(referenceBase.getX(), (referenceBase.getY() - 350));
                topPoint = ScenePoint.getPointByRotationAndRadius(referencePoint, 90 + rotation, radius);
                break;
        }

        //super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, topPoint, rotation));
        super.setTopPoint(topPoint);
        super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, bottomPoint, rotation));
    }

    public CurvedRoadType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " CurvedRoadType: " + type.toString();
    }

    public int getRadius() {
        return radius;
    }

    public ScenePoint getReferencePoint() {
        return referencePoint;
    }
}
