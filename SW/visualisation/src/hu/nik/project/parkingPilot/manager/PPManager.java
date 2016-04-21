package hu.nik.project.parkingPilot.manager;

import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusException;
import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.parkingPilot.model.Parking;
import hu.nik.project.parkingPilot.util.ParkingCalculator;
import hu.nik.project.ultrasonicsensor.UltrasonicMessagePackage;
import hu.nik.project.utils.Vector2D;
import java.awt.*;

/**
 * Created by haxxi on 2016.03.30..
 */
public class PPManager implements ICommBusDevice {

    // static
    private static PPManager mInstance;
    private static int parkingType;

    // listener
    private ParkingPilotListener senderListener;

    // local
    private Parking parking;
    private CommBusConnector commBusConnector;
    private String stringData = "";

    public static PPManager newInstance() {
        if (mInstance != null)
            return mInstance;
        else
            return mInstance = new PPManager();
    }

    public void sendPPData(Vector2D coordinate, int parkingType) {
        //call Environment, using Bus

        if (senderListener != null) {
            // only test
            if (parkingType == ParkingCalculator.MODIFY_HORIZONTAL) {
                Point[] car1 = new Point[4];
                Point[] car2 = new Point[4];
                car1[0] = (new Point((int) (coordinate.get_coordinateX() + 10), (int) (coordinate.get_coordinateY())));
                car1[1] = (new Point((int) (coordinate.get_coordinateX() + 34), (int) (coordinate.get_coordinateY())));

                car2[2] = (new Point((int) (coordinate.get_coordinateX() + 10), (int) (coordinate.get_coordinateY() + 30)));
                car2[3] = (new Point((int) (coordinate.get_coordinateX() + 34), (int) (coordinate.get_coordinateY() + 30)));
                parking = new Parking(car1, car2, 20);
                parking.setParkingType(ParkingCalculator.MODIFY_HORIZONTAL);
            } else if (parkingType == ParkingCalculator.MODIFY_VERTICAL_LEFT) {
                Point[] car1 = new Point[4];
                Point[] car2 = new Point[4];
                car1[0] = (new Point((int) (coordinate.get_coordinateX()), (int) (coordinate.get_coordinateY() - 10)));
                car1[1] = (new Point((int) (coordinate.get_coordinateX()), (int) (coordinate.get_coordinateY() - 30)));

                car2[2] = (new Point((int) (coordinate.get_coordinateX() + 10), (int) (coordinate.get_coordinateY() - 10)));
                car2[3] = (new Point((int) (coordinate.get_coordinateX() + 10), (int) (coordinate.get_coordinateY() - 30)));
                parking = new Parking(car1, car2, 80);
                parking.setParkingType(ParkingCalculator.MODIFY_VERTICAL_LEFT);
            }
            //

            senderListener.onDataChanged();
        }
    }



    // Getter ----------------------------------------------------------------------------------------------------------
    public Parking getParking() {
        return parking;
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setSenderListener(ParkingPilotListener senderListener) {
        this.senderListener = senderListener;
    }

    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();
        if (dataType == UltrasonicMessagePackage.class) {
            try {
                UltrasonicMessagePackage data = (UltrasonicMessagePackage) commBusConnector.receive();

            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        }
    }

    // Interface -------------------------------------------------------------------------------------------------------
    public interface ParkingPilotListener {
        void onDataChanged();
    }
}
