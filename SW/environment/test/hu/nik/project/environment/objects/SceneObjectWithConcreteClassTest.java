package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
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
        sceneObject = new Parking(new ScenePoint(50, 40), 20, Parking.ParkingType.PARKING_90);
    }

    @Test(expected = SceneObjectException.class)
    public void cantBeNagative() throws Exception{
        sceneObject = new Parking(new ScenePoint(-1, 40), 20, Parking.ParkingType.PARKING_90);
        sceneObject = new Parking(new ScenePoint(50, -2), 20, Parking.ParkingType.PARKING_90);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(Parking.ParkingType.PARKING_90, sceneObject.getObjectType());
    }

    @Test
    public void testGetPositionX() throws Exception {
        Assert.assertNotEquals(90, sceneObject.getBasePosition().getX());
        Assert.assertEquals(40, sceneObject.getBasePosition().getY());
    }

    @Test
    public void testGetRotation() throws Exception {
        Assert.assertNotEquals(90, sceneObject.getRotation());
        Assert.assertEquals(20, sceneObject.getRotation(), 0.00001);
    }

    @Test(expected = SceneObjectException.class)
    public void testExeptionThrowWithRotation() throws SceneObjectException {
        sceneObject = new Parking(new ScenePoint(999,999), -500, Parking.ParkingType.PARKING_0);
        sceneObject = new Parking(new ScenePoint(999,999), 500, Parking.ParkingType.PARKING_90);
    }

    @Test
    public void testConcreteType() {
        Assert.assertEquals(Parking.class, sceneObject.getClass());
    }

}