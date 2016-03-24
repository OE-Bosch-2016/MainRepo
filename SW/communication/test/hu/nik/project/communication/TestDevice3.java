package hu.nik.project.communication;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 *  It's a write-only device - it can only write onto the bus - no event-handling
 */
public class TestDevice3 implements ICommBusDevice {
    @Override
    public void commBusEvent(CommBusConnector connector) throws CommBusException {
        throw new UnsupportedOperationException("ERROR! commBusEvent not implemented on TestDevice3 (WriteOnly connector)");
    }
}
