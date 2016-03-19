package Radar;

import Interfaces.IRadarData;
import Interfaces.IRadarInputData;
import Map.MapLoader;
import Utils.Position;
import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarModul implements IRadarData {

    private IRadarInputData _radarInputData;
    private static final int _viewDistance=200;
    private float _angelOfSight;
    private int _sampingTime;
    private Timer timer; //for sampling
    private ArrayList<MapObjectData> mapObjectValues;

    public RadarModul(IRadarInputData radarInputData, float angelOfSight, int samplingTime) {
        _radarInputData = radarInputData;
        _angelOfSight=angelOfSight;
        _sampingTime=samplingTime;

        mapObjectValues= new ArrayList<MapObjectData>();

    }

    public Position getRadarPosition() {  //change here, if the camera position is not the same as our current pos.
        return (_radarInputData==null)?_radarInputData.getOurCurrentPosition():null;
    }

    public Integer getRadarViewDistance() {
        return _viewDistance;
    }

    public Float getAngelOfSight() {
        return _angelOfSight;
    }

    public ArrayList<MapObjectData> getDetectedObjsRelativeSpeedAndDistance() {
        return null;
    }

    private Double getDistance(Position x, Position y){
            double xCoordinate = Math.pow(x.get_positionX(),2)-Math.pow(y.get_positionX(),2);
            double yCoordinate= Math.pow(x.get_positionY(),2)-Math.pow(y.get_positionY(),2);
            return Math.sqrt(xCoordinate-yCoordinate);
    }

    private void getCurrentDataValues(){

        ArrayList<Position> positions = _radarInputData.getViewableObjectList();
        Position ourCurrentPos= _radarInputData.getOurCurrentPosition();
        double ourCurrentSpeed= _radarInputData.getOurCurrentSpeed();

        if(positions!=null && positions.size()!=0 ){
            for (int i = 0; i < positions.size(); i++) {
                double distance = getDistance(positions.get(i),ourCurrentPos);

                if(mapObjectValues.get(i)==null){
                    //if previous values are empty, we create them
                    MapObjectData mapObjData = new MapObjectData(0,distance,positions.get(i));
                    mapObjectValues.add(mapObjData);
                }else{
                    MapObjectData previous = mapObjectValues.get(i);
                    Position currentPos = positions.get(i);

                    double currentSpeed= getCurrentSpeedOfSpecificObj(previous,currentPos);
                    double relativeSpeed=calculateRelativeSpeed(ourCurrentSpeed,currentSpeed);

                    previous.setCurrentDistance(distance);
                    previous.set_currentPosition(currentPos);
                    previous.set_relativeSpeed(relativeSpeed);
                }

            }
        }
    }

    private double getCurrentSpeedOfSpecificObj(MapObjectData previous,Position current){
        return getDistance(previous.getCurrentPosition(),current)/_sampingTime;
    }

    private double calculateRelativeSpeed(double a, double b){ //A+B/1+AB
        return a+b/1+a*b;
    }
}
