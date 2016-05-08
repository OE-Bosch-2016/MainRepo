package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.04.12..
 */
public class Car extends SceneObject {

    private static final int carHeight = 240;
    private static final int carWidth = 100;

    private ScenePoint centerPoint;
    private ScenePoint topPoint;
    private ScenePoint bottomPoint;
    private ScenePoint leftPoint;
    private ScenePoint rightPoint;

    public enum CarType {
        CAR
    }

    private CarType type;

    public Car(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = CarType.CAR;
        topPoint = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + carWidth / 2, basePosition.getY()), rotation);
        bottomPoint = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + carWidth / 2, basePosition.getY() + carHeight), rotation);
        leftPoint = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX(), basePosition.getY() + carHeight / 2), rotation);
        rightPoint = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + carWidth, basePosition.getY() + carHeight / 2), rotation);
        centerPoint = ScenePoint.rotatePointAroundPoint(basePosition, new ScenePoint(basePosition.getX() + carWidth / 2, basePosition.getY() + carHeight / 2), rotation);
    }

    @Override
    public boolean isVisibleFromObserver(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance ) {
        return ScenePoint.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance, getBasePosition()) ||
                ScenePoint.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance, getTopPoint()) ||
                ScenePoint.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance, getBottomPoint()) ||
                ScenePoint.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance, getLeftPoint()) ||
                ScenePoint.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance, getRightPoint()
                );
    }

    public ScenePoint getTopPoint() { return this.topPoint; }
    public ScenePoint getBottomPoint() { return this.bottomPoint; }
    public ScenePoint getLeftPoint() { return this.leftPoint; }
    public ScenePoint getRightPoint() { return this.rightPoint; }
    public ScenePoint getCenterPoint() { return this.centerPoint; }

    public CarType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " CarType: " + type.toString();
    }
}
