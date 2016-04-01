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

    protected static final int WRITE_TIME_OUT_MSECS = 1000;

    private CommBus commBus;                    // the owner communication-bus object
    private ICommBusDevice device;              // embedded (connected) comm-bus device
    private CommBusConnectorType connectorType; // type of connection

    private Class dataType;                   // type of data in the dataBuffer
    private byte[] byteDataBuffer;                  // data on bus
    private boolean isDataInBuffer = false;     // data available for read
    private CommBusConnector connector = this;

    // listenerInvokerThread invokes active listeners
    private WriterThreadBase writerThreadBase;
    protected Thread writerThread;

    private class WriterThreadBase implements Runnable {
        private volatile boolean alive = true;

        public void run() {
            while (alive) writeToCommBus();
        }

        public void shutdown() {
            alive = false;
        }
    }

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
        this.isDataInBuffer = true;
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

    //--------------------- finalizer

    @Override
    protected void finalize() throws Throwable {
        commBus.removeConnector(this);
        super.finalize();
    }

    //--------------------- send data

    public boolean send( Class dataType, Object dataObject ) throws CommBusException {

        if (dataType == null) {throw  new CommBusException("Error in CommBusController send: " + "sent object type cannot be null"); }
        if (dataObject == null) {throw  new CommBusException("Error in CommBusController send: " + "sent object cannot be null"); }
        if (isDataInBuffer) return false;

        // Make commbus compatible data (microcontroller model)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(baos);
            out.writeObject(dataObject);
            //??? baos.size() check instead of later byteDataBuffer.length check
            if (baos.size() > CommBus.MAX_BUFFER_LENGTH_IN_BYTES) throw new CommBusException("Error in CommBusController: Data-record too long = " + baos.size());
            this.byteDataBuffer = baos.toByteArray();
            //if (byteDataBuffer.length > CommBus.MAX_BUFFER_LENGTH_IN_BYTES) throw new CommBusException("Error in CommBusController: Data-record too long = " + byteDataBuffer.length);
            this.isDataInBuffer = true;
            this.dataType = dataType;
        }
        catch (IOException e) {
            throw new CommBusException("Error in CommBusController send: " + e.getMessage());
        }

        // asynchronous write operation
        writerThreadBase = new WriterThreadBase();
        writerThread = new Thread(writerThreadBase);
        writerThread.start();

        //??? this "sleep" is unneccessary - HZ
        // Wait for the send to finish
        /*
        try {
            Thread.sleep(CommBus.BUSREQUEST_WAIT_TIME_MSECS);
        } catch (InterruptedException e) {}
        */
        return true;
    }

    private void writeToCommBus() {
        try {
            if (commBus.write(connector, dataType, byteDataBuffer)) {
                isDataInBuffer = false;
                writerThreadBase.shutdown();
            }
        }
        catch (CommBusException e) {
            isDataInBuffer = false;
            exceptionThrown = e;
        }
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
        catch (IOException|ClassNotFoundException e) {
            throw new CommBusException("Error in CommBusController receive: " + e.getMessage());
        }
    }

    private void reset() {
        isDataInBuffer = false; // read empties the buffer
        dataType = null;
    }

}
