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
    }
}