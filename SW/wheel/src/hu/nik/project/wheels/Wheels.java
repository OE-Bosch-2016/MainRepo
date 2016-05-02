package hu.nik.project.wheels;

import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

import hu.nik.project.gearbox.GearboxMessagePackage;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
import hu.nik.project.engine.EngineMessagePackage;
import hu.nik.project.visualisation.car.CarController;

public class Wheels implements IWheels, ICommBusDevice {

	private CommBusConnector commBusConnector;
	private CarController carController;

	//imput buffers
	private double engineRPM;
	private double engineTorque;
	private float hmiWheel;
	private float hmiBrake;

	//adjust these by testing
	static private double turningAdjustment = 0.2;      //proportion to simulate turning realistically
	static private double framerate = 24;
	static private double accelerationAdjustment = 0.2; //proportion to convert torque to acceleration
	static private double brakeAdjustment = 1.8;		//proportion to turn brake pedal to deceleration			//deceleration of environment

	//output buffers
	private double speed; 						   //given in km/h
	private double direction; 					   //given in degree 0-360

	@Override
	public void commBusDataArrived() {

		Class dataType = commBusConnector.getDataType();

		if (dataType == EngineMessagePackage.class) {
			try {
				engineRPM = ((EngineMessagePackage) commBusConnector.receive()).getRpm();
			} catch (CommBusException e) {
				//stringData = e.getMessage();
			}
		}

		if (dataType == DriverInputMessagePackage.class) {
			try {
				DriverInputMessagePackage data = (DriverInputMessagePackage)commBusConnector.receive();
				hmiWheel = data.getWheelAngle();
				hmiBrake = data.getCarBreak();
			} catch (CommBusException e) {
				//stringData = e.getMessage();
			}
		}

		if (dataType == GearboxMessagePackage.class) {
			try {
				engineTorque = ((GearboxMessagePackage)commBusConnector.receive()).getTorque();

			} catch (CommBusException e) {
				//stringData = e.getMessage();
			}
		}
	}

	public Wheels(CommBus commBus, CommBusConnectorType commBusConnectorType)
	{
		commBusConnector = commBus.createConnector(this, commBusConnectorType);
		carController = CarController.newInstance();

		speed=0;
		direction=0;
	}

	@Override
	public void doWork()
	{
		calcDirection();    //calculates first because new direction is effected by last speed
		calcSpeed();
		carController.autonomousController((int)direction,(float)speed);
	}

	private void calcDirection()
	{
		double phiDirection= hmiWheel *speed*turningAdjustment/framerate;
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

		speed = Math.pow((2 * (engineTorque * engineRPM / 9.5488)), new Double("0.3333333"))/10; // 10 is to change from km/h to the pixelmap/h
		if (engineTorque >= 0) {
			if(speed-brakeAdjustment*hmiBrake > 0) {
				speed -= brakeAdjustment * hmiBrake; //have to change this to adjust for boolean brake variable
			}
			else {
			speed=0;
			}
		} else {
			if(speed-brakeAdjustment*hmiBrake < 0) {
			speed += brakeAdjustment * hmiBrake;
			}
			else {
				speed=0;
			}
		}
		//System.out.println(engineRPM + "<-rpm " + engineTorque + "<-torque " + speed*10);
	}
}
