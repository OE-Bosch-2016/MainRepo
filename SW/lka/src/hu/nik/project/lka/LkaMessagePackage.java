package hu.nik.project.lka;

import java.io.Serializable;

/**
 * Created by zhodvogner on 2016. ápr. 15..
 */
public class LkaMessagePackage implements Serializable {

    private int requestedSteeringWheelAngle = 0;
    private int maximumSpeedForKeepingLane = 50;

    public LkaMessagePackage(int requestedSteeringWheelAngle, int maximumSpeedForKeepingLane) {
        this.requestedSteeringWheelAngle = requestedSteeringWheelAngle;
        this.maximumSpeedForKeepingLane = maximumSpeedForKeepingLane;
    }

    public int getRequestedSteeringWheelAngle() {
        return requestedSteeringWheelAngle;
    }

    public int getMaximumSpeedForKeepingLane() {
        return maximumSpeedForKeepingLane;
    }

}
