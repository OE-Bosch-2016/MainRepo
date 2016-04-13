package hu.nik.project.wheels;

import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

public class Wheels implements IWheels, ICommBusDevice {

	private CommBusConnector commBusConnector;

	//imput buffers
	private int EngineRPM;
	private int EngineTorque;
	private int HMIWheel;
	private bool HMIBrake;

	//adjust these by testing
	static private double turningAdjustment = 0.01;      //proportion to simulate turning realistically
	static private double framerate = 24;
	static private double accelerationAdjustment = 0.2; //proportion to convert torque to acceleration
	static private double brakeAdjustment = 0.2;		//proportion to turn brake pedal to deceleration
	static private double natureBrake = 0.02;			//deceleration of environment

	//output buffers
	private double speed; 						   //given in km/h
	private double direction; 					   //given in degree 0-360

	@Override
	public void commBusDataArrived() {

		if (commBusConnector.getDataType() == EngineMessagePackage || commBusConnector.getDataType() == HMIMeassagePacket ) {

			//dataType = commBusConnector.getDataType();
			if (commBusConnector.getDataType() == EngineMessagePackage) {
				try {
					EngineRPM = ((EngineMessagePackage)commBusConnector.receive()).RPM;
					EngineTorque = ((EngineMessagePackage)commBusConnector.receive()).Torque;

				} catch (CommBusException e) {
					//stringData = e.getMessage();
				}
			}

			if (commBusConnector.getDataType() == HMIMeassagePacket) {
				try {
					HMIWheel = ((HMIMeassagePacket)commBusConnector.receive()).DriverWheelAngle;

				} catch (CommBusException e) {
					//stringData = e.getMessage();
				}
			}
		}
	}

	public Wheels(CommBus commBus, CommBusConnectorType commBusConnectorType)
	{
		commBusConnector = commBus.createConnector(this, commBusConnectorType);

		speed=0;
		direction=0;
	}


	public void calcOnTick(double brakePedal)
	{
		calcDirection();    //calculates first because new direction is effected by last speed
		calcSpeed(brakePedal);
		SendToCom();
	}

	private void calcDirection()
	{
		double phiDirection=HMIWheel*speed*turningAdjustment/framerate;
		direction += phiDirection;
		direction = direction % 360;
		if (direction<0)
		{
			direction=360+direction;
		}
	}

	private void calcSpeed(double brakePedal) {
		/*
		Power (kW) = Torque (N.m) x Speed (RPM) /0.0095488
		c=0.3
		D=1.25
		A=3
		v = (2*P/(c*D*A))^(1/3)
		natureBrake=0.3*1.25*3=1.125;
		*/

		speed = Math.pow((2 * (EngineTorque * EngineRPM / 0.0095488) / (natureBrake)), new Double("0.3333333"));
		if (EngineTorque >= 0) {
			speed -= brakeAdjustment * brakePedal; //have to change this to adjust for boolean brake variable
		} else {
			speed += brakeAdjustment * brakePedal;
		}
	}

	public void SendToCom() {
		;
		while(!commBusConnector.send(new WheelsMessagePackage(speed,direction))); //is this gonna work even?
	}
	//Interface metodusok
	public double Direction(){
		return direction;
	}

	public double Speed(){
		return speed;
	}

}
