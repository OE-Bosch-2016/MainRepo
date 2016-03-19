package hu.nik.project.environment;

import hu.nik.project.environment.objects.SceneObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Created by zhodvogner on 2016.03.15.
 *
 * Test class for ScenePoint
 */
public class SensorServerTest {

    private static SensorServer sensorServer;

    @BeforeClass
    public static void setUp() throws Exception {
        Scene scene = new Scene("TestSource\\testScene.xml");
        sensorServer = new SensorServer(scene);
    }

    @Test
    public void testGetVisibleSceneObjects() throws Exception {
        ScenePoint observerBase = new ScenePoint(1500, 2300);
        int observerRotation = 45;
        int viewAngle = 90;
        int viewDistance = 200;
        ArrayList<SceneObject> visibleSceneObjects = sensorServer.getVisibleSceneObjects( observerBase, observerRotation, viewAngle, viewDistance );
        Assert.assertEquals(2, visibleSceneObjects.size());
    }
}