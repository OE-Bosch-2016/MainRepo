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

    private static Parking parking0;
    private static Parking parking90;
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        parking0 = new Parking(new ScenePoint(3404, 771), 0, Parking.ParkingType.PARKING_0);
        parking90 = new Parking(new ScenePoint(4060, 1918), 90, Parking.ParkingType.PARKING_90);
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
        Assert.assertEquals(Parking.ParkingType.PARKING_0, parking0.getObjectType());
        Assert.assertNotEquals(Parking.ParkingType.PARKING_90, parking0.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(parking0 instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(3404, parking0.getBasePosition().getX());
        Assert.assertEquals(771, parking0.getBasePosition().getY());
        Assert.assertEquals(0, parking0.getRotation());
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        ScenePoint observerBase = new ScenePoint(800, 800);
        int observerRotation = 135;
        Assert.assertTrue( parking0.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
    }

    @Test
    public void testIsPointOnRoad() throws Exception {
        Assert.assertTrue(parking0.isPointOnTheRoad(new ScenePoint(3450, 1080)));
        Assert.assertTrue(parking0.isPointOnTheRoad(new ScenePoint(parking0.getBasePosition().getX(), parking0.getBasePosition().getY())));
        Assert.assertTrue(parking0.isPointOnTheRoad(new ScenePoint(parking0.getBottomPoint().getX(), parking0.getBottomPoint().getY())));
        Assert.assertFalse(parking0.isPointOnTheRoad(new ScenePoint(2500, 2300)));

        Assert.assertTrue(parking90.isPointOnTheRoad(new ScenePoint(4400, 1800)));
        Assert.assertTrue(parking90.isPointOnTheRoad(new ScenePoint(parking90.getBasePosition().getX(), parking90.getBasePosition().getY())));
        Assert.assertTrue(parking90.isPointOnTheRoad(new ScenePoint(parking90.getBottomPoint().getX(), parking90.getBottomPoint().getY())));
        Assert.assertFalse(parking90.isPointOnTheRoad(new ScenePoint(2500, 2300)));
    }
}