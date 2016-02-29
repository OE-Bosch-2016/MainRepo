package hu.nik.project.environment.objects;

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

    @BeforeClass
    public static void setUp() throws Exception {
        parking = new Parking(300, 300, 0, Parking.ParkingType.PARKING_0);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(Parking.ParkingType.PARKING_0, parking.getObjectType());
        Assert.assertNotEquals(Parking.ParkingType.PARKING_90, parking.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(300, parking.getPositionX());
        Assert.assertEquals(300, parking.getPositionY());
        Assert.assertEquals(0, parking.getRotation(), 0.00001);
    }
}