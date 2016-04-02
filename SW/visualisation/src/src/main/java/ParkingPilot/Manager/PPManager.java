package ParkingPilot.Manager;

import ParkingPilot.Model.Parking;

import java.awt.*;

/**
 * Created by haxxi on 2016.03.30..
 */
public class PPManager {

    // static
    private static PPManager mInstance;

    // listener
    private ParkingPilotListener senderListener;

    // local
    private Parking parking;

    public static PPManager newInstance(){
        if(mInstance != null)
            return mInstance;
        else
            return mInstance = new PPManager();
    }

    public void sendPPData(float distance, float angle, Point coordinate){
        //call Environment, using Bus

        if(senderListener != null) {
            // only test
            Point[] car1 = new Point[4];
            Point[] car2 = new Point[4];
            car1[0] = (new Point(100,100));
            car1[1] = (new Point(100,200));

            car2[2] = (new Point(100,600));
            car2[3] = (new Point(100,800));
            parking = new Parking(car1, car2, 150f);
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

    // Interface -------------------------------------------------------------------------------------------------------
    public interface ParkingPilotListener {
        void onDataChanged();
    }
}
