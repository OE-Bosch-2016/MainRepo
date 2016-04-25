package hu.nik.project.tsr;

import hu.nik.project.communication.*;
import hu.nik.project.environment.objects.DirectionSign;
import hu.nik.project.environment.objects.PrioritySign;
import hu.nik.project.environment.objects.SpeedSign;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;

/**
 * Created by CyberZero on 2016. 04. 07..
 */
public class Tsr implements ICommBusDevice{

    private CommBusConnector commBusConnector;
    private String stringData = "";
    private boolean enabled;

    public Tsr(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
    }

    @Override
    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();

        if (dataType == DriverInputMessagePackage.class) {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                enabled = data.tsrIsActive();
            } catch (CommBusException e) {

            }

        } else
        if (dataType == PrioritySign.class) {
            try {
                //send only giveaway and stop sign
                PrioritySign data = (PrioritySign) commBusConnector.receive();
                if(data.getObjectType()== PrioritySign.PrioritySignType.GIVEAWAY ||
                        data.getObjectType() == PrioritySign.PrioritySignType.STOP)
                    commBusConnector.send( new TsrMessagePackage(0,data.getCenter()));
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        } else if (dataType == DirectionSign.class) {
            try {
                //send anytype direction sign
                DirectionSign data = (DirectionSign) commBusConnector.receive();
                commBusConnector.send( new TsrMessagePackage(0,data.getCenter()));
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        } else if (dataType == SpeedSign.class) {
            try {
                //send anytype, but different speedlimit
                SpeedSign data = (SpeedSign) commBusConnector.receive();
                TsrMessagePackage packet = null;
                switch(data.getObjectType()){
                    case LIMIT_10:
                        packet=new TsrMessagePackage(10,data.getCenter());
                        break;
                    case LIMIT_20:
                        packet=new TsrMessagePackage(20,data.getCenter());
                        break;
                    case LIMIT_40:
                        packet=new TsrMessagePackage(40,data.getCenter());
                        break;
                    case LIMIT_50:
                        packet=new TsrMessagePackage(50,data.getCenter());
                        break;
                    case LIMIT_70:
                        packet=new TsrMessagePackage(70,data.getCenter());
                        break;
                    case LIMIT_90:
                        packet=new TsrMessagePackage(90,data.getCenter());
                        break;
                    case LIMIT_100:
                        packet=new TsrMessagePackage(100,data.getCenter());
                        break;
                }
                if(packet!=null) commBusConnector.send(packet);
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        }
    }

    public String getStringData() {
        return stringData;
    }
}
