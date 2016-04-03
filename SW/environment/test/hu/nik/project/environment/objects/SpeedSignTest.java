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
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        // Object 57
        speedSign = new SpeedSign(new ScenePoint(235, 3908), 270, SpeedSign.SpeedSignType.LIMIT_50);
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
        Assert.assertEquals(SpeedSign.SpeedSignType.LIMIT_50, speedSign.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(speedSign instanceof Sign);
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

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        ScenePoint observerBase = new ScenePoint(352, 3826);
        int observerRotation = 180;

        //too little angle
        Assert.assertFalse( speedSign.isVisibleFromObserver(observerBase, observerRotation, 75, 10000) );
        //too little distance
        Assert.assertFalse( speedSign.isVisibleFromObserver(observerBase, observerRotation, 76, 198) );
        //it fits well (angle & distance)
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 76, 199) );

        //view is near 90°
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 89, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 90, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 91, 10000) );
        //view is near 180°
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 179, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 180, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 181, 10000) );
        //view is near 270°
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 269, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 270, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 271, 10000) );
        //view is near 360°
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, 360, 10000) );
        Assert.assertFalse( speedSign.isVisibleFromObserver(observerBase, observerRotation, 1, 10000) );

        //with real sensors
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, cameraViewWideAngle, cameraDistance) );
        Assert.assertTrue( speedSign.isVisibleFromObserver(observerBase, observerRotation, radarViewAngle, radarDistance) );
        Assert.assertFalse( speedSign.isVisibleFromObserver(observerBase, observerRotation, ultraViewAngle, ultraDistance) );
    }
}