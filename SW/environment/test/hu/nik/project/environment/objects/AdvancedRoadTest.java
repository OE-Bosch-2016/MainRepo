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

    private static AdvancedRoad junctionLeft;
    private static AdvancedRoad junctionRight;
    private static AdvancedRoad rotary;
    private static AdvancedRoad cross;


    @BeforeClass
    public static void setUp() throws Exception {
        // Object 22
        junctionLeft = new AdvancedRoad(new ScenePoint(775, 1913), 90, AdvancedRoad.AdvancedRoadType.JUNCTIONLEFT);
        // Object 19
        rotary = new AdvancedRoad(new ScenePoint(2525, 2263), 0, AdvancedRoad.AdvancedRoadType.ROTARY);
        // From picture
        junctionRight = new AdvancedRoad(new ScenePoint(300, 2000), 0, AdvancedRoad.AdvancedRoadType.JUNCTIONRIGHT);
        cross = new AdvancedRoad(new ScenePoint(2200, 1100), 0, AdvancedRoad.AdvancedRoadType.CROSSROADS);

    }

    @Test
    public void testGetObjectType() {
        Assert.assertEquals(AdvancedRoad.AdvancedRoadType.JUNCTIONLEFT, junctionLeft.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(junctionLeft instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(775, junctionLeft.getBasePosition().getX());
        Assert.assertEquals(1913, junctionLeft.getBasePosition().getY());
        Assert.assertEquals(90, junctionLeft.getRotation());
    }

    @Test
    public void testGetTrackWidth() throws Exception {
        Assert.assertEquals(175, Road.getTrackWidth());
    }

    @Test
    public void testTopAndBottomPoints() throws Exception {
        Assert.assertEquals(775, junctionLeft.getTopPoint().getX());
        Assert.assertEquals(2088, junctionLeft.getTopPoint().getY());

        Assert.assertEquals(2175, junctionLeft.getBottomPoint().getX());
        Assert.assertEquals(2088, junctionLeft.getBottomPoint().getY());

        Assert.assertEquals(1475, junctionLeft.getLeftPoint().getX());
        Assert.assertEquals(2788, junctionLeft.getLeftPoint().getY());

        Assert.assertNull(junctionLeft.getRightPoint());
    }

    @Test
    public void testIsPointOnRoad() throws Exception {
        Assert.assertTrue(rotary.isPointOnTheRoad(new ScenePoint(2800, 2000)));
        Assert.assertTrue(rotary.isPointOnTheRoad(new ScenePoint(3200, 1600)));
        Assert.assertTrue(rotary.isPointOnTheRoad(new ScenePoint(rotary.getBottomPoint().getX(), rotary.getBottomPoint().getY())));
        Assert.assertFalse(rotary.isPointOnTheRoad(new ScenePoint(2500, 2300)));

        Assert.assertTrue(junctionLeft.isPointOnTheRoad(new ScenePoint(1600, 2100)));
        Assert.assertTrue(junctionLeft.isPointOnTheRoad(new ScenePoint(1400, 2500)));
        Assert.assertTrue(junctionLeft.isPointOnTheRoad(new ScenePoint(junctionLeft.getBottomPoint().getX(), junctionLeft.getBottomPoint().getY())));
        Assert.assertFalse(junctionLeft.isPointOnTheRoad(new ScenePoint(2500, 2300)));

        Assert.assertTrue(junctionRight.isPointOnTheRoad(new ScenePoint(500, 2100)));
        Assert.assertTrue(junctionRight.isPointOnTheRoad(new ScenePoint(900, 2600)));
        Assert.assertTrue(junctionRight.isPointOnTheRoad(new ScenePoint(junctionRight.getBottomPoint().getX(), junctionRight.getBottomPoint().getY())));
        Assert.assertFalse(junctionRight.isPointOnTheRoad(new ScenePoint(2500, 2300)));

        Assert.assertTrue(cross.isPointOnTheRoad(new ScenePoint(2400, 800)));
        Assert.assertTrue(cross.isPointOnTheRoad(new ScenePoint(2800, 1300)));
        Assert.assertTrue(cross.isPointOnTheRoad(new ScenePoint(cross.getBottomPoint().getX(), cross.getBottomPoint().getY())));
        Assert.assertFalse(cross.isPointOnTheRoad(new ScenePoint(2500, 2300)));
    }
}