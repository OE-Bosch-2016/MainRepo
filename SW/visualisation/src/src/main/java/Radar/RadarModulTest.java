package Radar;

import Interfaces.IRadarInputData;
import Utils.Vector2D;
import junit.framework.TestCase;


import java.util.ArrayList;

import static org.easymock.EasyMock.*;

/**
 * Created by secured on 2016. 03. 20..
 */
public class RadarModulTest extends TestCase {

    private IRadarInputData _radarDataInputMOCK;
    private RadarModul _radarModul;
    private int _samplingTime=2;
    private int _angle =30;

    private ArrayList<Vector2D> inputPositions;

    @Override
    public void setUp()  {
        _radarDataInputMOCK=createStrictMock(IRadarInputData.class);
        _radarModul=new RadarModul(_radarDataInputMOCK, _angle,_samplingTime);

    }


    public void testGetRadarPosition() throws Exception {
        Vector2D mockedPos= new Vector2D(10,10);
        expect(_radarDataInputMOCK.getOurCurrentPosition()).andReturn(mockedPos);
        replay(_radarDataInputMOCK);  //replay ->"Done with setting up mocked object, do your work!"

        Vector2D expectedCameraPos= mockedPos;
        Vector2D actualCameraPos= _radarModul.getRadarPosition();

        assertEquals(expectedCameraPos.get_coordinateX(),actualCameraPos.get_coordinateX());
        assertEquals(expectedCameraPos.get_coordinateY(), actualCameraPos.get_coordinateY());
        assertEquals(expectedCameraPos,mockedPos);
    }

    public void testGetRadarViewDistance() throws Exception {
        int expectedViewDistance=200;
        int actualViewDistance=_radarModul.getRadarViewDistance();

        assertEquals(expectedViewDistance,actualViewDistance);
    }

    public void testGetAngelOfSight() throws Exception {
        int delta=10;
        float expectedAngle=20;
        float actualAngle=_radarModul.getAngelOfSight(); //actual=30, as in the constructor

        assertEquals(expectedAngle,actualAngle,delta);
    }

    public void testCurrentSpeedInput() throws Exception{
        double returnvalue=20;
        expect(_radarDataInputMOCK.getOurCurrentSpeed()).andReturn(returnvalue);
        replay(_radarDataInputMOCK);

        double actual= _radarModul.getOurCurrentSpeed();
        assertEquals(returnvalue,actual);
    }

    public void testGetIncomingPositionList() throws Exception {
        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(2,2));
        inputPositions.add(new Vector2D(4,4));
        expect(_radarDataInputMOCK.getViewableObjectList()).andReturn(inputPositions);
        replay(_radarDataInputMOCK);

        int actualSize = _radarModul.getIncomingPositionList().size();
        int expected=2;

        assertEquals(expected, actualSize);
    }

    public void testGetDetectedObjsRelativeSpeedDistance() throws Exception {
        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(2,2));
        inputPositions.add(new Vector2D(4, 4));

        double currentSpeed=20;
        Vector2D mockedPos = new Vector2D(5,5);

        int expectedSize=2;
        ArrayList<SpeedAndDistanceObj> res = _radarModul.getDetectedObjsRelativeSpeedDistance(inputPositions, mockedPos, currentSpeed);
        int actualSize = res.size();
        SpeedAndDistanceObj valueObj=res.get(0);
        SpeedAndDistanceObj valueObj2 = res.get(1);

        assertEquals(expectedSize,actualSize);

        double changedCurrentSpeed=30;
        Vector2D changedPos = new Vector2D(7,7);
        inputPositions = new ArrayList<Vector2D>();
        inputPositions.add(new Vector2D(5,5));
        inputPositions.add(new Vector2D(10, 10));

        int changedExpSize=2;
        ArrayList<SpeedAndDistanceObj> result2=_radarModul.getDetectedObjsRelativeSpeedDistance(inputPositions,changedPos,changedCurrentSpeed);
        SpeedAndDistanceObj firstObj= result2.get(0);
        SpeedAndDistanceObj secondObj= result2.get(1);

        boolean isTrue= firstObj.getRelativeSpeed()>0;
        boolean isTrue2= secondObj.getRelativeSpeed()>0;

        assertEquals(changedExpSize,result2.size());
        assertTrue(isTrue);
        assertTrue(isTrue2);
    }

    public void Equelsttest(){

    }
}