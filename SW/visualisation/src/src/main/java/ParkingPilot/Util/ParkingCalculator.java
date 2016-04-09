package ParkingPilot.Util;

import ParkingPilot.Model.SearchParkingPlace;

import java.awt.*;
import java.util.List;

/**
 * Created by haxxi on 2016.03.31..
 */
public class ParkingCalculator {

    private Point[] carPosition; //Front-left, Front-right, Bottom-left, Bottom-right
    private Point[] environment1Position; // behind
    private Point[] environment2Position; //front
    private int parkingPlaceType;
    private float edgeOfStreet;

    private int[][] parkingMatrix;
    private float carAngle;
    private Point[] parkingPlace;
    private List<Point> path;
    private float bottomFrontDistance;
    private float leftRightDistance;
    int freeDistance;
    private int state;

    private int HORIZONTAL_WIDTH = 100;

    public int MODIFY_HORIZONTAL = 0;
    public int MODIFY_VERTICAL_LEFT = 1;
    public int MODIFY_VERTICAL_RIGHT = 2;

    private OnParkingListener parkingListener;

    public ParkingCalculator() {
        state = 0;
    }

    public void init(Point[] carPosition, Point[] environment1Position, Point[] environment2Position, float edgeOfStreet, int parkingPlaceType) {
        // New data
        this.carPosition = carPosition;
        this.environment1Position = environment1Position;
        this.environment2Position = environment2Position;
        this.parkingPlaceType = parkingPlaceType;
        this.edgeOfStreet = edgeOfStreet;

        //vertical parking
        if (parkingPlaceType == SearchParkingPlace.HORIZONTAL) {
            freeDistance = environment2Position[0].y - environment1Position[2].y;
            bottomFrontDistance = (freeDistance - (carPosition[0].y + carPosition[2].y)) / 2; // distance front of the car and bottom of the car
            leftRightDistance = (HORIZONTAL_WIDTH - carPosition[1].x - carPosition[0].x) / 2;
            parkingMatrix = new int[HORIZONTAL_WIDTH + carPosition[1].x * 100][freeDistance * 100];
        }
        carAngle = 0;
    }

    public void parking() {
        if (state == 0)
            degreeOf45(-45);
        if (state == 1)
            goBottom();
        if (state == 2)
            degreeOf0(0);
    }

    private void degreeOf45(int degree) {
        carAngle -= 1;
        if (carAngle == degree)
            state++;
        if (parkingListener != null)
            parkingListener.changePosition(0.3f, 0.2f, carAngle);
    }

    private void goBottom() {
        if (edgeOfStreet > 1) {
            edgeOfStreet--;
            if (parkingListener != null)
                parkingListener.changePosition(0.5f, 0.5f);
        } else
            state++;
    }

    private void degreeOf0(int degree) {
        carAngle += 1;
        if (carAngle == degree)
            state++;
        if (parkingListener != null)
            parkingListener.changePosition(0.3f, 0.2f, carAngle);
    }
    // Setter ----------------------------------------------------------------------------------------------------------
    public void setParkingListener(OnParkingListener parkingListener) {
        this.parkingListener = parkingListener;
    }

    // Interface -------------------------------------------------------------------------------------------------------
    public interface OnParkingListener {
        void changePosition(float front, float side, float rotate);

        void changePosition(float front, float side);
    }
}
