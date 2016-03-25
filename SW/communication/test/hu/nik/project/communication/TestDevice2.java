package hu.nik.project.communication;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 *  It's a read-only device, it cannot write onto the bus, but can receive messages with type=2
 *
 */
public class TestDevice2 implements ICommBusDevice {

    private int lastEventDataType = 0;
    private byte[] lastData = null;

    @Override
    public void commBusEvent(CommBusConnector connector) throws CommBusException {
        if (connector.getDataType() == 2)
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
