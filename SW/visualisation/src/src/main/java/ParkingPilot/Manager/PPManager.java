package ParkingPilot.Manager;

import ParkingPilot.Model.Parking;
import java.util.List;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by haxxi on 2016.03.30..
 */
public class PPManager {

    // static
    private static PPManager mInstance;

    // listener
    private ParkinPilotListener senderListener;

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
            car1[0] = (new Point(1,1));
            car1[1] = (new Point(2,1));

            car2[2] = (new Point(1,6));
            car2[3] = (new Point(2,6));
            parking = new Parking(car1, car2, 3.5f);
            //

            senderListener.onDataChanged();
        }
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setSenderListener(ParkinPilotListener senderListener) {
        this.senderListener = senderListener;
    }

    // Interface -------------------------------------------------------------------------------------------------------
    public interface ParkinPilotListener{
        void onDataChanged();
    }
}
