package hu.nik.project.environment.objects;

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
        directionSign = new DirectionSign(222,222, 85.55, DirectionSign.DirectionType.FORWARD);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(DirectionSign.DirectionType.FORWARD, directionSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(222, directionSign.getPositionX());
        Assert.assertEquals(222, directionSign.getPositionY());
        Assert.assertEquals(85.55, directionSign.getRotation(), 0.00001);
    }
}