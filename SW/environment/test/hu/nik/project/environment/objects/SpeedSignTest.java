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
        // Object 57
        speedSign = new SpeedSign(new ScenePoint(235, 3908), 270, SpeedSign.SpeedSignType.LIMIT_50);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(SpeedSign.SpeedSignType.LIMIT_50, speedSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(235, speedSign.getBasePosition().getX());
        Assert.assertEquals(3908, speedSign.getBasePosition().getY());
        Assert.assertEquals(270, speedSign.getRotation(), 0.00001);
    }

    @Test
    public void testGetCenter() throws Exception {
        Assert.assertEquals(195, speedSign.getCenter().getX());
        Assert.assertEquals(3948, speedSign.getCenter().getY());
    }
}