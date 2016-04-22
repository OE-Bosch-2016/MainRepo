package hu.nik.project.visualisation.car.model;

import java.io.Serializable;

/**
 * Created by haxxi on 2016.04.17..
 */
public class DriverInputMessagePackage implements Serializable {

    private float carBrake;
    private float wheelAngle;
    private float carGas;
    private boolean acc;
    private boolean tsr;
    private boolean pp;
    private boolean aeb;
    private boolean lka;

    public DriverInputMessagePackage(float carBrake, float wheelAngle, float carGas, boolean acc, boolean tsr, boolean pp, boolean aeb, boolean lka) {
        this.carBrake = carBrake;
        this.wheelAngle = wheelAngle;
        this.carGas = carGas;
        this.acc = acc;
        this.tsr = tsr;
        this.pp = pp;
        this.aeb = aeb;
        this.lka = lka;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public float getCarBreak() {
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
