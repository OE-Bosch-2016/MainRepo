package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for DirectionSign
 */
public class DirectionSignTest {

    private static DirectionSign directionSign;

    @BeforeClass
    public static void setUp() throws Exception {
        directionSign = new DirectionSign(new ScenePoint(2785, 2245), 0, DirectionSign.DirectionType.ROUNDABOUT);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(DirectionSign.DirectionType.ROUNDABOUT, directionSign.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(directionSign instanceof Sign);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(2785, directionSign.getBasePosition().getX());
        Assert.assertEquals(2245, directionSign.getBasePosition().getY());
        Assert.assertEquals(0, directionSign.getRotation(), 0.00001);
    }

    @Test
    public void testGetCenter() throws Exception {
        Assert.assertEquals(2825, directionSign.getCenter().getX());
        Assert.assertEquals(2285, directionSign.getCenter().getY());
    }
}