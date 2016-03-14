package HMI;

/**
 * Created by haxxi on 2016.03.04..
 */
public class Hmi implements IHmi {

    OnHmiListener hmiListener;

    public void mileage(float mile) {
        if (hmiListener != null)
            hmiListener.mileAgeChanged(mile);
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void tachometer(float value) {
        if(hmiListener != null)
            hmiListener.tachometerChanged(value);
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setHmiListener(OnHmiListener hmiListener) {
        this.hmiListener = hmiListener;
    }

    // Listener --------------------------------------------------------------------------------------------------------
    public interface OnHmiListener {
        void mileAgeChanged(float mile);
        void tachometerChanged(float tachometer);
    }
}
