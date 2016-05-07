package hu.nik.project.environment.objects;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.03.14..
 *
 * * Class for curved roads
 */
public class CurvedRoad extends Road implements Serializable {

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
    private int radius = 350;

    public CurvedRoad(ScenePoint basePosition, int rotation, CurvedRoadType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;

        ScenePoint topPoint = null;
        ScenePoint bottomPoint = null;

        int pointNewX = 0;
        switch (type) {
            case SIMPLE_45_LEFT:
            case SIMPLE_65_LEFT:
            case SIMPLE_90_LEFT:
                pointNewX = (basePosition.getX() - getTrackWidth());
                bottomPoint = new ScenePoint((basePosition.getX() + getTrackWidth()), basePosition.getY());
                break;
            case SIMPLE_45_RIGHT:
            case SIMPLE_65_RIGHT:
            case SIMPLE_90_RIGHT:
                pointNewX = (basePosition.getX() + getTrackWidth());
                bottomPoint = new ScenePoint((basePosition.getX() - getTrackWidth()), basePosition.getY());
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

    private boolean isWithinAngle(ScenePoint point, ScenePoint startPoint, ScenePoint endPoint) {
        double pointAngle = Math.abs(Math.atan2(point.getY(), point.getX()));
        double startAngle = Math.abs(Math.atan2(startPoint.getY(), startPoint.getX()));
        double endAngle = Math.abs(Math.atan2(endPoint.getY(), endPoint.getX()));

        return (startAngle <= pointAngle) && (pointAngle <= endAngle);
    }

    private boolean isWithinRadius(ScenePoint relativePoint) {
        return (relativePoint.getX()*relativePoint.getX() + relativePoint.getY()*relativePoint.getY() <= (radius + 175) * (radius + 175)) &&
                (relativePoint.getX()*relativePoint.getX() + relativePoint.getY()*relativePoint.getY() >= radius / 2 * radius / 2);
    }

    public boolean isPointOnTheRoad(ScenePoint point) {
        ScenePoint nullRotatedPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), point, 360 - getRotation());
        ScenePoint nullRotatedReferencePoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), referencePoint, 360 - getRotation());
        ScenePoint relativePoint = new ScenePoint(nullRotatedPoint.getX() - nullRotatedReferencePoint.getX(), nullRotatedPoint.getY() - nullRotatedReferencePoint.getY());

        int rotation = 0;

        switch (type) {
            case SIMPLE_45_LEFT:
            case SIMPLE_45_RIGHT: {
                rotation = 45;
                break;
            }
            case SIMPLE_65_LEFT:
            case SIMPLE_65_RIGHT: {
                rotation = 65;
                break;
            }
            case SIMPLE_90_LEFT:
            case SIMPLE_90_RIGHT: {
                rotation = 90;
                break;
            }
        }

        ScenePoint relativeStart = null;
        ScenePoint relativeEnd = null;

        switch (type) {
            case SIMPLE_45_LEFT:
            case SIMPLE_65_LEFT:
            case SIMPLE_90_LEFT: {
                relativeStart = new ScenePoint((nullRotatedReferencePoint.getX() + (175 * 3)) - nullRotatedReferencePoint.getX(), 0);
                relativeEnd = ScenePoint.rotatePointAroundPoint(new ScenePoint(0, 0), relativeStart, rotation);
                break;
            }

            case SIMPLE_45_RIGHT:
            case SIMPLE_65_RIGHT:
            case SIMPLE_90_RIGHT: {
                relativeEnd = new ScenePoint((nullRotatedReferencePoint.getX() - (175 * 3)) - nullRotatedReferencePoint.getX(), 0);
                relativeStart = ScenePoint.rotatePointAroundPoint(new ScenePoint(0, 0), relativeEnd, 360 - rotation);
                break;
            }
        }

        return (isWithinAngle(relativePoint, relativeStart, relativeEnd) && isWithinRadius(relativePoint));
    }
}
