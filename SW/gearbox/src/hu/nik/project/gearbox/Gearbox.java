package hu.nik.project.gearbox;

import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

/**
 *
 * @author András & Gergő
 */
public class Gearbox implements ICommBusDevice {
	
	private CommBusConnector commBusConnector;
	
	//input
	int gearLever;
	double rpm;
		
	//inner
	private final int minShiftRpm = 1500;
	private final int maxShiftRpm = 3500;
		
	//output
	private int gearStage;
	private double torque;
	
    public Gearbox(int gearLever) {
        gearStage = gearLever;
    }

    @Override
    public void operateGearbox(int gearLever, double rpm) {	
        switch (gearLever) {
            case 1:
                if (gearStage > 0 && gearStage < 5 && rpm > maxShiftRpm) {
                    gearStage++;
                } else if (gearStage > 2 && rpm < minShiftRpm) {
                    gearStage--;
                }
                if (gearStage < 1) {
                    gearStage = 1;
                }
                break;
            case 0:
                gearStage = 0;
                break;
            default:
                gearStage = -1;
                break;
        }
		calculateTorque();
    }
	
	    private void calculateTorque() {
        switch (gearStage) {
            case 0: //now in N
                torque = 0;
                break;
            case -1: //now in R
                torque = -1 * rpm / 10;
                break;
            case 1: //now in 1
                torque = rpm / 10;
                break;
            default: //now in > 1
                torque = rpm / 10 - gearStage * 20;
                break;
        }
    }
}
