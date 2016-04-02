package ParkingPilot.Util;

import ParkingPilot.Model.SearchParkingPlace;

import java.awt.*;
import java.util.List;

/**
 * Created by haxxi on 2016.03.31..
 */
public class Calculator {

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

    private int HORIZONTAL_WIDTH = 100;

    public int MODIFY_HORIZONTAL = 0;
    public int MODIFY_VERTICAL_LEFT = 1;
    public int MODIFY_VERTICAL_RIGHT = 2;

    public Calculator() {

    }

    private void init(Point[] carPosition, Point[] environment1Position, Point[] environment2Position, int parkingPlaceType, float edgeOfStreet) {
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

    public void parking(Point[] carPosition, Point[] environment1Position, Point[] environment2Position, float edgeOfStreet, int parkingPlaceType) {
        init(carPosition, environment1Position, environment2Position, parkingPlaceType, edgeOfStreet);

        degreeOf45(45, 1);
        goBottom();
        degreeOf45(0, -1);
        preparePosition();
    }

    private void degreeOf45(int degree, int value) {
        while (carAngle != degree) {
            if (carAngle == 0) {
                carAngle += 5 * value;
                modifyCarPosition(-2 * value, -0.5f * value, MODIFY_HORIZONTAL);
            } else if (carAngle == 15) {
                carAngle += 5 * value;
                modifyCarPosition(-1 * value, -1f * value, MODIFY_HORIZONTAL);
            } else if (carAngle == 30) {
                carAngle += 5 * value;
                modifyCarPosition(-0.5f * value, -2f * value, MODIFY_HORIZONTAL);
            }
        }
    }

    private void goBottom() {
        while(carPosition[2].y - parkingPlace[2].y < 30){
            modifyCarPosition(-0.5f, 0, MODIFY_HORIZONTAL);
        }
    }

    private void preparePosition(){
        while (environment1Position[0].x < carPosition[2].y - freeDistance){
            modifyCarPosition(+1,0,MODIFY_HORIZONTAL);
        }
    }

    private void modifyCarPosition(float value, float value2, int type) {
        if (type == MODIFY_HORIZONTAL) {
            for (int i = 0; i < carPosition.length; i++) {
                if (i < 2)
                    carPosition[i].x += -value2;
                else
                    carPosition[i].x += value2;

                carPosition[i].y += value;
            }
        } else if (type == MODIFY_VERTICAL_LEFT) {
        } else if (type == MODIFY_VERTICAL_RIGHT) {
        }
    }
}
