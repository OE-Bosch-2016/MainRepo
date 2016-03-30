package ParkingPilot.Manager;

import ParkingPilot.Model.Parking;
import javafx.scene.effect.Light;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;

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
            List<Point> list1 = new ArrayList();
            List<Point> list2 = new ArrayList();
            list1.add(new Point(1,1));
            list1.add(new Point(2,1));

            list2.add(new Point(1,6));
            list2.add(new Point(2,6));
            parking = new Parking(list1, list2, 3.5f);
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
