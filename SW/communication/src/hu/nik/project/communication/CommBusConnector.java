package hu.nik.project.communication;

/**
 *
 * Created by zhodvogner on 2016.03.23.
 *
 **/

public class CommBusConnector {

    private CommBus commBus;                    // the owner communication-bus object
    private ICommBusDevice device;              // embedded (connected) comm-bus device
    private CommBusConnectorType connectorType; // type of connection

    private int dataType = 0;                   // type of data in the dataBuffer
    private byte[] dataBuffer;                  // data on bus
    private boolean isDataInBuffer = false;     // data available for read

    private Exception exceptionThrown = null;   // last exception on this connector

    //--------------------- getters and setters

    public ICommBusDevice getDevice() {
        return device;
    }

    public CommBusConnectorType getConnectorType() {
        return connectorType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        if (!isDataInBuffer) return 0;
        return dataType;
    }

    public void setDataBuffer(byte[] dataBuffer) {
        this.dataBuffer = dataBuffer;
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

    //--------------------- write

    public boolean write( int dataType, byte[] data ) throws CommBusException {
        boolean result = commBus.write(this, dataType, data);
        if (result && (connectorType == CommBusConnectorType.WriteOnly)) isDataInBuffer = false;
        return result;
    }

    //--------------------- read

    public byte[] read() /*throws CommBusException*/ {
        if ((!isDataInBuffer) || (connectorType == CommBusConnectorType.WriteOnly)) return new byte[0]; // the buffer already is empty or it's a write-only connector
        isDataInBuffer = false; // read empties the buffer
        dataType = 0;
        return dataBuffer; // result of read
    }

}
