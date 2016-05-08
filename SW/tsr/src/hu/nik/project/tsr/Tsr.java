package hu.nik.project.tsr;

import hu.nik.project.communication.*;
import hu.nik.project.environment.objects.DirectionSign;
import hu.nik.project.environment.objects.PrioritySign;
import hu.nik.project.environment.objects.SpeedSign;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;

/**
 * Created by CyberZero on 2016. 04. 07..
 */
public class Tsr implements ICommBusDevice{

    private CommBusConnector commBusConnector;
    private String stringData = "";
    private boolean enabled;
    private TsrMessagePackage packet = null;
    private Car boschCar;

    public Tsr(CommBus commBus, CommBusConnectorType commBusConnectorType, Car boschCar) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        this.boschCar = boschCar;
    }

    @Override
    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();

        if (dataType == DriverInputMessagePackage.class) {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                enabled = data.tsrIsActive();
            } catch (CommBusException e) {
                e.printStackTrace();
            }

        } else
        if (enabled) {
            if (dataType == PrioritySign.class) {
                try {
                    //send only giveaway and stop sign
                    PrioritySign data = (PrioritySign) commBusConnector.receive();
                    if (data.getObjectType() == PrioritySign.PrioritySignType.GIVEAWAY ||
                            data.getObjectType() == PrioritySign.PrioritySignType.STOP)
                        packet = new TsrMessagePackage(0, data.getCenter());

                } catch (CommBusException e) {
                    stringData = e.getMessage();
                    e.printStackTrace();
                }
            } else if (dataType == DirectionSign.class) {
                try {
                    //send anytype direction sign
                    DirectionSign data = (DirectionSign) commBusConnector.receive();
                    packet = new TsrMessagePackage(0, data.getCenter());
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                    e.printStackTrace();
                }
            } else if (dataType == SpeedSign.class) {
                try {
                    //send anytype, but different speedlimit
                    SpeedSign data = (SpeedSign) commBusConnector.receive();
                    switch (data.getObjectType()) {
                        case LIMIT_10:
                            packet = new TsrMessagePackage(10, data.getCenter());
                            break;
                        case LIMIT_20:
                            packet = new TsrMessagePackage(20, data.getCenter());
                            break;
                        case LIMIT_40:
                            packet = new TsrMessagePackage(40, data.getCenter());
                            break;
                        case LIMIT_50:
                            packet = new TsrMessagePackage(50, data.getCenter());
                            break;
                        case LIMIT_70:
                            packet = new TsrMessagePackage(70, data.getCenter());
                            break;
                        case LIMIT_90:
                            packet = new TsrMessagePackage(90, data.getCenter());
                            break;
                        case LIMIT_100:
                            packet = new TsrMessagePackage(100, data.getCenter());
                            break;
                    }
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringData() {
        return stringData;
    }

    public void doWork() {
        // send response message onto the bus (if necessary)
        if (packet != null) {
            try {

                if (commBusConnector.send(packet)) packet = null;
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }
    }
}
