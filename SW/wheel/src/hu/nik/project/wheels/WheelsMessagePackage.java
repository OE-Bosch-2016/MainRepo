package hu.nik.project.wheels;

import java.io.Serializable;

public class WheelsMessagePackage implements Serializable {
	public double speed;
	public double direction;
	
	public WheelsMessagePackage(double speed, double direction){
		this.speed = speed;
		this.direction = direction;
	}
}
