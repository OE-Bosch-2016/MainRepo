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
    private static Car modifyPositionAndRoatation;

    @BeforeClass
    public static void setUp() throws Exception {
        car = new Car(new ScenePoint(500, 500), 90);
        modifyPositionAndRoatation = new Car(new ScenePoint(600, 600), 85);
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

    @Test
    public void testModifyBasePositionAndRotation() {
        modifyPositionAndRoatation.setBasePositonAndRotation(new ScenePoint(1000, 1000), 90);
        Assert.assertEquals(1000, modifyPositionAndRoatation.getBasePosition().getX());
        Assert.assertEquals(1000, modifyPositionAndRoatation.getBasePosition().getY());
        Assert.assertEquals(90, modifyPositionAndRoatation.getRotation());
    }
}