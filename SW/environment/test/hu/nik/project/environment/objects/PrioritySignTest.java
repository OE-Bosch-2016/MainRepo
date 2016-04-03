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
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        prioritySign = new PrioritySign(new ScenePoint(300, 300), 0, PrioritySign.PrioritySignType.STOP);
        ultraDistance=300; //5m
        ultraViewAngle=60;
        radarDistance=12000; //200m
        radarViewAngle=180;
        cameraDistance=100000; //infinite
        cameraViewAngle=75;
        cameraViewWideAngle=170;
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(PrioritySign.PrioritySignType.STOP, prioritySign.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(prioritySign instanceof Sign);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(300, prioritySign.getBasePosition().getX());
        Assert.assertEquals(300, prioritySign.getBasePosition().getY());
        Assert.assertEquals(0, prioritySign.getRotation(), 0.00001);
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        ScenePoint observerBase = new ScenePoint(300, 700);
        int observerRotation = 90;

        //too little angle
        Assert.assertFalse( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 12, 10000) );
        //too little distance
        Assert.assertFalse( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 13, 362) );
        //it fits well (angle & distance)
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 13, 363) );

        //view is near 90°
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 89, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 90, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 91, 10000) );
        //view is near 180°
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 179, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 180, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 181, 10000) );
        //view is near 270°
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 269, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 270, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 271, 10000) );
        //view is near 360°
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 360, 10000) );
        Assert.assertFalse( prioritySign.isVisibleFromObserver(observerBase, observerRotation, 1, 10000) );

        //with real sensors
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, cameraViewWideAngle, cameraDistance) );
        Assert.assertTrue( prioritySign.isVisibleFromObserver(observerBase, observerRotation, radarViewAngle, radarDistance) );
        Assert.assertFalse( prioritySign.isVisibleFromObserver(observerBase, observerRotation, ultraViewAngle, ultraDistance) );
    }
}