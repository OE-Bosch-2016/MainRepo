package hu.nik.project.framework;

import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.engineandgearbox.Gearbox;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObjectException;
import hu.nik.project.tsr.Tsr;
import hu.nik.project.wheels.Wheels;
import hun.nik.project.acc.ACC;
import hu.nik.project.communication.CommBus;
import hu.nik.project.engineandgearbox.Engine;
import ultrasonicsensor.Ultrasonicsensor;

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


    public BoschCar(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);

        acc = new ACC();
        commBus = new CommBus();
        engine = new Engine(0);
        gearbox = new Gearbox(0);
        tsr = new Tsr(commBus, CommBusConnectorType.SenderReceiver);
        wheels = new Wheels(commBus, CommBusConnectorType.SenderReceiver);
    }

    public void start() {

    }

    // Need to consult with visualization to continue!!!!!!!!!!!!!!
}
