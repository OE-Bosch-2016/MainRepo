package hu.nik.project.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Main class of communication
 *
 * usage:
 *
 * Must create device-classes which are implementing the ICommBusDevice interface.
 * These devices will be attachable to the communication bus with the createConnector() method.
 * With the returned CommBusConnector object we can write and/or read data.
 *
 * 0. Initialization of CommBus:
 *
 * CommBus commBus = new CommBus();
 *
 * 1. Instantiating connector(s):
 *
 * device = (a class which implements ICommBusDevice interface)
 * CommBusConnector connector = createConnector( device, CommBusConnectorType.ReadWrite );
 *
 * 2. Writing to the bus:
 *
 * connector.write( dataType, data ); // int dataType, byte[] data // return: boolean
 *
 * 3. ICommBusDevice get notification when commBusEvent is fired:
 *
 * public void commBusEvent( int dataType ) {
 *     if (dataType is an interesting dataType) ...
 * }
 *
 * 4. Reading from the bus:
 *
 * byte[] data = connector.read();
 *
 *
 */
public class CommBus {

    protected static final int BUSREQUEST_WAIT_TIME_MSECS = 200;
    protected static final int MAX_BUFFER_LENGTH_IN_BYTES = 512;

    //private ByteArrayOutputStream outputStream = new ByteArrayOutputStream(MAX_BUFFER_LENGTH_BYTES);
    private byte[] byteDataBuffer;  // represents the bytes on the bus
    private Class dataType;

    private List<CommBusConnector> connectors = new ArrayList<CommBusConnector>();
    private CommBusConnector acceptedConnector = null;

    // create new connector and connect the device
    public CommBusConnector createConnector(ICommBusDevice device, CommBusConnectorType connectorType) {
        CommBusConnector connector = new CommBusConnector(this, device, connectorType);
        connectors.add(connector);
        return connector;
    }

    // disconnects a device and remove a connector
    public void removeConnector(CommBusConnector connector) {
        connectors.remove(connector);
        connector = null;
    }

    public int getConnectorCount() {
        return connectors.size();
    }

    public boolean isBusFree() {
        return (acceptedConnector == null);
    }

    // writes data to bus and sends notifications to the connectors
    protected boolean write(CommBusConnector connector, Class dataType, byte[] data ) throws CommBusException {
        // validity checks
        if (connector.getConnectorType() == CommBusConnectorType.ReadOnly) throw new CommBusException("CommBus.write error: Connector is read-only.");
        if (!connectors.contains( connector )) throw new CommBusException("CommBus.write error: Unknown connector.");
        if (dataType == null) throw new CommBusException("CommBus.write error: The dataType is null.");
        if (connector == acceptedConnector) return true;   // already accepted
        // bus-allocation
        if (acceptedConnector != null) { // the bus is busy, must wait...
            try { Thread.sleep(BUSREQUEST_WAIT_TIME_MSECS); } catch (InterruptedException ie) { return false; }
        }
        if (acceptedConnector != null) return false; // the bus is busy yet, sorry...
        // copy and store data into buffer
        this.byteDataBuffer = data.clone();
        this.dataType = dataType;
        // set the sender
        acceptedConnector = connector;  // OK - this will be the connector which is enabled for write-operation
        invokeListeners();
        return true;
    }

    // invokeListeners passes the bus-content to the connected listeners

    // !!!!!!!!!!! When write occours THIS should invoke without THREAD !!!!!!!!!!!!!!!!!!
    private void invokeListeners() {

            // HACK!!
            // acceptedConnector.setDataBuffer(null);
            // acceptedConnector.setDataType(null);

            // transmission handling
                for (CommBusConnector connector : connectors) {
                    if ((connector != acceptedConnector) && (connector.getConnectorType() != CommBusConnectorType.WriteOnly)) {

                        //connector.setExceptionThrown(null);
                        connector.setDataBuffer(byteDataBuffer.clone());    // data will be passed to the connector in own local buffer
                        connector.setDataType(dataType);                // dataType can describe the type of data-package or sender

                        // the dataType will be passed as an argument of the event, so the connector can decide want to deal with it or not
                        connector.getDevice().commBusDataArrived();
                    }
                }
                    // all listener were notified

                byteDataBuffer = null; // dataBuffer is empty
                if (acceptedConnector != null) acceptedConnector = null; // bus is free (bus request is cleared)

    }

    /*public void shutdown() {
        invokerThread.shutdown();
    }*/
}
