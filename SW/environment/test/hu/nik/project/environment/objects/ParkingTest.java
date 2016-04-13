package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for Parking
 */
public class ParkingTest {

    private static Parking parking;
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        parking = new Parking(new ScenePoint(300, 300), 0, Parking.ParkingType.PARKING_0);
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
        Assert.assertEquals(Parking.ParkingType.PARKING_0, parking.getObjectType());
        Assert.assertNotEquals(Parking.ParkingType.PARKING_90, parking.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(parking instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(300, parking.getBasePosition().getX());
        Assert.assertEquals(300, parking.getBasePosition().getY());
        Assert.assertEquals(0, parking.getRotation());
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        ScenePoint observerBase = new ScenePoint(800, 800);
        int observerRotation = 135;
        Assert.assertTrue( parking.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
    }
}