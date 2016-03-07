package wheels;

public class Wheels implements IWheels {
//author: Ferter Viktor
//Last modified: Sipos Pal
//Last Last modified: Apor
	
	
	double speed; 						   //given in km/h 
	double direction; 					   //given in degree 0-360
	
	//adjust these by testing
    	static double turningAdjustment = 0.2;      //proportion to simulate turning realistically
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
    
	
	public void calcOnTick(double driverWheel,double gasPedal, double brakePedal)
	{
		calcDirection(driverWheel);    //calculates first because new direction is effected by last speed
		calcSpeed(gasPedal,brakePedal);
	}
	
	private void calcDirection(double driverWheel) 
	{
		double phiDirection=driverWheel*speed*turningAdjustment;
		direction += phiDirection;
		direction = direction % 360;
		if (direction<0)
		{
			direction=360+direction;
		}
	}
	
	private void calcSpeed(double torque, double brakePedal)
	{
		speed += accelerationAdjustment * torque - natureBrake * speed;
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
