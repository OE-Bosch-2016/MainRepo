package ParkingPilot.Model;

import java.awt.*;
import java.util.List;

/**
 * Created by haxxi on 2016.03.30..
 */
public class Parking {

    private List<Point> car1;
    private List<Point> car2;
    private float edgeOfStreet;

    public Parking(List<Point> car1, List<Point> car2, float edgeOfStreet) {
        this.car1 = car1;
        this.car2 = car2;
        this.edgeOfStreet = edgeOfStreet;
    }

    //Getter -----------------------------------------------------------------------------------------------------------
    public List<Point> getCar1() {
        return car1;
    }

    public List<Point> getCar2() {
        return car2;
    }

    public float getEdgeOfStreet() {
        return edgeOfStreet;
    }
}
