package hu.nik.project.visualisation.car.model;

import java.io.Serializable;

/**
 * Created by haxxi on 2016.04.17..
 */
public class DriverInputMessagePacket implements Serializable {

    private float carBreak;
    private float wheelAngle;
    private float carGas;

    public DriverInputMessagePacket(float carBreak, float wheelAngle, float carGas) {
        this.carBreak = carBreak;
        this.wheelAngle = wheelAngle;
        this.carGas = carGas;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public float getCarBreak() {
        return carBreak;
    }

    public float getWheelAngle() {
        return wheelAngle;
    }

    public float getCarGas() {
        return carGas;
    }
}
