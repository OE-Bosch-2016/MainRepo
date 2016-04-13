package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
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
        advancedRoad = new AdvancedRoad(new ScenePoint(775, 1913), 90, AdvancedRoad.AdvancedRoadType.JUNCTIONLEFT);
    }

    @Test
    public void testGetObjectType() {
        Assert.assertEquals(AdvancedRoad.AdvancedRoadType.JUNCTIONLEFT, advancedRoad.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(advancedRoad instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(775, advancedRoad.getBasePosition().getX());
        Assert.assertEquals(1913, advancedRoad.getBasePosition().getY());
        Assert.assertEquals(90, advancedRoad.getRotation());
    }

    @Test
    public void testGetTrackWidth() throws Exception {
        Assert.assertEquals(175, Road.getTrackWidth());
    }

    @Test
    public void testTopAndBottomPoints() throws Exception {
        Assert.assertEquals(775, advancedRoad.getTopPoint().getX());
        Assert.assertEquals(2088, advancedRoad.getTopPoint().getY());

        Assert.assertEquals(2175, advancedRoad.getBottomPoint().getX());
        Assert.assertEquals(2088, advancedRoad.getBottomPoint().getY());

        Assert.assertEquals(1475, advancedRoad.getLeftPoint().getX());
        Assert.assertEquals(2788, advancedRoad.getLeftPoint().getY());

        Assert.assertNull(advancedRoad.getRightPoint());
    }
}