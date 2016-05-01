package hu.nik.project.engine;

import hu.nik.project.communication.*;
import hu.nik.project.gearbox.GearboxMessagePackage;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;

/*
 * @author Laci & Patrik
 */
public class Engine implements ICommBusDevice {

    private CommBusConnector commBusConnector;
    private String stringData = "";

    //input
    double throttle;
    int gearStage;
    int time;

    //inner
    final int maxRpm = 4000;
    final int minRpm = 500;
    private int lastGearStage;
    int lasttime;
    boolean started;

    //output
    private double rpm;


    public Engine(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
        rpm = 0;
        lastGearStage = 0;
        gearStage = 0;
        time = lasttime = 0;
    }

    public String getStringData() {
        return stringData;
    }

    @Override
    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();
        if (dataType == GearboxMessagePackage.class) {
            try {
                GearboxMessagePackage data = (GearboxMessagePackage) commBusConnector.receive();
                gearStage = data.getGearStage();
                operateShift();
                //data küldés a buszra
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        } else if (dataType == DriverInputMessagePackage.class) //throttle
        {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                started = data.engineIsActive();
                throttle = (double) data.getCarGas();
                time = (int) data.getTick();
                calculateRpm();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        }
    }

    public void operateShift() {
        if (gearStage > lastGearStage) { //shift up
            onShiftUp();
        } else if (gearStage < lastGearStage) { //shift down
            onShiftDown();
        }
        //SendToCom(); //disabled this func to avoid cyclic call btw eng and gb
    }

    private void onShiftUp() {
        if (gearStage == 0) {
            shiftToN(); //shift from R to N
        } else if (lastGearStage == 0) {
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

    //bugfix needed
    private void calculateRpm() {
        lasttime = time - lasttime;
        double operatingPoint = 5 * (Math.pow(Math.E, rpm / 1600) - 1);
        if (throttle > 0) {
            operatingPoint += (double) lasttime / 1000;
        } else {
            operatingPoint -= (double) lasttime / 1000;
        }
        if (operatingPoint < 0) {
            operatingPoint = 0;
        }
        if (operatingPoint >= 0) {
            rpm = (Math.log((operatingPoint + 5) / 5)) * 1600;
            if (rpm > maxRpm) {
                rpm = maxRpm;
            } else if (rpm < minRpm) {
                rpm = minRpm;
            }
        }
        lasttime = time;
    }

    public void doWork() {
        EngineMessagePackage message = new EngineMessagePackage(rpm); //so it doesnt have to remake it every time
        try {
            commBusConnector.send(message);
        } catch (CommBusException e) {
            //sad times
        }
    }
}
