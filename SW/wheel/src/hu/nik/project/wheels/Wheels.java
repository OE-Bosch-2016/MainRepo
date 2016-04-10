package hu.nik.project.wheels;

public class Wheels implements IWheels {
//author: Ferter Viktor
//Last modified: Sipos Pal
//Last Last modified: Apor
	
	
	double speed; 						   //given in km/h 
	double direction; 					   //given in degree 0-360
	
	//adjust these by testing
    	static double turningAdjustment = 0.01;      //proportion to simulate turning realistically
    	static double framerate = 24;
	static double accelerationAdjustment = 0.2; //proportion to convert torque to acceleration
	static double brakeAdjustment = 0.2;		//proportion to turn brake pedal to deceleration
	static double natureBrake = 0.02;			//deceleration of environment
    
	public Wheels()
	{
	speed=0;
	direction=0;
	}
	
	public Wheels(double direction)
	{
		speed=0;
		this.direction=direction;
	}
	
	public Wheels(double direction, double speed)
	{
		this.speed=speed;
		this.direction=direction;
	}
    
	
	public void calcOnTick(double driverWheel,double torque ,double RPM, double brakePedal)
	{
		calcDirection(driverWheel);    //calculates first because new direction is effected by last speed
		calcSpeed(torque, brakePedal, RPM);
	}
	
	private void calcDirection(double driverWheel) 
	{
		double phiDirection=driverWheel*speed*turningAdjustment/framerate;
		direction += phiDirection;
		direction = direction % 360;
		if (direction<0)
		{
			direction=360+direction;
		}
	}
	
	private void calcSpeed(double torque, double brakePedal, double RPM)
	{
		/*
		Power (kW) = Torque (N.m) x Speed (RPM) /0.0095488
		c=0.3
		D=1.25
		A=3
		v = (2*P/(c*D*A))^(1/3)
		natureBrake=0.3*1.25*3=1.125; 
		*/
		
		speed = Math.pow((2*(torque*RPM/0.0095488)/(natureBrake)), new Double("0.3333333"));
		if(torque >= 0)
		{
			speed -= brakeAdjustment * brakePedal;
		}
		else
		{
			speed += brakeAdjustment * brakePedal;
		}
	}
	
	//Interface metodusok
	public double Direction(){
		return direction;
	}
	
	public double Speed(){
		return speed;
	}

}
