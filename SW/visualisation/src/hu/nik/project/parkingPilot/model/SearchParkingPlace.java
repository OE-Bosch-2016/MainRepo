package hu.nik.project.parkingPilot.model;

import java.io.Serializable;

/**
 * Created by haxxi on 2016.03.31..
 */
public class SearchParkingPlace implements Serializable{

    private double[] distance;

    public SearchParkingPlace(double[] distance) {
        this.distance = distance;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public double[] getDistance() {
        return distance;
    }
}
