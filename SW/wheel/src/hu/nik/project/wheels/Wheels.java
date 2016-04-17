package hu.nik.project.wheels;

import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

import hu.nik.project.engine.EngineMessagePackage;
//import hu.nik.project.hmi;

public class Wheels implements IWheels, ICommBusDevice {

	private CommBusConnector commBusConnector;

	//imput buffers
	private double EngineRPM;
	private double EngineTorque;
	private int HmiWheel;
	private double HmiBrake;

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

			if (commBusConnector.getDataType() == EngineMessagePackage.class) {
				try {
					EngineRPM = ((EngineMessagePackage) commBusConnector.receive()).getRpm();
					//EngineTorque = ((EngineMessagePackage) commBusConnector.receive()).getTorque;

				} catch (CommBusException e) {
					//stringData = e.getMessage();
				}
			}
	}

	public Wheels(CommBus commBus, CommBusConnectorType commBusConnectorType)
	{
		commBusConnector = commBus.createConnector(this, commBusConnectorType);

		speed=0;
		direction=0;
	}

	@Override
	public void calcOnTick()
	{	//HmiWheel = hmi.getWheel();
		//HmiBrake =hmi.getbrake();
		calcDirection();    //calculates first because new direction is effected by last speed
		calcSpeed();
		SendToCom();
	}

	private void calcDirection()
	{
		double phiDirection=HmiWheel*speed*turningAdjustment/framerate;
		direction += phiDirection;
		direction = direction % 360;
		if (direction<0)
		{
			direction=360+direction;
		}
	}

	private void calcSpeed() {
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
			speed -= brakeAdjustment * HmiBrake; //have to change this to adjust for boolean brake variable
		} else {
			speed += brakeAdjustment * HmiBrake;
		}
	}

	public void SendToCom() {
		boolean sent = false;
		WheelsMessagePackage message = new WheelsMessagePackage(speed,direction); //so it doesnt have to remake it every time
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
