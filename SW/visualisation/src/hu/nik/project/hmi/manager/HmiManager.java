package hu.nik.project.hmi.manager;

import hu.nik.project.communication.*;
import hu.nik.project.visualisation.car.CarController;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;

/**
 * Created by haxxi on 2016.04.22..
 */
public class HmiManager implements ICommBusDevice{

    private static HmiManager mInstance;
    private CommBusConnector connector;
    private CommBus commBus;
    private CarController carController = CarController.newInstance();
    private DriverInputMessagePackage messagePackage;


    public static HmiManager newInstance(){
        if(mInstance == null)
            mInstance = new HmiManager();
        return mInstance;
    }

    public void createPackage(int tempomatSpeed, int tick, boolean engine, boolean acc,
                              boolean tsr, boolean pp, boolean aeb, boolean lka, boolean tempomat){
        messagePackage = new DriverInputMessagePackage(0, carController.getSteeringWheel(), carController.getGas(),
                tempomatSpeed, tick, engine, acc, tsr, pp, aeb, lka, tempomat);
        sendHmiValues(messagePackage);

    }

    private void sendHmiValues(DriverInputMessagePackage messagePackage){
        if(connector == null) {
            commBus = new CommBus();
            connector = commBus.createConnector(this, CommBusConnectorType.Sender);
        }

        try {
            connector.send(messagePackage);
        } catch (CommBusException e) {
            e.printStackTrace();
        }
    }


    public void commBusDataArrived() {
    }
}
