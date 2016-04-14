package hu.nik.project.ebs;

import hu.nik.project.camera.Camera;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.People;
import hu.nik.project.environment.objects.SceneObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lmpala on 2016.04.14..
 */
public class EmergencyBreakSystemTest {

    CommBus commBus = new CommBus();
    Scene scene;
    Camera c;
    EmergencyBreakSystem e;


    @Before
    public void setUp() throws Exception {
        c = new Camera(commBus, CommBusConnectorType.Sender, scene);
        e = new EmergencyBreakSystem(commBus,CommBusConnectorType.SenderReceiver);

        c.visibleObjects = new SceneObject[4];
        c.visibleObjects[1] = new People(new ScenePoint(10,10),0, People.PeopleType.MAN);
        c.SendToCom();

    }

    @Test
    public void commBusDataArrived() throws Exception {
        assertArrayEquals(c.visibleObjects,e.visibleObjectArray);
        assertEquals(c.visibleObjects,e.visibleObjectArray);
    }

    @Test
    public void sendToCom() throws Exception {

    }

}