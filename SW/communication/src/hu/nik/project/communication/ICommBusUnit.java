package hu.nik.project.communication;

/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Interface of communication bus unit.
 * This unit is a listener, plus this unit can request the bus and can write data to the bus.
 * All other listener unit will be notified and get possibility for reading bus-content.
 */
public interface ICommBusUnit extends ICommBusListenerUnit {

}
