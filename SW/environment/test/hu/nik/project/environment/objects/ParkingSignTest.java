package hu.nik.project.environment.objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for ParkingSign
 */
public class ParkingSignTest {

    private static ParkingSign parkingSign;

    @BeforeClass
    public static void setUp() throws Exception {
        parkingSign = new ParkingSign(111, 111, 40, ParkingSign.ParkingSignType.PARKING_RIGHT);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(ParkingSign.ParkingSignType.PARKING_RIGHT, parkingSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(111, parkingSign.getPositionX());
        Assert.assertEquals(111, parkingSign.getPositionY());
        Assert.assertEquals(40, parkingSign.getRotation(), 0.00001);
    }
}