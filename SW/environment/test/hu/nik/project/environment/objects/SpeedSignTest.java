package hu.nik.project.environment.objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for speed signs
 */
public class SpeedSignTest {

    private static SpeedSign speedSign;

    @BeforeClass
    public static void setUp() throws Exception {
        speedSign = new SpeedSign(700, 700, 270, SpeedSign.SpeedSignType.LIMIT_100);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(SpeedSign.SpeedSignType.LIMIT_100, speedSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(700, speedSign.getPositionX());
        Assert.assertEquals(700, speedSign.getPositionY());
        Assert.assertEquals(270, speedSign.getRotation(), 0.00001);
    }
}