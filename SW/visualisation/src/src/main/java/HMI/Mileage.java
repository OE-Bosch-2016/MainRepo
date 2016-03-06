package HMI;

import javax.swing.*;

/**
 * Created by haxxi on 2016.03.04..
 */
public class Mileage implements IMileage {

    public static String UNIT = " km/h";

    OnMileAgeListener mileAgeListener;

    public void mileage(float mile) {
        if (mileAgeListener != null)
            mileAgeListener.changed(mile);
        else
            JOptionPane.showMessageDialog(null, "MilaAgeListener missing!");
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setMileAgeListener(OnMileAgeListener mileAgeListener) {
        this.mileAgeListener = mileAgeListener;
    }

    // Listener --------------------------------------------------------------------------------------------------------
    public interface OnMileAgeListener {
        void changed(float mile);
    }
}
