package hu.nik.project.engine;

import hu.nik.project.communication.*;
import hu.nik.project.gearbox.GearboxMessagePackage;
import hu.nik.project.hmi.Hmi;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
import hu.nik.project.acc.ACCMessagePackage;

/*
 * @author Laci & Patrik
 */
public class Engine implements ICommBusDevice {

    private CommBusConnector commBusConnector;

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
    private Hmi myHmi;

    //output
    private double rpm;


    public Engine(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
        throttle = 0;
        rpm = 0;
        lastGearStage = 0;
        gearStage = 0;
        time = lasttime = 0;
        myHmi = Hmi.newInstance();
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
                e.printStackTrace();
            }
        } else if (dataType == ACCMessagePackage.class) //ACC throttle
        {
            try {
                ACCMessagePackage data = (ACCMessagePackage) commBusConnector.receive();
                throttle = (double) data.getGasPedal();
                calculateRpm();
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        } else if (dataType == DriverInputMessagePackage.class) //throttle
        {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();

                started = data.engineIsActive();
                time = (int)data.getTick();

                if (!data.accIsActive()) {//ACC is on? => ignore this input
                    throttle = (double) data.getCarGas();//ACC is off => get this input
                    calculateRpm();
                }

            } catch (CommBusException e) {
                e.printStackTrace();
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
            operatingPoint += (double) lasttime / 5;
        } else {
            operatingPoint -= (double) lasttime / 5;
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
        myHmi.tachometer((int) rpm);
        lasttime = time;
    }

    public void doWork() {
        if (started) {
            EngineMessagePackage message = new EngineMessagePackage(rpm); //so it doesnt have to remake it every time
            try {
                commBusConnector.send(message);
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }
    }
}
