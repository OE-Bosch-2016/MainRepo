package hu.nik.project.engine;

import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

/*
 * @author Laci & Patrik
 */
public class Engine implements ICommBusDevice {
	
	private CommBusConnector commBusConnector;
	
	//input
	boolean throttle;
	//deltatime
	
	//inner
    final int maxRpm = 4000;
    final int minRpm = 500;
    private int lastGearStage;
	//lasttime
	
	//output
    private double rpm;
	
	
    public Engine() {
        rpm = 0;
        lastGearStage = 0;
    }

    @Override
    public void operateEngine(int gearStage, boolean throttle) {
        if (gearStage > lastGearStage) { //shift up
            onShiftUp(gearStage);
        } else if(gearStage < lastGearStage){ //shift down
            onShiftDown(gearStage);
        }
        calculateRpm(throttle);//a végén üziküldés
    }

    private void onShiftUp(int gearStage) {
        if (gearStage == 0) {
            shiftToN(); //shift from R to N
        } else if(lastGearStage == 0) {
            shiftFromN(gearStage); //shift to 1 from N
        } else {
            rpm -= 1500;
        }
        lastGearStage = gearStage;
    }

    private void onShiftDown(int gearStage) {
        if (gearStage == 0) {
            shiftToN(); //shift to N
        } else if (lastGearStage == 0) {
            shiftFromN(gearStage); //shift to R
        } else {
            rpm += 500;
        }
        lastGearStage = gearStage;
    }

    private void shiftFromN(int gearStage) {
        rpm -= 1500;
    }

    private void shiftToN() {
		rpm = rpm;
    }

    private void calculateRpm(boolean throttle) {
        double time = 5 * (Math.pow(Math.E, rpm / 1600) - 1);
        if (throttle) {
            time += 0.5;
        } else {
            time -= 0.5;
        }
        if (time < 0) {
            time = 0;
        }
        if (time >= 0) {
            rpm = (Math.log((time + 5) / 5)) * 1600;
            if (rpm > maxRpm) {
                rpm = maxRpm;
            } else if (rpm < minRpm) {
                rpm = minRpm;
            }
        }
		//kiküldi a datát
    }
}
