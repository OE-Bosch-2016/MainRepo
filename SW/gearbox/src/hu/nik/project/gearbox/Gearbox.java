package hu.nik.project.gearbox;

import hu.nik.project.communication.*;
import hu.nik.project.engine.EngineMessagePackage;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
import hu.nik.project.hmi.Hmi;

/**
 * @author András & Gergő
 */
public class Gearbox implements ICommBusDevice {

    private CommBusConnector commBusConnector;
    private Hmi myHmi;

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
        myHmi = Hmi.newInstance();
    }

    @Override
    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();
        if (dataType == EngineMessagePackage.class) {
            try {
                EngineMessagePackage data = (EngineMessagePackage) commBusConnector.receive();
                rpm = data.getRpm();
                operateGearbox();
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }
        else if(dataType == DriverInputMessagePackage.class)
		{
			try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                gearLever = data.getGearLeverPosition();
				//operateGearbox();
            } catch (CommBusException e) {
                e.printStackTrace();
            }
		}
    }

    public void operateGearbox() {
        switch (gearLever) {
            case Hmi.GEAR_SHIFT_D:
                if (gearStage > 0 && gearStage < 5 && rpm > maxShiftRpm) {
                    gearStage++;
                } else if (gearStage >= 2 && rpm < minShiftRpm) {
                    gearStage--;
                }
                if (gearStage < 1) {
                    gearStage = 1;
                }
                break;
            case Hmi.GEAR_SHIFT_N:
                gearStage = 0;
                break;
            case Hmi.GEAR_SHIFT_R:
                gearStage = -1;
                break;
        }
        myHmi.numberedGearShiftPosition(gearStage);
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

    public void doWork() {
        GearboxMessagePackage message = new GearboxMessagePackage(gearStage, torque); //so it doesnt have to remake it every time
        try {
            commBusConnector.send(message);
        }
        catch (CommBusException e) {
            e.printStackTrace();
        }
    }
}
