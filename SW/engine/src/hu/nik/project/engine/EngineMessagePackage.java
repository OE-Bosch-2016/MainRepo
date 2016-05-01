package hu.nik.project.engine;

import java.io.Serializable;

/*
 * @author Patrik
 */
public class EngineMessagePackage implements Serializable {
	private double rpm;
	
	public EngineMessagePackage(double rpm){
		this.rpm = rpm;
	}
	
	public double getRpm(){
		return rpm;
	}
}