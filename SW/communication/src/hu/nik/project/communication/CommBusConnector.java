package hu.nik.project.communication;

import java.io.*;

/**
 *
 * Created by zhodvogner on 2016.03.23.
 *
 * Class for the CommBusConnector
 **/

public class CommBusConnector {

    private CommBus commBus;                    // the owner communication-bus object
    private ICommBusDevice device;              // embedded (connected) comm-bus device
    private CommBusConnectorType connectorType; // type of connection

    private Class dataType;                   // type of data in the dataBuffer
    private byte[] byteDataBuffer;              // data on bus
    private boolean isDataInBuffer = false;     // data available for read
    private boolean isDataOutBuffer = false;    // data available for write
    private CommBusConnector connector = this;

    private Exception exceptionThrown = null;   // last exception on this connector

    //--------------------- getters and setters

    protected ICommBusDevice getDevice() {
        return device;
    }

    protected CommBusConnectorType getConnectorType() {
        return connectorType;
    }

    protected void setDataType(Class dataType) {
        this.dataType = dataType;
    }

    public Class getDataType() {
        if (!isDataInBuffer) return null;
        return dataType;
    }

    protected void setDataBuffer(byte[] dataBuffer) {
        this.byteDataBuffer = dataBuffer;
        this.isDataInBuffer = (dataBuffer != null);
    }

    protected void setExceptionThrown(Exception exceptionThrown) {
        this.exceptionThrown = exceptionThrown;
    }

    //--------------------- constructor

    protected CommBusConnector(CommBus commBus, ICommBusDevice device, CommBusConnectorType connectorType) {
        this.commBus = commBus;
        this.device = device;
        this.connectorType = connectorType;
    }

    //--------------------- send data

    public boolean send(Object dataObject) throws CommBusException {
        Class dataType = dataObject.getClass(); // taa-daaa!!!
        String exceptionMessagePrefix = "Error in CommBusConnector send: ";
        if (connectorType == CommBusConnectorType.Receiver) { throw new CommBusException(exceptionMessagePrefix + "Cannot send with this connector");}
        if (dataType == null) {throw new CommBusException(exceptionMessagePrefix + "Sent object type cannot be null"); }
        if (dataObject == null) {throw new CommBusException(exceptionMessagePrefix + "Sent object cannot be null"); }
        if (isDataInBuffer) return false; // unreaded data in the buffer
        if (isDataOutBuffer) return false; // unwritten data in the buffer

        // Make commbus compatible data (microcontroller model)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(baos);
            out.writeObject(dataObject);
            if (baos.size() > CommBus.MAX_BUFFER_LENGTH_IN_BYTES) throw new CommBusException(exceptionMessagePrefix + "Data-record is too long (size=" + baos.size() + " bytes)");
            this.byteDataBuffer = baos.toByteArray();
            this.dataType = dataType;
            this.isDataOutBuffer = true; // data-block for write is in the buffer (writer thread will be submit it)
        }
        catch (IOException e) {
            throw new CommBusException(exceptionMessagePrefix + e.getMessage());
        }


        writeToCommBus();

        return true;
    }

    private boolean writeToCommBus() {
        if (isDataOutBuffer)
        try {
            if (commBus.write(connector, dataType, byteDataBuffer)) {
                isDataOutBuffer = false;
                exceptionThrown = null;
                return true;
            }
        }
        catch (CommBusException e) {
            isDataOutBuffer = false;
            exceptionThrown = e;
            return false;
        }
        return false;
    }

    //--------------------- receive data

    public Object receive() throws CommBusException {
        if (connectorType == CommBusConnectorType.Sender) { throw  new CommBusException("Error in CommBusConnector receive: Cannot receive with this connector"); }
        if (!isDataInBuffer) return null;

        // Convert object from commbus bytes
        try {
            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(byteDataBuffer));
            Object object = in.readObject();
            reset();
            return object;
        }
        catch (IOException|ClassNotFoundException e) {
            throw new CommBusException("Error in CommBusConnector receive: " + e.getMessage());
        }
    }

    private void reset() {
        isDataInBuffer = false; // read empties the buffer
        dataType = null;
    }

}
