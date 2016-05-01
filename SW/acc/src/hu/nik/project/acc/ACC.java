package hu.nik.project.acc;

import hu.nik.project.communication.*;
import hu.nik.project.radar.RadarMessagePacket;//nearestObjectDistance
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;//ACCIsOn, TargetSpeed
import hu.nik.project.wheels.WheelsMessagePackage; // -> wheelStateInDegrees, currentSpeed

/**
 * @author Patrik
 */
public class ACC implements ICommBusDevice {

    private CommBusConnector commBusConnector;
    private String stringData = "";
    private boolean enabled = false;

    //input
    private double wheelStateInDegrees;
    private double currentSpeed;
    private int targetSpeed;
    private int nearestObstacleDistance;

    //inner
    private int minFollowingTime; //in secs
    private final int maxSpeed = 200; //in km/h

    //output
    float gasPedal,breakPedal;

    public ACC(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
        minFollowingTime = 2;
        wheelStateInDegrees = currentSpeed = targetSpeed = nearestObstacleDistance = 0;
        gasPedal = breakPedal = 0;
    }

    public String getStringData() {
        return stringData;
    }

    @Override
    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();
        if (dataType == WheelsMessagePackage.class) {
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
                stringData = e.getMessage();
            }
        } else if (dataType == DriverInputMessagePackage.class) {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                enabled = data.accIsActive();
                targetSpeed = data.getTempomatSpeed();
                gasPedal = data.getCarGas();
                breakPedal = data.getCarBreak();

            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        }
        else if(dataType == RadarMessagePacket.class)
		{
			try {
                RadarMessagePacket data = (RadarMessagePacket) commBusConnector.receive();
                nearestObstacleDistance = (int)data.getCurrentDistance();
				operateACC();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}
    }

    public void operateACC() {
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
        SendToCom();
    }

    public void SendToCom() {
        boolean sent = false;
        ACCMessagePackage message = new ACCMessagePackage(gasPedal, breakPedal); //so it doesnt have to remake it every time
        while(!sent) {
            try {
                if (commBusConnector.send(message)) {
                    sent = true;
                }
            } catch (CommBusException e) {
                //sad times
            }
        }
    }
}
