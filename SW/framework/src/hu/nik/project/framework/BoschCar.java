package hu.nik.project.framework;

import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.gearbox.Gearbox;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObjectException;
import hu.nik.project.hmi.manager.HmiManager;
import hu.nik.project.tsr.Tsr;
import hu.nik.project.wheels.Wheels;
import hu.nik.project.acc.ACC;
import hu.nik.project.communication.CommBus;
import hu.nik.project.engine.Engine;
import hu.nik.project.ultrasonicsensor.Ultrasonicsensor;

/**
 * Created by Róbert on 2016.04.10..
 *
 * All integrated factory created BoschCar class
 */
public class BoschCar extends Car {

    ACC acc;
    CommBus commBus;
    Engine engine;
    Gearbox gearbox;
    Tsr tsr;
    Ultrasonicsensor ultrasonicsensor; // The constructor is not straightforward, have to discuss it with the team
    Wheels wheels;
    HmiManager hmiManager;


    public BoschCar(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);

        commBus = new CommBus();
        acc = new ACC(commBus, CommBusConnectorType.SenderReceiver);
        engine = new Engine(commBus, CommBusConnectorType.SenderReceiver);
        gearbox = new Gearbox(0, commBus, CommBusConnectorType.SenderReceiver);
        tsr = new Tsr(commBus, CommBusConnectorType.SenderReceiver);
        wheels = new Wheels(commBus, CommBusConnectorType.SenderReceiver);
        hmiManager = hmiManager.newInstance(commBus, CommBusConnectorType.Sender);
    }

    public void doWork() {
        hmiManager.doWork();
        engine.doWork();
        gearbox.doWork();
        wheels.doWork();
    }

    public void setDriverInput(int tempomatSpeed, int tick, int gearLeverPosition, boolean engine, boolean acc,
                               boolean tsr, boolean pp, boolean aeb, boolean lka, boolean tempomat) {
        hmiManager.setDriverInputMessagePackage(tempomatSpeed, tick, gearLeverPosition, engine, acc, tsr, pp, aeb, lka, tempomat);
    }


    // Need to consult with visualization to continue!!!!!!!!!!!!!!
}
