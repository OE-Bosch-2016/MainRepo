package hu.nik.project.communication;

import java.io.*;

/**
 *
 * Created by zhodvogner on 2016.03.23.
 *
 **/

public class CommBusConnector {

    protected static final int WRITE_CHECK_WAIT_LOOP_MSECS = 50;
    protected static final int WAIT_TIME_AFTER_SEND_MSECS = 100;

    private CommBus commBus;                    // the owner communication-bus object
    private ICommBusDevice device;              // embedded (connected) comm-bus device
    private CommBusConnectorType connectorType; // type of connection

    private Class dataType;                   // type of data in the dataBuffer
    private byte[] byteDataBuffer;              // data on bus
    private boolean isDataInBuffer = false;     // data available for read
    private boolean isDataOutBuffer = false;    // data available for write
    private CommBusConnector connector = this;

    // listenerInvokerThread invokes active listeners
    private WriterThreadBase writerThreadBase;
    protected Thread writerThread;

    private class WriterThreadBase implements Runnable {
        private volatile boolean alive = true;

        public void run() {
            while (alive) {
                writeToCommBus();
                try { Thread.sleep(WRITE_CHECK_WAIT_LOOP_MSECS); } catch (InterruptedException ie) { alive = false; }
            }
        } // Write-Check-Wait-Loop

        //public void shutdown() { alive = false; }
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

        writerThreadBase = new WriterThreadBase();
        writerThread = new Thread(writerThreadBase);
    }

    //--------------------- finalizer

    @Override
    protected void finalize() throws Throwable {
        writerThread.interrupt();
        commBus.removeConnector(this);
        super.finalize();
    }

    //--------------------- send data

    public boolean send( Class dataType, Object dataObject ) throws CommBusException {
        String exceptionMessagePrefix = "Error in CommBusController send: ";
        if (dataType == null) {throw  new CommBusException(exceptionMessagePrefix + "sent object type cannot be null"); }
        if (dataObject == null) {throw  new CommBusException(exceptionMessagePrefix + "sent object cannot be null"); }
        if (isDataInBuffer) return false; // unreaded data in the buffer
        if (isDataOutBuffer) return false; // unwritten data in the buffer
        //if ((String)dataObject=="NewestDataArrived") {
        //    exceptionMessagePrefix = "send():";
        //}

        // Make commbus compatible data (microcontroller model)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(baos);
            out.writeObject(dataObject);
            if (baos.size() > CommBus.MAX_BUFFER_LENGTH_IN_BYTES) throw new CommBusException(exceptionMessagePrefix + "data-record is too long (size=" + baos.size() + " bytes)");
            this.byteDataBuffer = baos.toByteArray();
            this.dataType = dataType;
            this.isDataOutBuffer = true; // data-block for write is in the buffer (writer thread will be submit it)
        }
        catch (IOException e) {
            throw new CommBusException(exceptionMessagePrefix + e.getMessage());
        }

        // start worker thread if not in RUNNABLE state
        if (writerThread.getState() == Thread.State.NEW) {
            writerThread.start();
        }
        else if ((writerThread.getState() != Thread.State.RUNNABLE) && (writerThread.getState() != Thread.State.TIMED_WAITING) && (writerThread.getState() != Thread.State.WAITING))
        {
            writerThread = new Thread(writerThreadBase);
            writerThread.start();
        }
        try { Thread.sleep(WAIT_TIME_AFTER_SEND_MSECS); } catch (InterruptedException ie) { return false; }
        return true;
    }

    private void writeToCommBus() {
        if (isDataOutBuffer)
        try {
            if (commBus.write(connector, dataType, byteDataBuffer)) {
                isDataOutBuffer = false;
                exceptionThrown = null;
            }
        }
        catch (CommBusException e) {
            isDataOutBuffer = false;
            exceptionThrown = e;
        }
    }

    //--------------------- receive data

    public Object receive() throws CommBusException {
        if (!isDataInBuffer) return null;

        if (connectorType == CommBusConnectorType.WriteOnly) {
            reset();
            return null; // the buffer already is empty or it's a write-only connector
        }

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
