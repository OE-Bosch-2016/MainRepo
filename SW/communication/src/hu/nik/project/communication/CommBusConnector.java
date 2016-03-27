package hu.nik.project.communication;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;

import java.io.IOException;
/**
 *
 * Created by zhodvogner on 2016.03.23.
 *
 **/

public class CommBusConnector {

    private CommBus commBus;                    // the owner communication-bus object
    private ICommBusDevice device;              // embedded (connected) comm-bus device
    private CommBusConnectorType connectorType; // type of connection

    private Class dataType;                   // type of data in the dataBuffer
    private byte[] byteDataBuffer;                  // data on bus
    private boolean isDataInBuffer = false;     // data available for read

    private Exception exceptionThrown = null;   // last exception on this connector

    //--------------------- getters and setters

    public ICommBusDevice getDevice() {
        return device;
    }

    public CommBusConnectorType getConnectorType() {
        return connectorType;
    }

    public void setDataType(Class dataType) {
        this.dataType = dataType;
    }

    public Class getDataType() {
        if (!isDataInBuffer) return null;
        return dataType;
    }

    public void setDataBuffer(byte[] dataBuffer) {
        this.byteDataBuffer = dataBuffer;
        this.isDataInBuffer = true;
    }

    public void setExceptionThrown(Exception exceptionThrown) {
        this.exceptionThrown = exceptionThrown;
    }

    //--------------------- constructor

    public CommBusConnector(CommBus commBus, ICommBusDevice device, CommBusConnectorType connectorType) {
        this.commBus = commBus;
        this.device = device;
        this.connectorType = connectorType;
    }

    //--------------------- finalizer

    @Override
    protected void finalize() throws Throwable {
        commBus.removeConnector(this);
        super.finalize();
    }

    //--------------------- send data

    public boolean send( Class dataType, Object dataObject ) throws CommBusException {

        // Make commbus compatible data (microcontroller model)
        byte[] byteData = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(baos);
            out.writeObject(dataObject);
            byteData = baos.toByteArray();
            if (byteData.length > CommBus.MAX_BUFFER_LENGTH_IN_BYTES) throw new CommBusException("Error in CommBusController: Data-record too long.");
        }
        catch (IOException e) {
            throw new CommBusException("Error in CommBusController send: " + e.getMessage());
        }

        boolean result = commBus.write(this, dataType, byteData);
        if (result && (connectorType == CommBusConnectorType.WriteOnly)) isDataInBuffer = false;
        return result;
    }

    //--------------------- receive data

    public Object receive() throws CommBusException {
        if ((!isDataInBuffer) || (connectorType == CommBusConnectorType.WriteOnly)) return null; // the buffer already is empty or it's a write-only connector

        // Convert object from commbus bytes
        try {
            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(byteDataBuffer));
            Object object = in.readObject();
            reset();
            return object;
        }
        catch (IOException e) {
            throw new CommBusException("Error in CommBusController receive: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            throw new CommBusException("Error in CommBusController receive: " + e.getMessage());
        }
    }

    public void reset() {
        isDataInBuffer = false; // read empties the buffer
        dataType = null;
    }

}
