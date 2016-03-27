package hu.nik.project.communication;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 *  It's a device with two-way connenction, it can write onto the bus and can receive messages with type=1
 */
class TestDevice implements ICommBusDevice {

    private Class dataType = null;
    private int intData = 0;
    private CommBusConnector commBusConnector;
    private String stringData = "";

    public TestDevice(CommBus commBus, Class whatKindOfObjectIsNeededToTest, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        dataType = whatKindOfObjectIsNeededToTest;
    }

    @Override
    public void commBusDataArrived() {
        if (commBusConnector.getDataType() == Integer.class) {
            dataType = commBusConnector.getDataType();
            try {
                intData = (Integer)commBusConnector.receive();
            }
            catch (CommBusException e) {
                throw new RuntimeException();
                //intData = 555;
            }

        }
        if (commBusConnector.getDataType() == String.class) {
            dataType = commBusConnector.getDataType();
            try {
                stringData = (String)commBusConnector.receive();
            }
            catch (CommBusException e) {

            }
        }
    }

    public Class DataType() {
        return dataType;
    }
    public int getIntData() {
        return intData;
    }
    public String getStringData() {
        return stringData;
    }
    public  CommBusConnector getCommBusConnector() { return commBusConnector;}
}
