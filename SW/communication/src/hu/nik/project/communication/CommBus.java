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

    public static final int MAX_BUFFER_LENGTH_BYTES = 64;
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream(MAX_BUFFER_LENGTH_BYTES);

    private static List<ICommBusListener> listeners = new ArrayList<ICommBusListener>();
    private static int acceptedUnitID = 0;

    private static Thread listenerInvokerThread = new Thread(new Runnable() {
        public void run()
        {
            invokeListeners();
        }
    });

    public static synchronized void connect(ICommBusListener listener) {
        listeners.add(listener);
    }

    public static synchronized void disconnect(ICommBusListener listener) {
        listeners.remove(listener);
    }

    public static synchronized OutputStream busRequest( int unitID ) throws CommBusException {

        // TODO: bus will be allocated only a given time-interval (request-time-out)

        if (unitID <= 0) throw new CommBusException("Invalid unit-ID.");
        if (unitID == acceptedUnitID) return outputStream;   // already accepted
        if (acceptedUnitID != 0) return null;                // the bus is busy

        // TODO: waiting for the bus (for a little time)

        acceptedUnitID = unitID;
        outputStream.reset();
        // successful bus-request, requester can write data into this outputStream
        return outputStream;
    }

    public static void busRelease( int unitID ) throws CommBusException {

        if((acceptedUnitID == 0) || ( unitID != acceptedUnitID )) throw new CommBusException("Bus request is missing.");
        listenerInvokerThread.start(); // listeners will be invoked asynchronously
    }

    private static void invokeListeners() {

        if ((acceptedUnitID > 0) && (outputStream != null)) {
            for (ICommBusListener listener : listeners) {

                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                listener.commBusEvent(inputStream);
            }
        }
        // all invoker were called
        outputStream.reset();
        acceptedUnitID = 0; // bus is free (bus request is cleared)
    }



}
