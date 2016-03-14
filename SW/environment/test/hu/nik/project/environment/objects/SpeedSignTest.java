package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
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
        speedSign = new SpeedSign(new ScenePoint(700, 700), 270, SpeedSign.SpeedSignType.LIMIT_100);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(SpeedSign.SpeedSignType.LIMIT_100, speedSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(700, speedSign.getBasePosition().getX());
        Assert.assertEquals(700, speedSign.getBasePosition().getY());
        Assert.assertEquals(270, speedSign.getRotation(), 0.00001);
    }
}