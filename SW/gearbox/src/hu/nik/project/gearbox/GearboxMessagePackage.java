package hu.nik.project.gearbox;

import java.io.Serializable;

/*
 * @author Laci
 */
public class GearboxMessagePackage implements Serializable {
 	private int gearStage;
	private double torque;
 
 public GearboxMessagePackage(int gearStage, double torque){
	 this.gearStage = gearStage;
	 this.torque = torque;
 }
 
 public int getGearStage(){
	 return gearStage;
 }
 
  public double getTorque(){
	 return torque;
 }
}