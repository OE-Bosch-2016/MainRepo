package hu.nik.project.hmi;

/**
 * Created by haxxi on 2016.03.04..
 */
public class Hmi implements IHmi {

    private static Hmi mInstance;

    public final static int GEAR_SHIFT_D = 0;
    public final static int GEAR_SHIFT_N = 1;
    public final static int GEAR_SHIFT_R = 2;
    public final static int GEAR_SHIFT_P = 3;
    public final static int GEAR_SHIFT_1 = 4;
    public final static int GEAR_SHIFT_2 = 5;

    private int kmh = 0;
    private int rpm = 800;
    private int gearLever;

    OnHmiListener hmiListener;

    public static Hmi newInstance() {
        if (mInstance == null)
            mInstance = new Hmi();
        return mInstance;
    }

    private Hmi(){}

    public void mileage(float mile) {
        if (hmiListener != null) {
            hmiListener.mileAgeChanged(mile);
            kmh = (int)mile;
        }
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void tachometer(float value) {
        if (hmiListener != null) {
            hmiListener.tachometerChanged(value);
            rpm = (int)value;
        }
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void gearshift(int stage) {
        if (hmiListener != null) {
            hmiListener.gearshiftChanged(stage);
            gearLever = stage;
        }
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void numberedGearShiftPosition(int stage){
        if (hmiListener != null)
            hmiListener.numberedGearShiftChanged(stage);
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    public void steeringWheelPosition(double angle){
        if (hmiListener != null)
            hmiListener.steerengWheelChanged(angle);
        else
            throw new NullPointerException("Hmi listener is missing");
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public int getKhm() {
        return kmh;
    }

    public int getRpm() {
        return rpm;
    }

    public int getGearLever() {
        return gearLever;
    }

    public static int getGearShiftR() {
        return GEAR_SHIFT_R;
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setHmiListener(OnHmiListener hmiListener) {
        this.hmiListener = hmiListener;
    }

    public void setKhm(int kmh) {
        this.kmh = kmh;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    // Listener --------------------------------------------------------------------------------------------------------
    public interface OnHmiListener {
        void mileAgeChanged(float mile);

        void tachometerChanged(float tachometer);

        void gearshiftChanged(int gearshift);

        void numberedGearShiftChanged(int position);

        void steerengWheelChanged(double angle);
    }
}
