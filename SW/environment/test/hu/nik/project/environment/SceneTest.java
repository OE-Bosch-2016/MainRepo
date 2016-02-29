package hu.nik.project.environment;

import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.environment.objects.SimpleRoad;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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
        SceneObject testObject = scene.getSceneObjectByPosition(2175, 2375);
        Assert.assertEquals(testObject.getClass(), SimpleRoad.class);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("Scene Width: 5000 Height: 4000 Number of SceneObjects: 67", scene.toString());
    }
}