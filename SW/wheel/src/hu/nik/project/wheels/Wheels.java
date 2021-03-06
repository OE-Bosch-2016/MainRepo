package hu.nik.project.wheels;

import hu.nik.project.acc.ACCMessagePackage;
import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

import hu.nik.project.ebs.EmergencyBreakSystemMessagePackage;
import hu.nik.project.gearbox.GearboxMessagePackage;
import hu.nik.project.hmi.Hmi;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
import hu.nik.project.engine.EngineMessagePackage;
import hu.nik.project.visualisation.car.CarController;

public class Wheels implements IWheels, ICommBusDevice {

    private CommBusConnector commBusConnector;
    private CarController carController;
    private Hmi hmi;

    //imput buffers
    private double engineRPM;
    private double engineTorque;
    private int gearStage;
    private float hmiWheel;
    private float hmiBrake;
    private float accBrake;
    private float ebsBrake;
    private float currentBrake;

    //adjust these by testing
    static private double turningAdjustment = 0.2;      //proportion to simulate turning realistically
    static private double framerate = 24;
    static private double brakeAdjustment = 1.8;        //proportion to turn brake pedal to deceleration			//deceleration of environment

    //output buffers
    private double speed;                           //given in km/h
    private double direction;                       //given in degree 0-360

    @Override
    public void commBusDataArrived() {

        Class dataType = commBusConnector.getDataType();

        if (dataType == EngineMessagePackage.class) {
            try {
                engineRPM = ((EngineMessagePackage) commBusConnector.receive()).getRpm();
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }

        if (dataType == DriverInputMessagePackage.class) {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                hmiWheel = data.getWheelAngle();
                hmiBrake = data.getCarBreak();
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }

        if (dataType == GearboxMessagePackage.class) {
            try {
                GearboxMessagePackage data = (GearboxMessagePackage) commBusConnector.receive();
                engineTorque = data.getTorque();
                gearStage = data.getGearStage();

            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }

        if (dataType == ACCMessagePackage.class) {
            try {
                accBrake = ((ACCMessagePackage) commBusConnector.receive()).getBreakPedal();

            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }

        if (dataType == EmergencyBreakSystemMessagePackage.class) {
            try {
                ebsBrake = ((EmergencyBreakSystemMessagePackage) commBusConnector.receive()).deceleration;

            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }

    }

    public Wheels(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        carController = CarController.newInstance();
        hmi = Hmi.newInstance();

        speed = 0;
        direction = 0;
    }

    @Override
    public void doWork() {
        calcDirection();    //calculates first because new direction is effected by last speed
        calcSpeed();
        carController.autonomousController((int) direction, (float) speed);
    }

    private void calcDirection() {
        double phiDirection = hmiWheel * speed * turningAdjustment / framerate;
        direction += phiDirection;
        direction = direction % 360;
        if (direction < 0) {
            direction = 360 + direction;
        }
    }

    private void calcSpeed() {
        currentBrake = hmiBrake;

        if (accBrake != -1) {
            currentBrake = accBrake;
        }

        if (ebsBrake != -1) {
            currentBrake = ebsBrake;
        }

        accBrake = -1;
        ebsBrake = -1;
        /*
        Power (kW) = Torque (N.m) x Speed (RPM) /0.0095488
		c=0.3
		D=1.25
		A=3
		v = (2*P/(c*D*A))^(1/3)
		natureBrake=0.3*1.25*3=1.125;
		*/

        double scale = 0;
        if (gearStage < 2) {
            scale = 1;
        } else if (gearStage == 2) {
            scale = 4;
        } else if (gearStage == 3) {
            scale = 9;
        } else if (gearStage == 4) {
            scale = 16;
        } else {
            scale = 25;
        }
        speed = Math.pow((2 * (engineTorque * engineRPM / 9.5488 * scale)), new Double("0.3333333")) / 10; // 10 is to change from km/h to the pixelmap/h
        hmi.mileage((float) speed * 10);
        if (engineTorque >= 0) {
            if (speed - brakeAdjustment * currentBrake > 0) {
                speed -= brakeAdjustment * currentBrake; //have to change this to adjust for boolean brake variable
            } else {
                speed = 0;
            }
        } else {
            if (speed - brakeAdjustment * currentBrake < 0) {
                speed += brakeAdjustment * currentBrake;
            } else {
                speed = 0;
            }
        }
        //System.out.println(engineRPM + "<-rpm " + engineTorque + "<-torque " + speed*10);
    }
}
