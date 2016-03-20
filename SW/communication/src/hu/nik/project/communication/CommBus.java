package hu.nik.project.communication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Main class of communication
 */
public class CommBus {

    private static final int BUSREQUEST_WAIT_TIME_MSECS = 200;
    private static final int BUSREQUEST_TIME_OUT_MSECS = 500;
    public static final int MAX_BUFFER_LENGTH_BYTES = 64;
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream(MAX_BUFFER_LENGTH_BYTES);

    private static List<ICommBusListenerUnit> listeners = new ArrayList<ICommBusListenerUnit>();
    private static ICommBusUnit acceptedUnit = null;

    // busRequestTrackerThread controls the time-interval of bus-requests
    private static Thread busRequestTrackerThread = new Thread(new Runnable() {
        public void run()
        {
            busRequestTracker();
        }
    });

    // listenerInvokerThread invokes active listeners
    private static Thread listenerInvokerThread = new Thread(new Runnable() {
        public void run()
        {
            invokeListeners();
        }
    });

    // it connects a unit or a listener to the bus
    public static synchronized void connect(ICommBusListenerUnit listener) {
        listeners.add(listener);
    }

    // id disconnects a unit or a listener
    public static synchronized void disconnect(ICommBusListenerUnit listener) {
        listeners.remove(listener);
    }

    // busRequest gives possibility for connected CommBusUnits for requesting a unique bus-usage
    // return-value: OutputStream if bus-request is successful or null (if bus is busy)
    public static synchronized OutputStream busRequest( ICommBusUnit commBusUnit ) throws CommBusException {

        if (!(commBusUnit instanceof ICommBusUnit)) throw new CommBusException("Invalid unit-type.");
        if (commBusUnit == acceptedUnit) return outputStream;   // already accepted

        if (acceptedUnit != null) { // the bus is busy
            try { Thread.sleep(BUSREQUEST_WAIT_TIME_MSECS); } catch (InterruptedException ie) { return null; }
        }
        if (acceptedUnit != null) return null; // the bus is busy yet

        if( !listeners.contains( commBusUnit ) ) throw new CommBusException("Not connected.");

        outputStream.reset();
        acceptedUnit = commBusUnit;
        busRequestTrackerThread.start();

        // successful bus-request:
        // the requester can write data into the outputStream in next BUSREQUEST_TIME_OUT_MSECS milliseconds
        return outputStream;
    }

    // with busRelease the active bus-requester signals the end of bus-access
    public static void busRelease( ICommBusUnit comBusUnit ) throws CommBusException {

        if((acceptedUnit == null) || ( comBusUnit != acceptedUnit )) throw new CommBusException("Invalid operation.");
        listenerInvokerThread.start(); // listeners will be invoked asynchronously
    }

    // busRequestTracker checks BUSREQUEST_TIME_OUT_MSECS, if elapsed it releases the bus
    private static void busRequestTracker() {

        long startTime = System.currentTimeMillis();
        while ( (acceptedUnit != null) && ( (System.currentTimeMillis() - startTime) < BUSREQUEST_TIME_OUT_MSECS) )
            try { Thread.sleep(50); } catch (InterruptedException ie) { break; }

        if (acceptedUnit != null) acceptedUnit = null; // bus-request timed out -> must freeze the bus
    }

    // invokeListeners passes the bus-content to the connected listeners
    private static void invokeListeners() {

        if ((acceptedUnit != null) && (outputStream != null)) {
            for (ICommBusListenerUnit listener : listeners) {

                if( listener != acceptedUnit) /* written data not echoed */ {
                    InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    listener.commBusEvent(inputStream);
                }
            }
        }
        // all invoker were called
        if (acceptedUnit != null) acceptedUnit = null; // bus is free (bus request is cleared)
    }

}
