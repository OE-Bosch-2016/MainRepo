package hu.nik.project.communication;

import jdk.internal.util.xml.impl.Input;

import java.io.InputStream;
import java.util.stream.Stream;

/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Interface of communication bus listener.
 * This unit can connect to CommBus, will get CommBusEvents and can read data from the bus.
 */
public interface ICommBusDevice {
    void commBusDataArrived();
}
