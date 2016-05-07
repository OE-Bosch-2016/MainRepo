package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for Simple roads
 */
public class SimpleRoadTest {

    private static SimpleRoad simpleRoad;
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        simpleRoad = new SimpleRoad(new ScenePoint(2175, 2263), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT);
        /*
        Dimensions of avarage parking spot: 2,5*5 meters (300 pixels in the picture)
        300px/5m=60px/m
        */
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
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT, simpleRoad.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(simpleRoad instanceof Road);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(2175, simpleRoad.getBasePosition().getX());
        Assert.assertEquals(2263, simpleRoad.getBasePosition().getY());
        Assert.assertEquals(90, simpleRoad.getRotation());
    }

    @Test
    public void testTopAndBottomPoints() throws Exception {
        Assert.assertEquals(2175, simpleRoad.getTopPoint().getX());
        Assert.assertEquals(2088, simpleRoad.getTopPoint().getY());

        Assert.assertEquals(2525, simpleRoad.getBottomPoint().getX());
        Assert.assertEquals(2088, simpleRoad.getBottomPoint().getY());
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        /*
        top: 2175, 2088
        bottom: 2525, 2088
         */
        ScenePoint observerBase = new ScenePoint(2112, 2152);
        ScenePoint observerBase2 = new ScenePoint(2048, 2000);
        ScenePoint observerBase3 = new ScenePoint(2048, 2088);
        ScenePoint observerBase4 = new ScenePoint(1874, 2088);
        int observerRotation = 0; //0° is horizontal, direction is right, but anticlockwise (coz we view from above)
        int observerRotation2 = 180;

        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase, observerRotation, ultraViewAngle, ultraDistance) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase3, observerRotation, ultraViewAngle, ultraDistance) );
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase4, observerRotation, ultraViewAngle, ultraDistance) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase, observerRotation, radarViewAngle, radarDistance) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase, observerRotation, cameraViewWideAngle, cameraDistance) );
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase2, observerRotation2, ultraViewAngle, ultraDistance) );
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase2, observerRotation2, radarViewAngle, radarDistance) );
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase2, observerRotation2, cameraViewWideAngle, cameraDistance) );

        ScenePoint observerBase5 = new ScenePoint(2200, 2263);
        observerRotation = 110;

        //too little angle
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 23, 10000) );
        //too little distance
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 24, 176) );
        //it fits well (angle & distance)
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 24, 177) );

        //view is near 90°
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 89, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 90, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 91, 10000) );
        //view is near 180°
        Assert.assertTrue(simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 179, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 180, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 181, 10000) );
        //view is near 270°
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 269, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 270, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 271, 10000) );
        //view is near 360°
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 359, 10000) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 360, 10000) );
        Assert.assertFalse( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, 1, 10000) );

        //with real sensors
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, cameraViewWideAngle, cameraDistance) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, radarViewAngle, radarDistance) );
        Assert.assertTrue( simpleRoad.isVisibleFromObserver(observerBase5, observerRotation, ultraViewAngle, ultraDistance) );
    }

    @Test
    public void testReadWriteSerializedObject() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(baos);
        out.writeObject(simpleRoad);
        //
        byte[] byteDataBuffer = baos.toByteArray();
        //
        ByteArrayInputStream bais = new ByteArrayInputStream(byteDataBuffer);
        ObjectInput in = new ObjectInputStream(bais);
        Object clonedSimpleRoad = in.readObject();
        //
        Assert.assertEquals(simpleRoad.toString(), clonedSimpleRoad.toString());
    }

    @Test
    public void testIsPointOnRoad() throws Exception {
        Assert.assertTrue(simpleRoad.isPointOnTheRoad(new ScenePoint(2300, 2000)));
        Assert.assertTrue(simpleRoad.isPointOnTheRoad(new ScenePoint(simpleRoad.getBasePosition().getX(), simpleRoad.getBasePosition().getY())));
        Assert.assertTrue(simpleRoad.isPointOnTheRoad(new ScenePoint(simpleRoad.getBottomPoint().getX(), simpleRoad.getBottomPoint().getY())));
        Assert.assertFalse(simpleRoad.isPointOnTheRoad(new ScenePoint(2500, 2300)));
    }
}