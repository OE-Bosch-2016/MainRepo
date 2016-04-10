package hmi2;

/**
 * Created by haxxi on 2016.03.04..
 */
public interface IHmi {
    void mileage(float mile);
    void tachometer(float value);
    void gearshift(int stage); // You can reach the stages from Hmi class. Them static fields. Example: "Hmi.GEAR_SHIFT_D" - these are parameters. Example: gearshift(Hmi.GEAR_SHIFT_D)
}
