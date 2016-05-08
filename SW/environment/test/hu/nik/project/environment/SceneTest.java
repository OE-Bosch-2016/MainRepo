package hu.nik.project.environment;

import hu.nik.project.environment.objects.AdvancedRoad;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.environment.objects.Car;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Róbert on 2016.02.29..
 *
 * Test class for scene
 */
public class SceneTest {

    private static Scene scene;

    @BeforeClass
    public static void setUp() throws Exception {
        scene = new Scene("TestSource\\testScene.xml");
    }

    @Test(expected=XMLParserException.class)
    public void testExist() throws Exception{
        scene = new Scene("TestSource\\notExist.xml");
    }

    @Test
    public void testGetSceneWidth() throws Exception {
        Assert.assertEquals(5000, scene.getSceneWidth());
    }

    @Test
    public void testGetSceneHeight() throws Exception {
        Assert.assertEquals(4000, scene.getSceneHeight());
    }

    @Test
    public void testGetSceneObjectByPosition() throws Exception {
        SceneObject testObject = scene.getSceneObjectByPosition(new ScenePoint(2525, 2263));
        Assert.assertEquals(testObject.getClass(), AdvancedRoad.class);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("Scene Width: 5000 Height: 4000 Number of SceneObjects: 66", scene.toString());
    }

    @Test
    public void testGetVisibleSceneObjects() throws Exception {
        ScenePoint observerBase = new ScenePoint(500, 1400);
        int observerRotation = 45;
        int viewAngle = 60;
        int viewDistance = 500;
        ArrayList<SceneObject> visibleSceneObjects = scene.getVisibleSceneObjects( observerBase, observerRotation, viewAngle, viewDistance );
        Assert.assertEquals(2, visibleSceneObjects.size());
    }

    @Test
    public void addCarToScene() throws Exception {
        ScenePoint carPosition = new ScenePoint(500, 500);
        Car car = new Car(carPosition, 90);
        scene.addDummyCarToScene(car);
        Assert.assertTrue(scene.getSceneObjectByPosition(carPosition).getClass() == Car.class);
    }

}