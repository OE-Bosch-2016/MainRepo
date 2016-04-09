package hu.nik.project.tsr;

import hu.nik.project.communication.*;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SimpleRoad;
import hu.nik.project.environment.objects.SpeedSign;

/**
 * Created by hodvogner.zoltan on 2016.04.08.
 *
 *  It's a device with two-way connenction, it can write onto the bus and can receive messages with type=1
 */
class TestDevice implements ICommBusDevice {

    private Class dataType = null;
    private CommBusConnector commBusConnector;
    private Class neededDataType;

    private String stringData = "";   // last error message if something wrong
    private TsrPacket tsrPacketData;
    private SpeedSign speedSignData;

    public TestDevice(CommBus commBus, Class whatKindOfObjectIsNeededToTest, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        neededDataType = whatKindOfObjectIsNeededToTest;
    }

    @Override
    public void commBusDataArrived() {

        stringData = "";
        dataType = commBusConnector.getDataType();

        if (dataType == neededDataType) {

            if (dataType == TsrPacket.class) {
                try {
                    tsrPacketData = (TsrPacket) commBusConnector.receive();
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
            if (dataType == SpeedSign.class) {
                try {
                    speedSignData = (SpeedSign) commBusConnector.receive();
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
        }
    }

    public String getStringData() {
        return stringData;
    }
    public TsrPacket getTsrPacketData() { return tsrPacketData; }
    public SpeedSign getSpeedSignData() { return speedSignData; }

    public CommBusConnector getCommBusConnector() { return commBusConnector;}
}
