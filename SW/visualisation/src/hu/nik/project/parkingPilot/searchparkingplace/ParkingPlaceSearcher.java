package hu.nik.project.parkingPilot.searchparkingplace;

import hu.nik.project.communication.*;

/**
 * Created by haxxi on 2016.04.15..
 */
public class ParkingPlaceSearcher implements ICommBusDevice {

    private CommBusConnector commBusConnector;
    private CommBus commBus;
    CommBusConnectorType commBusConnectorType;

    public ParkingPlaceSearcher() {
        commBus = new CommBus();
        commBusConnectorType = CommBusConnectorType.Receiver;
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
    }

    public void commBusDataArrived() {
        Class dataType = commBusConnector.getDataType();
        if (dataType == ParkingPlaceSearcher.class) {
            try {
                ParkingPlaceSearcher data = (ParkingPlaceSearcher) commBusConnector.receive();
            } catch (CommBusException e) {
                e.getMessage();
            }
        }
    }
}
