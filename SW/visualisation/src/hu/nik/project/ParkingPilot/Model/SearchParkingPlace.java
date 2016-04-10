package ParkingPilot.Model;

import java.awt.*;
import java.util.List;

/**
 * Created by haxxi on 2016.03.31..
 */
public class SearchParkingPlace extends Parking {

    public static int VERTICAL = 0;
    public static int HORIZONTAL = 1;

    private int typeOfParkingPlace;

    public SearchParkingPlace(Point[] car1, Point[] car2, float edgeOfStreet) {
        super(car1, car2, edgeOfStreet);
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public int getTypeOfParkingPlace() {
        return typeOfParkingPlace;
    }

    public float currentDistance(){
        //TODO: actual distance between 2 cars
        return 3f;
    }
}
