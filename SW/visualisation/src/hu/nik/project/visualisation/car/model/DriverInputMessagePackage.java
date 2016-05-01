package hu.nik.project.visualisation.car.model;

import java.io.Serializable;

/**
 * Created by haxxi on 2016.04.17..
 */
public class DriverInputMessagePackage implements Serializable {

    private float carBrake;
    private float wheelAngle;
    private float carGas;
    private int tempomatSpeed;
    private int tick;
    int gearLeverPosition;

    private boolean engine;
    private boolean acc;
    private boolean tsr;
    private boolean pp;
    private boolean aeb;
    private boolean lka;
    private boolean tempomat;

    public DriverInputMessagePackage(float carBrake, float wheelAngle, float carGas, int tempomatSpeed, int gearLeverPosition, int tick, boolean engine, boolean acc,
                                     boolean tsr, boolean pp, boolean aeb, boolean lka, boolean tempomat) {
        this.carBrake = carBrake;
        this.wheelAngle = wheelAngle;
        this.carGas = carGas;
        this.tempomatSpeed = tempomatSpeed;
        this.gearLeverPosition = gearLeverPosition;
        this.tick = tick;
        this.engine = engine;
        this.acc = acc;
        this.tsr = tsr;
        this.pp = pp;
        this.aeb = aeb;
        this.lka = lka;
        this.tempomat = tempomat;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public float getCarBreak() {
        return carBrake;
    }

    public float getWheelAngle() {
        return wheelAngle;
    }

    public int getTempomatSpeed() {
        return tempomatSpeed;
    }

    public boolean engineIsActive() {
        return engine;
    }

    public boolean tempomatisActive() {
        return tempomat;
    }

    public int getTick() {
        return tick;
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
