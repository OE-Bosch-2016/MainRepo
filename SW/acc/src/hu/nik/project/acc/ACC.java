package hu.nik.project.acc;

import hu.nik.project.communication.*;
import hu.nik.project.radar.RadarMessagePacket;//nearestObjectDistance
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;//ACCIsOn, TargetSpeed
//import hu.nik.project.wheels.WheelsMessagePackage; // -> wheelStateInDegrees, currentSpeed
import hu.nik.project.hmi.Hmi;

/**
 * @author Patrik
 */
public class ACC implements ICommBusDevice {

    private CommBusConnector commBusConnector;
    private boolean enabled = false;
    private Hmi myHmi;


    //input
    private double wheelStateInDegrees;
    private double currentSpeed;
    private int targetSpeed;
    private int nearestObstacleDistance = 1000;

    //inner
    private int minFollowingTime; //in secs
    private final int maxSpeed = 200; //in km/h

    //output
    float gasPedal, breakPedal;

    public ACC(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
        minFollowingTime = 2;
        wheelStateInDegrees = currentSpeed = targetSpeed = 0;
        gasPedal = breakPedal = 0;
        myHmi = Hmi.newInstance();
    }

    @Override
    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();
        /*if (dataType == WheelsMessagePackage.class) {
            try {
                WheelsMessagePackage data = (WheelsMessagePackage) commBusConnector.receive();
                //wheelStateInDegrees = data.getDirection();
                wheelStateInDegrees = data.direction;
                //currentSpeed = data.getSpeed();
                currentSpeed = data.speed;
                if (enabled) {
                    operateACC();
                }
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        } */
        if (dataType == DriverInputMessagePackage.class) {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                enabled = data.accIsActive();
                targetSpeed = data.getTempomatSpeed();
                gasPedal = data.getCarGas();
                breakPedal = data.getCarBreak();

            } catch (CommBusException e) {
                e.printStackTrace();
            }
        } else if (dataType == RadarMessagePacket.class) {
            try {
                RadarMessagePacket data = (RadarMessagePacket) commBusConnector.receive();
                nearestObstacleDistance = (int) data.getCurrentDistance();
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }
    }

    public void operateACC() {
        currentSpeed = myHmi.getKhm();
        if (targetSpeed != 0) {//ACC is on
            double safeDistance = currentSpeed / 3.6 * minFollowingTime;//km/h to m/s * s = m
            if (targetSpeed < currentSpeed) {
                //pedal = -1;//breaking üziküldés
                breakPedal = 1;
                gasPedal = 0;
            } else if (currentSpeed < maxSpeed - Math.abs(wheelStateInDegrees * 4)) {//Should we accelerate?
                if (safeDistance > nearestObstacleDistance) {//break
                    //pedal = -1; üziküldés
                    breakPedal = 1;
                    gasPedal = 0;
                } else if (targetSpeed == currentSpeed) {//fine
                    //pedal = 0;
                } else {//throttle
                    // pedal = 1; üziküldés
                    breakPedal = 0;
                    gasPedal = 1;
                }
            } else if (currentSpeed == maxSpeed - Math.abs(wheelStateInDegrees * 4)) {
                //pedal = 0;//fine
            } else {
                // pedal = -1;//break üziküldés
                breakPedal = 1;
                gasPedal = 0;
            }
        } else {
            //do nothing
            // pedal = 0;
        }
        nearestObstacleDistance = 1000;
    }

    public void doWork() {
        if (enabled) {
            operateACC();
            ACCMessagePackage message = new ACCMessagePackage(gasPedal, breakPedal); //so it doesnt have to remake it every time
            try {
                commBusConnector.send(message);
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }
    }
}

