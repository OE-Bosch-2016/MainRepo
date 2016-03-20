package hu.nik.project.environment;

import hu.nik.project.environment.objects.AdvancedRoad;
import hu.nik.project.environment.objects.SceneObject;
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
        ScenePoint observerBase = new ScenePoint(1500, 2300);
        int observerRotation = 45;
        int viewAngle = 90;
        int viewDistance = 200;
        ArrayList<SceneObject> visibleSceneObjects = scene.getVisibleSceneObjects( observerBase, observerRotation, viewAngle, viewDistance );
        Assert.assertEquals(2, visibleSceneObjects.size());
    }
}