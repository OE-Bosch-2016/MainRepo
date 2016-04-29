package hu.nik.project.communication;
/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Interface of communication bus listener.
 * This unit can connect to CommBus, will get CommBusEvents and can read data from the bus.
 */
public interface ICommBusDevice {
    // The devices have to handle the arrived exceptions, so the InvokeListeners don't need to deal with them
    void commBusDataArrived();
}
