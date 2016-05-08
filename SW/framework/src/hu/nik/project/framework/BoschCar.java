package hu.nik.project.framework;

import hu.nik.project.camera.Camera;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.Scene;
import hu.nik.project.gearbox.Gearbox;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObjectException;
import hu.nik.project.hmi.manager.HmiManager;
import hu.nik.project.radar.RadarModul;
import hu.nik.project.tsr.Tsr;
import hu.nik.project.ultrasonicsensor.UltrasonicModul;
import hu.nik.project.wheels.Wheels;
import hu.nik.project.acc.ACC;
import hu.nik.project.communication.CommBus;
import hu.nik.project.engine.Engine;
import hu.nik.project.ultrasonicsensor.Ultrasonicsensor;

import hu.nik.project.framework.main.Main;

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
    UltrasonicModul ultrasonic; // The constructor is not straightforward, have to discuss it with the team
    Camera camera;
    RadarModul radar;
    Wheels wheels;
    HmiManager hmiManager;


    public BoschCar(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);

        // Commbus
        commBus = new CommBus();

        // Sensors
        camera = new Camera(commBus, CommBusConnectorType.Sender, Main.scene, this);
        radar = new RadarModul(Main.scene, commBus, (float)45.0, 42);
        ultrasonic = new UltrasonicModul(commBus, CommBusConnectorType.Sender, this, Main.scene);

        // Driver assistant components
        acc = new ACC(commBus, CommBusConnectorType.SenderReceiver);
        tsr = new Tsr(commBus, CommBusConnectorType.SenderReceiver);

        // Base components
        engine = new Engine(commBus, CommBusConnectorType.SenderReceiver);
        gearbox = new Gearbox(0, commBus, CommBusConnectorType.SenderReceiver);
        wheels = new Wheels(commBus, CommBusConnectorType.SenderReceiver);
        hmiManager = hmiManager.newInstance(commBus, CommBusConnectorType.Sender);
    }

    public void doWork() {

        // Do the sensor job!
        camera.doWork();
        radar.doWork();

        // Send the driverinput package
        hmiManager.doWork();

        // Call the DA modules
        tsr.doWork();
        acc.doWork();

        // Base components call chain
        engine.doWork();
        gearbox.doWork();
        wheels.doWork();
    }

    public void setCarPosition(ScenePoint position, int rotation) {
        this.setBasePositonAndRotation(position, rotation);
    }

    public void setDriverInput(int tempomatSpeed, int tick, int gearLeverPosition, boolean engine, boolean acc,
                               boolean tsr, boolean pp, boolean aeb, boolean lka) {
        hmiManager.setDriverInputMessagePackage(tempomatSpeed, tick, gearLeverPosition, engine, acc, tsr, pp, aeb, lka);
    }
}
