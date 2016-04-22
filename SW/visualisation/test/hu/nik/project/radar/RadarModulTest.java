package hu.nik.project.radar;

import hu.nik.project.communication.CommBus;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.utils.Vector2D;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by secured on 2016. 03. 20..
 */
public class RadarModulTest extends TestCase {

    private RadarModul _radarModul;
    private int _samplingTime = 2;
    private float _angle = 30;

    private ArrayList<Vector2D> inputPositions;
    private CommBus combus;
    private ScenePoint currentPos;
    private SensorSceneDummy sensorSceneDummy;

    @Before
    public void setUp() {
        currentPos = new ScenePoint(10, 10);
        sensorSceneDummy = new SensorSceneDummy();
        combus = new CommBus();
        _radarModul = new RadarModul(sensorSceneDummy, combus, _angle, _samplingTime);
    }


    @Test
    public void testRadarForNoIncomingData() throws Exception {

        RadarMessagePacket result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(10, currentPos);
        assertNull(result);
    }

    @Test
    public void testRadarForFirstCycle() throws Exception {
        RadarMessagePacket result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(10, currentPos);
        //next call, but we still expect the result to be null, because we only had 1 cycle of data
        result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(11, currentPos);

        assertNull(result);
    }

    @Test
    public void testRadarForMultipleCyclesAndDeletion() throws Exception {
        RadarMessagePacket result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(10, currentPos);
        result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(11,currentPos);

        //now we expect some output with relative speed and distance of the closest object
        result = _radarModul.getDetectedObjsRelativeSpeedAndDistance(12,currentPos);
        assertNotNull(result);
    }

    @Test
    public void testGetMostRecentVectorsDeletingItems() throws Exception {
        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(2, 2));
        inputPositions.add(new Vector2D(3, 3));
        inputPositions.add(new Vector2D(4, 3));
        inputPositions.add(new Vector2D(5, 3));
        inputPositions.add(new Vector2D(6, 3));
        RadarMessagePacket test = _radarModul.getDetectedObjsRelativeSpeedAndDistance(30, currentPos);

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