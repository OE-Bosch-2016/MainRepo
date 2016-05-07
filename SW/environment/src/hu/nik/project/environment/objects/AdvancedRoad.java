package hu.nik.project.environment.objects;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Advanced roads
 */
public class AdvancedRoad extends Road implements Serializable {

    private ScenePoint leftPoint;
    private ScenePoint rightPoint;

    public enum AdvancedRoadType {
        CROSSROADS,
        ROTARY,
        JUNCTIONLEFT,
        JUNCTIONRIGHT
    }

    private AdvancedRoadType type;

    public AdvancedRoad(ScenePoint basePosition, int rotation, AdvancedRoadType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;

        ScenePoint topPoint = null;
        ScenePoint bottomPoint = null;

        switch (type) {
            case JUNCTIONLEFT:
                topPoint = new ScenePoint(basePosition.getX() - getTrackWidth(), basePosition.getY());
                leftPoint = new ScenePoint(topPoint.getX() - getTrackWidth() * 4, topPoint.getY() + 4 * getTrackWidth());
                rightPoint = null;
                break;
            case JUNCTIONRIGHT:
                topPoint = new ScenePoint(basePosition.getX() + getTrackWidth(), basePosition.getY());
                rightPoint = new ScenePoint(topPoint.getX() + getTrackWidth() * 4, topPoint.getY() + 4 * getTrackWidth());
                leftPoint = null;
                break;
            case CROSSROADS:
            case ROTARY:
                leftPoint = new ScenePoint(basePosition.getX(), basePosition.getY() - getTrackWidth());
                rightPoint = new ScenePoint(leftPoint.getX() + 8 * getTrackWidth(), leftPoint.getY());
                topPoint = new ScenePoint(leftPoint.getX() + 4 * getTrackWidth(), leftPoint.getY() - 4 * getTrackWidth());

        }

        bottomPoint = new ScenePoint(topPoint.getX(), topPoint.getY() + 8 * getTrackWidth());

        leftPoint = ScenePoint.rotatePointAroundPoint(basePosition, leftPoint, rotation);
        rightPoint = ScenePoint.rotatePointAroundPoint(basePosition, rightPoint, rotation);

        super.setTopPoint(ScenePoint.rotatePointAroundPoint(basePosition, topPoint, rotation));
        super.setBottomPoint(ScenePoint.rotatePointAroundPoint(basePosition, bottomPoint, rotation));
    }

    public AdvancedRoadType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " AdvancedRoadType: " + type.toString();
    }

    public ScenePoint getLeftPoint() {
        return leftPoint;
    }

    public ScenePoint getRightPoint() {
        return rightPoint;
    }

    @Override
    public boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance ) {
        return ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getTopPoint()) ||
                ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getBottomPoint()) ||
                ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getLeftPoint()) ||
                ScenePoint.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance, getRightPoint());
    }

    public boolean isPointOnTheRoad(ScenePoint point) {
        ScenePoint nullRotatedPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), point, 360 - getRotation());
        ScenePoint nullRotatedTopPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), getTopPoint(), 360 - getRotation());
        ScenePoint nullRotatedBottomPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), getBottomPoint(), 360 - getRotation());

        if (type == AdvancedRoadType.ROTARY || type == AdvancedRoadType.CROSSROADS) {
            ScenePoint nullRotatedRightPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), getRightPoint(), 360 - getRotation());

            return (ScenePoint.isPointInARectangle(nullRotatedPoint, new ScenePoint(getBasePosition().getX(), getBasePosition().getY() - 350), new ScenePoint(nullRotatedRightPoint.getX(), nullRotatedRightPoint.getY() + 175)) ||
                    ScenePoint.isPointInARectangle(nullRotatedPoint, new ScenePoint(nullRotatedTopPoint.getX() - 175, nullRotatedTopPoint.getY()), new ScenePoint(nullRotatedBottomPoint.getX() + 175, nullRotatedBottomPoint.getY())));
        }
        else {
            if (type == AdvancedRoadType.JUNCTIONLEFT) {
                ScenePoint nullRotatedLeftPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), getLeftPoint(), 360 - getRotation());

                return (ScenePoint.isPointInARectangle(nullRotatedPoint, new ScenePoint(getBasePosition().getX() - 350, getBasePosition().getY()), new ScenePoint(nullRotatedBottomPoint.getX() + 175, nullRotatedBottomPoint.getY())) ||
                        ScenePoint.isPointInARectangle(nullRotatedPoint, new ScenePoint(nullRotatedLeftPoint.getX(), nullRotatedLeftPoint.getY() - 175), new ScenePoint(getBasePosition().getX(), getBasePosition().getY() + 875)));
            } else {
                if (type == AdvancedRoadType.JUNCTIONRIGHT) {
                    ScenePoint nullRotatedRightPoint = ScenePoint.rotatePointAroundPoint(getBasePosition(), getRightPoint(), 360 - getRotation());

                    return (ScenePoint.isPointInARectangle(nullRotatedPoint, getBasePosition(), new ScenePoint(nullRotatedBottomPoint.getX() + 175, nullRotatedBottomPoint.getY())) ||
                            ScenePoint.isPointInARectangle(nullRotatedPoint, new ScenePoint(getBasePosition().getX(), getBasePosition().getY() + 525), new ScenePoint(nullRotatedRightPoint.getX(), nullRotatedRightPoint.getY() + 175)));
                }
            }
        }
        return false;
    }
}
