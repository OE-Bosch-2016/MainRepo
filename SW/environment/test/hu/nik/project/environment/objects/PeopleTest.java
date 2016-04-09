package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for people
 */
public class PeopleTest {

    private static People people;
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        people = new People(new ScenePoint(1608, 308), 0, People.PeopleType.MAN);
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
        Assert.assertEquals(People.PeopleType.MAN, people.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(people instanceof Misc);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(1608, people.getBasePosition().getX());
        Assert.assertEquals(308, people.getBasePosition().getY());
        Assert.assertEquals(0, people.getRotation(), 0.00001);
    }

    @Test
    public void testGetWidthAndHeight() throws Exception{
        Assert.assertEquals(80, Misc.getMiscWidthAndHeight());
    }

    @Test
    public void testCenter() throws Exception{
        Assert.assertEquals(1648, people.getCenter().getX());
        Assert.assertEquals(348, people.getCenter().getY());
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {

        //the observer's point equals the target's point
        //1608; from 308 to 347 error
        ScenePoint observerBase = new ScenePoint(1608, 308);
        int observerRotation = 10;
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );

        observerBase = new ScenePoint(1608, 348);
        observerRotation = 10;
        //too little angle
        Assert.assertFalse( people.isVisibleFromObserver(observerBase, observerRotation, 19, 10000) );
        //too little distance
        Assert.assertFalse( people.isVisibleFromObserver(observerBase, observerRotation, 20, 39) );
        //it fits well (angle & distance)
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 20, 40) );

        //view is near 90°
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 89, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 90, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 91, 10000) );
        //view is near 180°
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 179, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 180, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 181, 10000) );
        //view is near 270°
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 269, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 270, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 271, 10000) );
        //view is near 360°
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, 360, 10000) );
        Assert.assertFalse( people.isVisibleFromObserver(observerBase, observerRotation, 1, 10000) );

        //with real sensors
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, cameraViewWideAngle, cameraDistance) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, radarViewAngle, radarDistance) );
        Assert.assertTrue( people.isVisibleFromObserver(observerBase, observerRotation, ultraViewAngle, ultraDistance) );
    }
}