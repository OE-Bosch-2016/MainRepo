package hu.nik.project.hmi.manager;

import hu.nik.project.communication.*;
import hu.nik.project.visualisation.car.CarController;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;

/**
 * Created by haxxi on 2016.04.22..
 */
public class HmiManager implements ICommBusDevice{

    private static HmiManager mInstance;
    private CommBusConnector commBusConnector;
    private CarController carController = CarController.newInstance();
    private DriverInputMessagePackage messagePackage;


    public static HmiManager newInstance(CommBus commBus, CommBusConnectorType commBusConnectorType){
        if(mInstance == null)
            mInstance = new HmiManager(commBus, commBusConnectorType);
        return mInstance;
    }

    private HmiManager(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        this.commBusConnector = commBus.createConnector(this, commBusConnectorType);
    }

    public void setDriverInputMessagePackage(int tempomatSpeed, int tick, int gearLeverPosition, boolean engine, boolean acc,
                              boolean tsr, boolean pp, boolean aeb, boolean lka) {

        messagePackage = new DriverInputMessagePackage(carController.getBrake(), carController.getSteeringWheel(), carController.getGas(),
                tempomatSpeed, gearLeverPosition, tick, engine, acc, tsr, pp, aeb, lka);
    }

    public void doWork() {
        try {
            commBusConnector.send(messagePackage);
        } catch (CommBusException e) {
            e.printStackTrace();
        }
    }

    public void commBusDataArrived() {
    }
}
