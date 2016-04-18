package hu.nik.project.acc;

import hu.nik.project.communication.*;
//import hu.nik.project.HMI/RADAR! -> nearestObstacleDistance
//import hu.nik.project.HMI! -> targetSpeed
import hu.nik.project.wheels.WheelsMessagePackage; // -> wheelStateInDegrees, currentSpeed
/**
 *
 * @author Patrik
 */
public class ACC implements ICommBusDevice {
	
	private CommBusConnector commBusConnector;
	private String stringData = "";

	//input
	private double wheelStateInDegrees;
	private double currentSpeed;
	private int targetSpeed;
	private int nearestObstacleDistance;
	
	//inner
    private int minFollowingTime; //in secs
    private final int maxSpeed = 200; //in km/h
	
	//output
	//gasPedal,breakPedal
	
    public ACC(CommBus commBus, CommBusConnectorType commBusConnectorType) {
		this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
        minFollowingTime = 2;
		wheelStateInDegrees = currentSpeed = targetSpeed = nearestObstacleDistance = 0;
    }
	
    public String getStringData() {
        return stringData;
    }
	
    @Override
    public void commBusDataArrived() {
		Class dataType = commBusConnector.getDataType();
		if(dataType == WheelsMessagePackage.class)
		{
			try {
                WheelsMessagePackage data = (WheelsMessagePackage) commBusConnector.receive();
				//wheelStateInDegrees = data.getDirection();
				wheelStateInDegrees = data.direction;
				//currentSpeed = data.getSpeed();
				currentSpeed = data.speed;
				operateACC();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}
		/*else if(dataType == xxx.class)
		{
			try {
                XXXX data = (XXXX) commBusConnector.receive();
                targetSpeed = data.getXXXX();
				operateACC();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}*/
		/*else if(dataType == xxx.class)
		{
			try {
                YYYY data = (YYYY) commBusConnector.receive();
                nearestObstacleDistance = data.getYYYYY();
				operateACC();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}*/
	}

    public void operateACC() {
        if (targetSpeed != 0) {//ACC is on
            double safeDistance = currentSpeed / 3.6 * minFollowingTime;//km/h to m/s * s = m
            if (targetSpeed < currentSpeed) {
                //pedal = -1;//breaking üziküldés
            } else if (currentSpeed < maxSpeed - Math.abs(wheelStateInDegrees * 4)) {//Should we accelerate?
                if (safeDistance > nearestObstacleDistance) {//break
                    //pedal = -1; üziküldés
                } else if (targetSpeed == currentSpeed) {//fine
                    //pedal = 0;
                } else {//throttle
                   // pedal = 1; üziküldés
                }
            } else if (currentSpeed == maxSpeed - Math.abs(wheelStateInDegrees * 4)) {
                //pedal = 0;//fine
            } else {
               // pedal = -1;//break üziküldés
            }
        } else {
            //do nothing
           // pedal = 0;
        }
    }
}
