package hu.nik.project.visualisation.car.model;

import java.io.Serializable;

/**
 * Created by haxxi on 2016.04.17..
 */
public class DriverInputMessagePacket implements Serializable {

    private float carBrake;
    private float wheelAngle;
    private float carGas;
    private boolean acc;
    private boolean tsr;
    private boolean pp;
    private boolean aeb;
    private boolean lka;

    public DriverInputMessagePacket(float carBreak, float wheelAngle, float carGas) {
        this.carBrake = carBreak;
        this.wheelAngle = wheelAngle;
        this.carGas = carGas;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public float getCarBrake() {
        return carBrake;
    }

    public float getWheelAngle() {
        return wheelAngle;
    }

    public float getCarGas() {
        return carGas;
    }

    public boolean accIsActive() {
        return acc;
    }

    public boolean tsrIsActive() {
        return tsr;
    }

    public boolean ppIsActive() {
        return pp;
    }

    public boolean aebIsActive() {
        return aeb;
    }

    public boolean lkaIsActive() {
        return lka;
    }
}
