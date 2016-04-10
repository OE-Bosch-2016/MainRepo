package parkingPilot2.Model;

import java.awt.*;

/**
 * Created by haxxi on 2016.03.30..
 */
public class Parking {

    private Point[] car1;
    private Point[] car2;
    private float edgeOfStreet;
    private int parkingType;

    public Parking(Point[] car1, Point[] car2, float edgeOfStreet) {
        this.car1 = car1;
        this.car2 = car2;
        this.edgeOfStreet = edgeOfStreet;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public Point[] getCar1() {
        return car1;
    }

    public Point[] getCar2() {
        return car2;
    }

    public float getEdgeOfStreet() {
        return edgeOfStreet;
    }

    public int getParkingType() {
        return parkingType;
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setParkingType(int parkingType) {
        this.parkingType = parkingType;
    }
}
