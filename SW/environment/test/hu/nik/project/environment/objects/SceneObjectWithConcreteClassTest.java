package hu.nik.project.environment.objects;

import org.junit.*;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for SceneObject
 */
public class SceneObjectWithConcreteClassTest {

    private static SceneObject sceneObject;

    @BeforeClass
    public static void setUp() throws Exception {
        sceneObject = new Parking(50, 40, 20, Parking.ParkingType.PARKING_90);
    }

    @Test
    public void cantBeNagative() throws Exception{
        sceneObject = new Parking(-1, 40, 20, Parking.ParkingType.PARKING_90);
        Assert.fail("The X or Y position is negative! X: "+sceneObject.getPositionX()+" Y: "+sceneObject.getPositionY());
        sceneObject = new Parking(50, -2, 20, Parking.ParkingType.PARKING_90);
        Assert.fail("The X or Y position is negative! X: "+sceneObject.getPositionX()+" Y: "+sceneObject.getPositionY());
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(Parking.ParkingType.PARKING_90, sceneObject.getObjectType());
    }

    @Test
    public void testGetPositionX() throws Exception {
        Assert.assertNotEquals(90, sceneObject.getPositionX());
        Assert.assertEquals(50, sceneObject.getPositionX());
    }

    @Test
    public void testGetRotation() throws Exception {
        Assert.assertNotEquals(90, sceneObject.getRotation());
        Assert.assertEquals(20, sceneObject.getRotation(), 0.00001);
    }

    @Test(expected = SceneObjectException.class)
    public void testExeptionThrowWithRotation() throws SceneObjectException {
        sceneObject = new Parking(999,999, -500, Parking.ParkingType.PARKING_BOLLARD);
        sceneObject = new Parking(999,999, 500, Parking.ParkingType.PARKING_BOLLARD);
    }

    @Test
    public void testConcreteType() {
        Assert.assertEquals(Parking.class, sceneObject.getClass());
    }
}