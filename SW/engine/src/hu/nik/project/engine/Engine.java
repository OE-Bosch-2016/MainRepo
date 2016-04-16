package hu.nik.project.engine;

import hu.nik.project.communication.*;
import hu.nik.project.gearbox.GearboxMessagePackage;
//import hu.nik.project.HMI!
//import hu.nik.project.SYSTEM!

/*
 * @author Laci & Patrik
 */
public class Engine implements ICommBusDevice {
	
	private CommBusConnector commBusConnector;
	private String stringData = "";
	
	//input
	boolean throttle;
	int gearStage;
	//deltatime time?
	
	//inner
    final int maxRpm = 4000;
    final int minRpm = 500;
    private int lastGearStage;
	//lasttime
	
	//output
    private double rpm;
	
	
    public Engine(CommBus commBus, CommBusConnectorType commBusConnectorType) {
		this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
        rpm = 0;
        lastGearStage = 0;
        gearStage = 0;
    }
	
    public String getStringData() {
        return stringData;
    }
	
    @Override
    public void commBusDataArrived() {
		Class dataType = commBusConnector.getDataType();
		if(dataType == GearboxMessagePackage.class)
		{
			try {
                GearboxMessagePackage data = (GearboxMessagePackage) commBusConnector.receive();
                gearStage = data.getGearStage();
				operateShift();
				//data küldés a buszra
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}
		/*else if(dataType == xxx.class) //throttle
		{
			try {
                XXXX data = (XXXX) commBusConnector.receive();
                throttle = data.getXXXX();
				//itt nem számolunk RPM-t mert nem tudjuk mennyi idő telt el -> negatív input lagg lesz
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}*/
		/*else if(dataType == yyyy.class) //time
		{
			try {
                YYYY data = (YYYY) commBusConnector.receive();
                time = data.getYYYY();
				calculateRpm();
				//data küldés a buszra
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}*/
	}
	
	
    public void operateShift() {
		if (gearStage > lastGearStage) { //shift up
			onShiftUp();
		} else if(gearStage < lastGearStage){ //shift down
			onShiftDown();
		}
    }

    private void onShiftUp() {
        if (gearStage == 0) {
            shiftToN(); //shift from R to N
        } else if(lastGearStage == 0) {
            shiftFromN(gearStage); //shift to 1 from N
        } else {
            rpm -= 1500;
        }
        lastGearStage = gearStage;
    }

    private void onShiftDown() {
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

    private void calculateRpm() {
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
    }
}
