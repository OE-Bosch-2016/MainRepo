package hu.nik.project.lka;

import hu.nik.project.communication.*;

/**
 * Created by hodvogner.zoltan on 2016.04.08.
 *
 *  It's a device with two-way connenction, it can write onto the bus and can receive messages with type=1
 */
class TestDevice implements ICommBusDevice {

    private Class dataType = null;
    private CommBusConnector commBusConnector;
    private Class neededDataType;

    private String stringData = "no message was received";   // last error message if something wrong
    private Object lastData;

    public TestDevice(CommBus commBus, Class whatKindOfObjectIsNeededToTest, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        neededDataType = whatKindOfObjectIsNeededToTest;
    }

    @Override
    public void commBusDataArrived() {

        stringData = "";
        dataType = commBusConnector.getDataType();

        if (dataType == neededDataType) {
            try {
                lastData = commBusConnector.receive();
            } catch (CommBusException e) {
                stringData = e.getMessage();
            }
        }
    }

    public String getStringData() {
        return stringData;
    }
    public Object getLastData() { return lastData; }

    public CommBusConnector getCommBusConnector() { return commBusConnector;}
}
