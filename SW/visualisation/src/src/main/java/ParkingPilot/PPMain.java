package ParkingPilot;

import ParkingPilot.Manager.PPManager;
import ParkingPilot.Model.Parking;
import ParkingPilot.Util.Calculator;
import java.awt.*;

/**
 * Created by haxxi on 2016.03.30..
 */
public class PPMain {

    private PPManager manager;
    private Calculator calculator;

    public PPMain() {
        manager = PPManager.newInstance();
        manager.setSenderListener(parkingListener);
        calculator = new Calculator();
    }

    public void parkingPilotActivate(){
        manager.sendPPData(300, 180f, new Point(100,50));
    }

    // Listener --------------------------------------------------------------------------------------------------------
    private PPManager.ParkingPilotListener parkingListener = new PPManager.ParkingPilotListener() {
        public void onDataChanged() {
            Parking parking = manager.getParking();
            Point[] car = new Point[4];
            car[0] = new Point(300, 150);
            car[1] = new Point(300, 50);
            car[2] = new Point(100, 150);
            car[3] = new Point(100, 50);
            calculator.parking(car, parking.getCar1(), parking.getCar2(), parking.getEdgeOfStreet(), calculator.MODIFY_HORIZONTAL);
        }
    };

}
