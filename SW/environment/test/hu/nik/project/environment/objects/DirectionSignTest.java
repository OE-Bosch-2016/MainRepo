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
        directionSign = new DirectionSign(new ScenePoint(222,222), 85, DirectionSign.DirectionType.FORWARD);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(DirectionSign.DirectionType.FORWARD, directionSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(222, directionSign.getBasePosition().getX());
        Assert.assertEquals(222, directionSign.getBasePosition().getY());
        Assert.assertEquals(85, directionSign.getRotation(), 0.00001);
    }
}