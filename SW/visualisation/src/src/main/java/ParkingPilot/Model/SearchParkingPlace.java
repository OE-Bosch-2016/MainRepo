package ParkingPilot.Model;

import javafx.scene.effect.Light;

import java.util.List;

/**
 * Created by haxxi on 2016.03.31..
 */
public class SearchParkingPlace extends Parking {

    public static int VERTICAL = 0;
    public static int HORIZONTAL = 1;

    private int typeOfParkingPlace;

    public SearchParkingPlace(List<Light.Point> car1, List<Light.Point> car2, float edgeOfStreet) {
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
