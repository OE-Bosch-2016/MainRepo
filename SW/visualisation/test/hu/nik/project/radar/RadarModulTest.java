package hu.nik.project.radar;

import hu.nik.project.communication.CommBus;
import hu.nik.project.environment.ISensorScene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.utils.Vector2D;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.easymock.EasyMock.*;

/**
 * Created by secured on 2016. 03. 20..
 */
public class RadarModulTest extends TestCase {

    private ISensorScene _sensorScene;
    private RadarModul _radarModul;
    private int _samplingTime = 2;
    private int _angle = 30;

    private ArrayList<Vector2D> inputPositions;
    private CommBus combus;
    private ScenePoint currentPos;

    @Override
    public void setUp() {

    }


    @Test
    public void testRadarForSingeCycle() throws Exception {

        combus = new CommBus();
        currentPos = new ScenePoint(4, 4);
         _sensorScene =  Mockito.mock(ISensorScene.class);
        _radarModul = new RadarModul(_sensorScene,combus,_angle,_samplingTime,currentPos);

        Car carOne = new Car(new ScenePoint(10,10),10);
        Car carTwo = new Car(new ScenePoint(30,30),20);
        ArrayList<SceneObject> cars = new ArrayList<SceneObject>();
        cars.add(carOne);
        cars.add(carTwo);

        Mockito.doReturn(cars).when(_sensorScene).getVisibleSceneObjects(anyObject(ScenePoint.class), anyInt(), anyInt());

        _radarModul.setIsRadarEnabled(true);
        RadarMessagePacket result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(10, 15);

        assertNull(result);
    }

    @Test
    public void testRadarForMultipleCycles() throws Exception {
        ArrayList<Vector2D> input = new ArrayList<Vector2D>();
        Vector2D first = new Vector2D(3, 3);
        Vector2D second = new Vector2D(4, 4);
        input.add(first);
        input.add(second);


        RadarMessagePacket result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(20,20);

        //2 seconds later....
        input = new ArrayList<Vector2D>();
        first.set_coordinateX(30);
        first.set_coordinateY(30);

        second.set_coordinateX(40);
        second.set_coordinateY(40);
        input.add(first);
        input.add(second);


        result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(30,20);


        //assertEquals(expectedSize, actualSize);

    }

    @Test
    public void testRadarForMultipleCyclesAndDeletion() throws Exception {
        ArrayList<Vector2D> input = new ArrayList<Vector2D>();
        Vector2D first = new Vector2D(3, 3);
        Vector2D second = new Vector2D(4, 4);
        input.add(first);
        input.add(second);


        RadarMessagePacket result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(20,30);


        input = new ArrayList<Vector2D>();
        first.set_coordinateX(30);
        first.set_coordinateY(30);

        second.set_coordinateX(40);
        second.set_coordinateY(40);
        input.add(first);
        input.add(second);
        input.add(new Vector2D(6, 6));
        input.add(new Vector2D(7, 7));


        //result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(input);
/*
        int expectedSize = 4;
        int actualSize = result.size();
        for (RadarMessagePacket item : result) {
            assertNotNull(item.getCurrentPosition());
            assertTrue(4 < item.getCurrentPosition().get_coordinateX());
            assertTrue(4 < item.getCurrentPosition().get_coordinateY());
        }
        assertEquals(expectedSize, actualSize);
*/
        //2 seconds later

        input = new ArrayList<Vector2D>();
        first.set_coordinateX(3);
        first.set_coordinateY(5);

        input.add(first);

       // result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(input);
/*
        expectedSize = 1;
        actualSize = result.size();

        assertEquals(expectedSize, actualSize);
        for (RadarMessagePacket item : result) {
            assertNotNull(item.getCurrentPosition());
            assertEquals(3f, item.getCurrentPosition().get_coordinateX());
            assertEquals(5f, item.getCurrentPosition().get_coordinateY());
            assertTrue(0 != item.getCurrentDistance());
            assertTrue(0 != item.getRelativeSpeed());
        }
*/
    }

    @Test
    public void testGetMostRecentVectorsDeletingItems() throws Exception {
        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(2, 2));
        inputPositions.add(new Vector2D(3, 3));
        inputPositions.add(new Vector2D(4, 3));
        inputPositions.add(new Vector2D(5, 3));
        inputPositions.add(new Vector2D(6, 3));
        RadarMessagePacket test = _radarModul.getDetectedObjsRelativeSpeedAndDistance(30,30);

        int expectedCount = 5;
        //int actual = test.size();
        //assertEquals(expectedCount, actual);

        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(11, 11));
        inputPositions.add(new Vector2D(10, 10));

        //test = _radarModul.getDetectedObjsRelativeSpeedAndDistance(inputPositions);
        expectedCount = 2;
       // actual = test.size();

        //assertEquals(expectedCount, actual);

    }

    //<editor-fold desc="Private method unit tests; can be deleted later">
    /*
    @Test
    public void testSpeedAndDistanceListWithSingleIteration() throws Exception{
        inputPositions=new ArrayList<Vector2D>();
        Vector2D first = new Vector2D(2,2);
        Vector2D second =new Vector2D(4,4);
        inputPositions.add(first);
        inputPositions.add(second);

        ArrayList<Vector2D> inputs = _radarModul.getMostRecentVectorsFromDataBus(inputPositions);
        ArrayList<RadarPacket> speedAndDistanceObjArrayList = _radarModul.getDetectedObjsRelativeSpeedDistance(inputs,new Vector2D(7,7),10);

        int expectedSize=2;
        int actualSize=speedAndDistanceObjArrayList.size();

        double expectedRelativeSpeed=0;
        double actualRealtiveSpeed=speedAndDistanceObjArrayList.get(0).getRelativeSpeed();

        assertNotNull(speedAndDistanceObjArrayList);
        assertEquals(expectedSize,actualSize);
        assertEquals(expectedRelativeSpeed,actualRealtiveSpeed);

    }

    @Test
    public void testSpeedAndDistanceListWithTwoIteration() throws Exception{
        inputPositions=new ArrayList<Vector2D>();
        Vector2D first = new Vector2D(1,1);
        Vector2D second =new Vector2D(2,2);
        inputPositions.add(first);
        inputPositions.add(second);

        ArrayList<Vector2D> recent= _radarModul.getMostRecentVectorsFromDataBus(inputPositions);
        ArrayList<RadarPacket> speedAndDistanceObjArrayList = _radarModul.getDetectedObjsRelativeSpeedDistance(recent,new Vector2D(7,7),10);

        //2. iteration, like 2 seconds later

        first.set_coordinateX(11);
        first.set_coordinateY(11);
        second.set_coordinateX(22);
        second.set_coordinateY(22);
        Vector2D third = new Vector2D(5,5);

        ArrayList<Vector2D> asd = new ArrayList<Vector2D>();
        asd.add(first);
        asd.add(second);
        asd.add(third);

        ArrayList<Vector2D> recent2 = _radarModul.getMostRecentVectorsFromDataBus(asd);
        speedAndDistanceObjArrayList= _radarModul.getDetectedObjsRelativeSpeedDistance(recent2, new Vector2D(4, 4), 11);

        int expectedSize=3;
        int actualSize=speedAndDistanceObjArrayList.size();

        double expectedRelativeSpeed=0;
        double actualRealtiveSpeed=speedAndDistanceObjArrayList.get(0).getRelativeSpeed();

        int expectedXValue=22;
        int actualXValue=speedAndDistanceObjArrayList.get(1).getCurrentPosition().get_coordinateX();

        assertNotNull(speedAndDistanceObjArrayList);
        assertEquals(expectedSize, actualSize);
        assertTrue(expectedRelativeSpeed < actualRealtiveSpeed);
        assertEquals(expectedXValue, actualXValue);

        ArrayList<Vector2D> asd2 = new ArrayList<Vector2D>();
        first.set_coordinateX(100);
        first.set_coordinateY(100);
        asd2.add(first);

        recent2 = _radarModul.getMostRecentVectorsFromDataBus(asd2);
        speedAndDistanceObjArrayList=_radarModul.getDetectedObjsRelativeSpeedDistance(recent2, new Vector2D(5, 5), 21);

        expectedSize=1;
        actualSize=speedAndDistanceObjArrayList.size();

        assertNotNull(speedAndDistanceObjArrayList);
        assertEquals(expectedSize,actualSize);
        assertTrue(speedAndDistanceObjArrayList.get(0).getRelativeSpeed()>0);


    }


    @Test
    public void testGetMostRecentVectorsFromDataBus() throws Exception {
        inputPositions = new ArrayList<Vector2D>();
        Vector2D first = new Vector2D(2,2);
        Vector2D second =new Vector2D(4,4);
        inputPositions.add(first);
        inputPositions.add(second);

        //<editor-fold desc="First call, when previous list is empty">
        ArrayList<Vector2D> recents = _radarModul.getMostRecentVectorsFromDataBus(inputPositions);

        int expectedCount=2;
        int actualCount=recents.size();

        assertEquals(expectedCount,actualCount);
        assertNotNull(recents);

        //</editor-fold >

        //<editor-fold desc="Second call, when previous list is NOT empty, modification, new item added">
        inputPositions= new ArrayList<Vector2D>();
        first.set_coordinateX(10);
        first.set_coordinateY(10);
        inputPositions.add(first);
        inputPositions.add(second);
        inputPositions.add(new Vector2D(3,3));
        inputPositions.add(new Vector2D(4,3));
        inputPositions.add(new Vector2D(5,3));


        recents = _radarModul.getMostRecentVectorsFromDataBus(inputPositions);
        actualCount=recents.size();

        int expectedX=10;
        int actualX= recents.get(0).get_coordinateX();
        assertEquals(expectedX,actualX);
        assertEquals(5,actualCount);

        // </editor-fold>

        //<editor-fold desc="Third call, when previous list is NOT empty, removing is needed, modification">

        ArrayList<Vector2D> asd=new ArrayList<Vector2D>();
        first.set_coordinateX(11);
        first.set_coordinateY(11);
        asd.add(first);
        asd.add(second);

        recents = _radarModul.getMostRecentVectorsFromDataBus(asd);
        actualCount=recents.size();

        expectedX=11;
        actualX = recents.get(0).get_coordinateX();
        assertEquals(expectedX,actualX);
        assertEquals(2,actualCount);

        //</editor-fold>
    }

    @Test
    public void testGetMostRecentVectorsDeletingItems() throws Exception{
        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(2,2));
        inputPositions.add(new Vector2D(3,3));
        inputPositions.add(new Vector2D(4,3));
        inputPositions.add(new Vector2D(5,3));
        inputPositions.add(new Vector2D(6, 3));
        ArrayList<Vector2D>test = _radarModul.getMostRecentVectorsFromDataBus(inputPositions);

        int expectedCount= 5;
        int actual =test.size();
        assertEquals(expectedCount,actual);

        inputPositions= new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(11,11));
        inputPositions.add(new Vector2D(10,10));

        test = _radarModul.getMostRecentVectorsFromDataBus(inputPositions);
        expectedCount=2;
        actual=test.size();

        assertEquals(expectedCount,actual);

    }
    */
    //</editor-fold>
}