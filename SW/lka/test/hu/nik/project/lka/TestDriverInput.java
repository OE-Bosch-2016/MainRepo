package hu.nik.project.lka;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;

/**
 * Created by zhodvogner on 2016. ápr. 29..
 */
public class TestDriverInput implements ICommBusDevice {

    private CommBusConnector commBusConnector;

    public TestDriverInput(CommBus commBus) {
        commBusConnector = commBus.createConnector(this, CommBusConnectorType.Sender);
    }

    @Override
    public void commBusDataArrived() {}

    public void SendAsDriverInput(boolean lkaEnabled) throws Exception {
        DriverInputMessagePackage message = new DriverInputMessagePackage(0,0,0,0,0,0,true,true,true,false,false,lkaEnabled);
        commBusConnector.send(message);
    }

}
