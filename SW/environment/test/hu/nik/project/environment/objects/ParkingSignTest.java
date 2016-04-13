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
        parkingSign = new ParkingSign(new ScenePoint(4546, 1906), 90, ParkingSign.ParkingSignType.PARKING_RIGHT);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(ParkingSign.ParkingSignType.PARKING_RIGHT, parkingSign.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(parkingSign instanceof Sign);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(4546, parkingSign.getBasePosition().getX());
        Assert.assertEquals(1906, parkingSign.getBasePosition().getY());
        Assert.assertEquals(90, parkingSign.getRotation());
    }

    @Test
    public void testGetCenter() throws Exception {
        Assert.assertEquals(4586, parkingSign.getCenter().getX());
        Assert.assertEquals(1866, parkingSign.getCenter().getY());
    }
}