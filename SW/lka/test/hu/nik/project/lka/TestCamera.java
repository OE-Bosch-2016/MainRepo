package hu.nik.project.lka;

import hu.nik.project.camera.CameraMessagePackage;
import hu.nik.project.communication.*;
import hu.nik.project.environment.objects.SceneObject;

/**
 * Created by zhodvogner on 2016. ápr. 29..
 */
public class TestCamera implements ICommBusDevice {

    private CommBusConnector commBusConnector;

    public TestCamera(CommBus commBus) {
        commBusConnector = commBus.createConnector(this, CommBusConnectorType.Sender);
    }

    @Override
    public void commBusDataArrived() {}

    public void SendAsCameraMessage(double laneDistance) throws Exception {
        SceneObject closestSign = null;
        boolean IsLaneRestricted = false;
        CameraMessagePackage message = new CameraMessagePackage(closestSign,laneDistance,IsLaneRestricted); //so it doesnt have to remake it every time
        commBusConnector.send(message);
    }

}
