package ParkingPilot.Model;

import javafx.scene.effect.Light;

import java.util.List;

/**
 * Created by haxxi on 2016.03.30..
 */
public class Parking {

    private List<Light.Point> car1;
    private List<Light.Point> car2;
    private float edgeOfStreet;

    public Parking(List<Light.Point> car1, List<Light.Point> car2, float edgeOfStreet) {
        this.car1 = car1;
        this.car2 = car2;
        this.edgeOfStreet = edgeOfStreet;
    }

    //Getter -----------------------------------------------------------------------------------------------------------
    public List<Light.Point> getCar1() {
        return car1;
    }

    public List<Light.Point> getCar2() {
        return car2;
    }

    public float getEdgeOfStreet() {
        return edgeOfStreet;
    }
}
