package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.04.12..
 *
 * Test class for SceneObject
 */
public class CarWithConcreteClassTest {

    private static Car car;

    @BeforeClass
    public static void setUp() throws Exception {
        car = new Car(new ScenePoint(500, 500), 90);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(Car.CarType.CAR, car.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(car instanceof SceneObject);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(500, car.getBasePosition().getX());
        Assert.assertEquals(500, car.getBasePosition().getY());
        Assert.assertEquals(90, car.getRotation());
    }

    @Test
    public void testGetPoints() throws Exception {
        Assert.assertEquals(500, car.getTopPoint().getX());
        Assert.assertEquals(450, car.getTopPoint().getY());
        Assert.assertEquals(740, car.getBottomPoint().getX());
        Assert.assertEquals(450, car.getBottomPoint().getY());
        Assert.assertEquals(620, car.getLeftPoint().getX());
        Assert.assertEquals(500, car.getLeftPoint().getY());
        Assert.assertEquals(620, car.getRightPoint().getX());
        Assert.assertEquals(400, car.getRightPoint().getY());
        Assert.assertEquals(620, car.getCenterPoint().getX());
        Assert.assertEquals(450, car.getCenterPoint().getY());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("ClassType: Car ->  Position X: 500 Position Y: 500 Rotation: 90 CarType: CAR", car.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testToStringNullPointerTest() throws Exception{
        //outside of enviroment package i had to surround the newly created car obj with try catch
        try{
            //Exception thrown in super class, debug- step into and
            //u will see the exception there.
            Car newCar = new Car(new ScenePoint(10,10),20);
            newCar.toString();
        }
        catch (SceneObjectException ex){
            ex.printStackTrace();
        }
    }
}