package hu.nik.project.acc;

import java.io.Serializable;

/*
 * @author Patrik
 */
public class ACCMessagePackage implements Serializable {
 	private boolean gasPedal;
 	private boolean breakPedal;
 
	public ACCMessagePackage(boolean gasPedal, boolean breakPedal){
		this.gasPedal = gasPedal;
		this.breakPedal = breakPedal;
	}
 
    public int getGasPedal(){
		return gasPedal;
	}
 
	public double getBreakPedal(){
		return breakPedal;
	}
}