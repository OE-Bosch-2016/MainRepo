package hu.nik.project.hmi;

/**
 * Created by haxxi on 2016.03.04..
 */
public class Hmi implements IHmi {

    public static int GEAR_SHIFT_D = 0;
    public static int GEAR_SHIFT_N = 1;
    public static int GEAR_SHIFT_R = 2;
    public static int GEAR_SHIFT_P = 3;
    public static int GEAR_SHIFT_1 = 4;
    public static int GEAR_SHIFT_2 = 5;

    private int kmh = 0;
    private int rpm = 800;

    OnHmiListener hmiListener;

    public void setKhm(int kmh)
    {
        this.kmh = kmh;
    }

    public void setRpm(int rpm)
    {
        this.rpm = rpm;
    }

    public int getKhm()
    {
        return kmh;
    }

    public  int getRpm()
    {
        return rpm;
    }
    public void mileage(float mile) {
        if (hmiListener != null)
            hmiListener.mileAgeChanged(mile);
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void tachometer(float value) {
        if (hmiListener != null)
            hmiListener.tachometerChanged(value);
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void gearshift(int stage) {
        if (hmiListener != null)
            hmiListener.gearshiftChanged(stage);
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

        void gearshiftChanged(int gearshift);
    }
}
