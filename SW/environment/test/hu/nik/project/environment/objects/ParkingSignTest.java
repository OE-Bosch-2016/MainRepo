package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
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
        parkingSign = new ParkingSign(new ScenePoint(111, 111), 40, ParkingSign.ParkingSignType.PARKING_RIGHT);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(ParkingSign.ParkingSignType.PARKING_RIGHT, parkingSign.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(111, parkingSign.getBasePosition().getX());
        Assert.assertEquals(111, parkingSign.getBasePosition().getY());
        Assert.assertEquals(40, parkingSign.getRotation(), 0.00001);
    }
}