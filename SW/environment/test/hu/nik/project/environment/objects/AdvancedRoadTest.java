package hu.nik.project.environment.objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for advanced road
 */
public class AdvancedRoadTest {

    private static AdvancedRoad advancedRoad;

    @BeforeClass
    public static void setUp() throws Exception {
        advancedRoad = new AdvancedRoad(200, 99, 45, AdvancedRoad.AdvancedRoadType.ROTARY);
    }

    @Test
    public void testGetObjectType() {
        Assert.assertEquals(AdvancedRoad.AdvancedRoadType.ROTARY, advancedRoad.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(200, advancedRoad.getPositionX());
        Assert.assertEquals(99, advancedRoad.getPositionY());
        Assert.assertEquals(45, advancedRoad.getRotation(), 0.00001);
    }
}