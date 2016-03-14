package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for priority sign
 */
public class PrioritySignTest {

    private static PrioritySign prioritySign;

    @BeforeClass
    public static void setUp() throws Exception {
        prioritySign = new PrioritySign(new ScenePoint(300, 300), 0, PrioritySign.PrioritySignType.STOP);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(PrioritySign.PrioritySignType.STOP, prioritySign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(300, prioritySign.getBasePosition().getX());
        Assert.assertEquals(300, prioritySign.getBasePosition().getY());
        Assert.assertEquals(0, prioritySign.getRotation(), 0.00001);
    }
}