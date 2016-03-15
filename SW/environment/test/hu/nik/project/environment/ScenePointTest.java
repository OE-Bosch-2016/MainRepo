package hu.nik.project.environment;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by Róbert on 2016.03.15..
 *
 * Test class for ScenePoint
 */
public class ScenePointTest {

    private static ScenePoint scenePoint;

    @BeforeClass
    public static void setUp() throws Exception {
        scenePoint = new ScenePoint(100, 100);
    }

    @Test
    public void testGetX() throws Exception {
        Assert.assertEquals(100, scenePoint.getX());
    }

    @Test
    public void testGetY() throws Exception {
        Assert.assertEquals(100, scenePoint.getY());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("X: 100 Y: 100", scenePoint.toString());
    }

    @Test
    public void testRotatePointAroundPoint() throws Exception {
        ScenePoint rotated = ScenePoint.rotatePointAroundPoint(scenePoint, new ScenePoint(100, 150), 180);
        Assert.assertEquals(100, rotated.getX());
        Assert.assertEquals(50, rotated.getY());

        rotated = ScenePoint.rotatePointAroundPoint(scenePoint, new ScenePoint(100, 150), 360);
        Assert.assertEquals(100, rotated.getX());
        Assert.assertEquals(150, rotated.getY());

        rotated = ScenePoint.rotatePointAroundPoint(scenePoint, new ScenePoint(100, 150), 0);
        Assert.assertEquals(100, rotated.getX());
        Assert.assertEquals(150, rotated.getY());
    }

    @Test
    public void testGetPointByRotationAndRadius() throws Exception {
        ScenePoint point = ScenePoint.getPointByRotationAndRadius(scenePoint, 0, 20);
        Assert.assertEquals(120, point.getX());
        Assert.assertEquals(100, point.getY());

        point = ScenePoint.getPointByRotationAndRadius(scenePoint, 90, 20);
        Assert.assertEquals(100, point.getX());
        Assert.assertEquals(80, point.getY());

        point = ScenePoint.getPointByRotationAndRadius(scenePoint, 180, 20);
        Assert.assertEquals(80, point.getX());
        Assert.assertEquals(100, point.getY());

        point = ScenePoint.getPointByRotationAndRadius(scenePoint, 360, 20);
        Assert.assertEquals(120, point.getX());
        Assert.assertEquals(100, point.getY());

        point = ScenePoint.getPointByRotationAndRadius(scenePoint, 45, 20);
        Assert.assertEquals(114, point.getX());
        Assert.assertEquals(86, point.getY());
    }
}