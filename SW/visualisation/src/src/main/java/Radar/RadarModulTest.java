package Radar;

import Interfaces.IRadarInputData;
import Utils.Position;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;

/**
 * Created by secured on 2016. 03. 20..
 */
public class RadarModulTest extends TestCase {

    private IRadarInputData _radarDataInputMOCK;
    private RadarModul _radarModul;
    private int _samplingTime=2;
    private int angle=30;

    @Override
    public void setUp()  {
        _radarDataInputMOCK=createStrictMock(IRadarInputData.class);
        _radarModul=new RadarModul(_radarDataInputMOCK,angle,_samplingTime);


    }


    public void testGetRadarPosition() throws Exception {
        Position mockedPos= new Position(10,10);
        expect(_radarDataInputMOCK.getOurCurrentPosition()).andReturn(mockedPos);
        replay(_radarDataInputMOCK);  //replay ->"Done with setting up mocked object, do your work!"

        Position expectedCameraPos= mockedPos;
        Position actualCameraPos= _radarModul.getRadarPosition();

        assertEquals(expectedCameraPos.get_positionX(),actualCameraPos.get_positionX());
        assertEquals(expectedCameraPos.get_positionY(), actualCameraPos.get_positionY());
        assertEquals(expectedCameraPos,mockedPos);
    }

    public void testGetRadarViewDistance() throws Exception {
        int expectedViewDistance=200;
        int actualViewDistance=_radarModul.getRadarViewDistance();

        assertEquals(expectedViewDistance,actualViewDistance);
    }

    public void testGetAngelOfSight() throws Exception {
        float expectedAngle=20;
        float actualAngle=_radarModul.getAngelOfSight(); //actual=30, as in the constructor

        assertEquals(expectedAngle,actualAngle,10);
    }
}