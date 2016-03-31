package ParkingPilot.Util;

import ParkingPilot.Model.SearchParkingPlace;

import java.awt.*;
import java.util.List;

/**
 * Created by haxxi on 2016.03.31..
 */
public class Calculator {

    private Point[] carPosition;
    private Point[] environment1Position; //Front-left, Front-right, Bottom-left, Bottom-right
    private Point[] environment2Position;
    private int parkingPlaceType;

    private int[][] parkingMatrix;
    private float carAngle;
    private Point[] parkingPlace;
    private List<Point> path;
    private float bottomFrontDistance;
    private float leftRightDistance;

    private int HORIZONTAL_WIDTH = 4;

    public Calculator(Point[] carPosition, Point[] environment1Position, Point[] environment2Position, int parkingPlaceType) {
        this.carPosition = carPosition;
        this.environment1Position = environment1Position;
        this.environment2Position = environment2Position;
        this.parkingPlaceType = parkingPlaceType;
    }

    public List<Point> doCalculate(){
        init();
        return path;
    }

    private void init(){
        //vertical parking
        if(parkingPlaceType == SearchParkingPlace.HORIZONTAL) {
            int freeDistance = environment2Position[0].y - environment1Position[2].y;
            bottomFrontDistance = (freeDistance - (carPosition[0].y + carPosition[2].y)) / 2; // distance front of the car and bottom of the car
            leftRightDistance = (HORIZONTAL_WIDTH - carPosition[1].x - carPosition[0].x) / 2;
            parkingMatrix = new int[HORIZONTAL_WIDTH * 100][freeDistance * 100];
        }

    }
}
