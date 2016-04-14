package hu.nik.project.camera;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.People;
import hu.nik.project.environment.objects.Road;
import hu.nik.project.environment.objects.SceneObject;
import junit.framework.TestCase;
import hu.nik.project.ebs.EmergencyBreakSystem;
import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;
/**
 * Created by lmpala on 2016.04.14..
 */
public class CameraTest extends TestCase {

    CommBus commBus= new CommBus();
    CommBusConnectorType commBusConnectorType = CommBusConnectorType.Sender;
    Scene scene;
    Camera c;
    EmergencyBreakSystem e;

    public void setUp() throws Exception {
        super.setUp();
        c = new Camera(commBus, CommBusConnectorType.Sender, scene);
        e = new EmergencyBreakSystem(commBus, CommBusConnectorType.SenderReceiver);

        c.visibleObjects = new SceneObject[4];
        c.visibleObjects[1]= new People(new ScenePoint(10,10),0, People.PeopleType.MAN);
        c.SendToCom();
    }


    public void tearDown() throws Exception {

    }

    public void testCommBusDataArrived() throws Exception {

    }

    public void testSendToCom() throws Exception {

    }

    public void testGetClosestSign() throws Exception {

    }

    public void testGetLaneDistance() throws Exception {

    }

    public void testGetLaneType() throws Exception {

    }

}
