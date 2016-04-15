package hu.nik.project.gearbox;

import hu.nik.project.communication.*;
import hu.nik.project.engine.EngineMessagePackage;
//import hu.nik.project.visualisation

/**
 *
 * @author András & Gergő
 */
public class Gearbox implements ICommBusDevice {
	
	private CommBusConnector commBusConnector;
	private String stringData = "";
	
	//input
	int gearLever;
	double rpm;
		
	//inner
	private final int minShiftRpm = 1500;
	private final int maxShiftRpm = 3500;
		
	//output
	private int gearStage;
	private double torque;
	
    public Gearbox(int gearLever, CommBus commBus, CommBusConnectorType commBusConnectorType) {
        this.gearStage = this.gearLever = gearLever;
		this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
		this.rpm = 0;
    }
	
    public String getStringData() {
        return stringData;
    }
	
    @Override
    public void commBusDataArrived() {
		Class dataType = commBusConnector.getDataType();
		if(dataType == EngineMessagePackage.class)
		{
			try {
                EngineMessagePackage data = (EngineMessagePackage) commBusConnector.receive();
                rpm = data.getRpm();
				operateGearbox();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}
		else if(dataType == xxx.class)
		{
			try {
                XXXX data = (XXXX) commBusConnector.receive();
                gearLever = data.getXXXX();
				operateGearbox();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
		}
	}

    @Override
    public void operateGearbox() {	
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
	
	public int getGearStage()
	{
		return gearStage;
	}
	
	public double getTorque()
	{
		return torque;
	}
}
