package hu.nik.project.tsr;

import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CyberZero on 2016. 04. 07..
 */
public class TsrMessagePackage implements Serializable {

    private int speedLimit;
    private ScenePoint centerPoint;

    public TsrMessagePackage(int speedLimit, ScenePoint centerPoint) {
        this.speedLimit = speedLimit;
        this.centerPoint = centerPoint;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public ScenePoint getCenterPoint() {
        return centerPoint;
    }
}
