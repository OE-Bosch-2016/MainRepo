package hu.nik.project.communication;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 *  It's a device with two-way connenction, it can write onto the bus and can receive messages with type=1
 */
public class TestDevice1 implements ICommBusDevice {

    private int lastEventDataType = 0;
    private byte[] lastData = null;

    @Override
    public void commBusEvent(CommBusConnector connector) throws CommBusException {
        if (connector.getDataType() == 1)
        {
            lastEventDataType = connector.getDataType();
            lastData = connector.read();
        }
        // ... do something ...
    }

    public int getLastEventDataType() {
        return lastEventDataType;
    }

    public byte[] getLastData() {
        return lastData;
    }
}
