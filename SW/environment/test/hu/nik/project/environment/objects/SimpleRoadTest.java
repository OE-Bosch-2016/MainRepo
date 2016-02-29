package hu.nik.project.environment.objects;

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
        simpleRoad = new SimpleRoad(0, 0, 0, SimpleRoad.SimpleRoadType.SIMPLE_65_LEFT);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_65_LEFT, simpleRoad.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(0, simpleRoad.getPositionX());
        Assert.assertEquals(0, simpleRoad.getPositionY());
        Assert.assertEquals(0, simpleRoad.getRotation(), 0.00001);
    }
}