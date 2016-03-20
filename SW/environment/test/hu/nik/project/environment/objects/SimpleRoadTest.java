package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for Simple roads
 */
public class SimpleRoadTest {

    private static SimpleRoad simpleRoad;

    @BeforeClass
    public static void setUp() throws Exception {
        simpleRoad = new SimpleRoad(new ScenePoint(2175, 2263), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT, simpleRoad.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(simpleRoad instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(2175, simpleRoad.getBasePosition().getX());
        Assert.assertEquals(2263, simpleRoad.getBasePosition().getY());
        Assert.assertEquals(90, simpleRoad.getRotation(), 0.00001);
    }

    @Test
    public void testTopAndBottomPoints() throws Exception {
        Assert.assertEquals(2175, simpleRoad.getTopPoint().getX());
        Assert.assertEquals(2088, simpleRoad.getTopPoint().getY());

        Assert.assertEquals(2525, simpleRoad.getBottomPoint().getX());
        Assert.assertEquals(2088, simpleRoad.getBottomPoint().getY());
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        ScenePoint observerBase = new ScenePoint(1790, 2030);
        int observerRotation = 45;
        int viewAngle = 90;
        int viewDistance = 200;

        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance) );
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase, observerRotation, viewAngle, viewDistance) );
    }
}