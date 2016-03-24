package hu.nik.project.communication;

//import java.io.*;
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

    private static final int BUSREQUEST_WAIT_TIME_MSECS = 200;
    public static final int MAX_BUFFER_LENGTH_BYTES = 64;

    //private ByteArrayOutputStream outputStream = new ByteArrayOutputStream(MAX_BUFFER_LENGTH_BYTES);
    private byte[] dataBuffer;
    private int dataType;

    private List<CommBusConnector> connectors = new ArrayList<CommBusConnector>();
    private CommBusConnector acceptedConnector = null;

    // listenerInvokerThread invokes active listeners
    private Thread listenerInvokerThread = new Thread(new Runnable() {
        public void run()
        {
            invokeListeners();
        }
    });

    // create new connector and connect the device
    public synchronized CommBusConnector createConnector(ICommBusDevice device, CommBusConnectorType connectorType) {
        CommBusConnector connector = new CommBusConnector(this, device, connectorType);
        connectors.add(connector);
        if (connectors.size() == 1) listenerInvokerThread.start(); // listeners will be invoked asynchronously
        return connector;
    }

    // disconnects a device and remove a connector
    public synchronized void removeConnector(CommBusConnector connector) {
        connectors.remove(connector);
        connector = null;
    }

    public synchronized int getConnectorCount() {
        return connectors.size();
    }

    public synchronized boolean isBusFree() {
        return (acceptedConnector == null);
    }

    // writes data to bus and sends notifications to the connectors
    public synchronized boolean write(CommBusConnector connector, int dataType, byte[] data ) throws CommBusException {
        // validity checks
        if (connector.getConnectorType() == CommBusConnectorType.ReadOnly) throw new CommBusException("Connector is read-only.");
        if (data.length > MAX_BUFFER_LENGTH_BYTES) throw new CommBusException("Data-record too long.");
        if (!connectors.contains( connector )) throw new CommBusException("Unknown connector.");
        if (connector == acceptedConnector) return true;   // already accepted
        // bus-allocation
        if (acceptedConnector != null) { // the bus is busy, must wait...
            try { Thread.sleep(BUSREQUEST_WAIT_TIME_MSECS); } catch (InterruptedException ie) { return false; }
        }
        if (acceptedConnector != null) return false; // the bus is busy yet, sorry...
        // copy and store data into buffer
        this.dataBuffer = data.clone();
        this.dataType = dataType;
        // set the sender
        acceptedConnector = connector;  // OK - this will be the connector which is enabled for write-operation
        // notifications will be sended by the background thread (in invokeListeners)
        return true;
    }

    // invokeListeners passes the bus-content to the connected listeners
    private void invokeListeners() {
        boolean interrupted = false;
        while (!interrupted && (connectors.size() > 0)) {
            // transmission handling
            if ((acceptedConnector != null) && (dataBuffer != null)) {
                for (CommBusConnector connector : connectors) {
                    if ((connector != acceptedConnector) && (connector.getConnectorType() != CommBusConnectorType.WriteOnly)) {

                        connector.setExceptionThrown(null);
                        connector.setDataBuffer(dataBuffer.clone());    // data will be passed to the connector in own local buffer
                        connector.setDataType(dataType);                // dataType can describe the type of data-package or sender

                        try {                                                                    //
                            connector.getDevice().commBusEvent(connector); // the dataType will be passed as an argument of the event
                            // so the connector can decide want to deal with it or not
                        } catch (CommBusException e) {
                            connector.read(); // connector reset
                            connector.setExceptionThrown(e);
                        }
                    }
                }
                // all listener were notified
                dataBuffer = null; // dataBuffer is empty
                if (acceptedConnector != null) acceptedConnector = null; // bus is free (bus request is cleared)
            }
            // waiting for requests
            try { Thread.sleep(BUSREQUEST_WAIT_TIME_MSECS); } catch (InterruptedException ie) { interrupted = true; }
        }
    }

}
