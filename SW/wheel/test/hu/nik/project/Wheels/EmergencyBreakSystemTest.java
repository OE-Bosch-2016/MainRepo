package hu.nik.project.ebs;

import hu.nik.project.camera.Camera;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.People;
import hu.nik.project.environment.objects.SceneObject;
import junit.framework.TestCase;

/**
 * Created by lmpala on 2016.04.14..
 */
public class EmergencyBreakSystemTest extends TestCase {

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
        assertEquals(c.visibleObjects,e.visibleObjectArray);
    }

    public void testSendToCom() throws Exception {

    }


}
