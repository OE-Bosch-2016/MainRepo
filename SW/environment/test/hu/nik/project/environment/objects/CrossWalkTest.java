package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for CrossWalk
 */
public class CrossWalkTest {

    private static CrossWalk crossWalk;

    @BeforeClass
    public static void setUp() throws Exception {
        crossWalk = new CrossWalk(new ScenePoint(1550, 498), 90, CrossWalk.CrossWalkType.CROSSWALK_5);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(CrossWalk.CrossWalkType.CROSSWALK_5, crossWalk.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(crossWalk instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(1550, crossWalk.getBasePosition().getX());
        Assert.assertEquals(498, crossWalk.getBasePosition().getY());
        Assert.assertEquals(90, crossWalk.getRotation(), 0.00001);
    }

    @Test
    public void testTopAndBottomPoints() throws Exception {
        Assert.assertEquals(1550, crossWalk.getTopPoint().getX());
        Assert.assertEquals(323, crossWalk.getTopPoint().getY());

        Assert.assertEquals(1740, crossWalk.getBottomPoint().getX());
        Assert.assertEquals(323, crossWalk.getBottomPoint().getY());
    }
}