package parkingPilot2;

import parkingPilot2.Manager.PPManager;
import parkingPilot2.Model.Parking;
import parkingPilot2.Util.ParkingCalculator;
import utils2.Vector2D;

import java.awt.*;

/**
 * Created by haxxi on 2016.03.30..
 */
public class PPMain {

    private PPManager manager;
    private ParkingCalculator parkingCalculator;
    private Point[] car;

    public PPMain() {
        manager = PPManager.newInstance();
        manager.setSenderListener(parkingListener);
        parkingCalculator = new ParkingCalculator();
    }

    public void parkingPilotActivate(Vector2D point, int height, int width, ParkingCalculator.OnParkingListener parkingListener, int parkingType){
        calculateCorners(point, height, width);
        parkingCalculator.setParkingListener(parkingListener);
        manager.sendPPData(40, 0f, point, parkingType);
    }

    private void calculateCorners(Vector2D point, int height, int width){
        car = new Point[4];
        car[0] = new Point((int)(point.get_coordinateX() - width / 2), (int)(point.get_coordinateY() + height / 2));
        car[1] = new Point((int)(point.get_coordinateX() + width / 2), (int)(point.get_coordinateY() + height / 2));
        car[2] = new Point((int)(point.get_coordinateX() - width / 2), (int)(point.get_coordinateY() - height / 2));
        car[3] = new Point((int)(point.get_coordinateX() + width / 2), (int)(point.get_coordinateY() + height / 2));
    }

    public void doParking(){
        parkingCalculator.parking();
    }

    // Listener --------------------------------------------------------------------------------------------------------
    private PPManager.ParkingPilotListener parkingListener = new PPManager.ParkingPilotListener() {
        public void onDataChanged() {
            Parking parking = manager.getParking();
            parkingCalculator.init(car, parking.getCar1(), parking.getCar2(), parking.getEdgeOfStreet(), parking.getParkingType());
            doParking();
        }
    };

}
