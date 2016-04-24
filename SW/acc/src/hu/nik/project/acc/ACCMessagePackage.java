package hu.nik.project.acc;

import java.io.Serializable;

/*
 * @author Patrik
 */
public class ACCMessagePackage implements Serializable {
 	private float gasPedal;
 	private float breakPedal;
 
	public ACCMessagePackage(float gasPedal, float breakPedal){
		this.gasPedal = gasPedal;
		this.breakPedal = breakPedal;
	}
 
    public float getGasPedal(){
		return gasPedal;
	}
 
	public float getBreakPedal(){
		return breakPedal;
	}
}