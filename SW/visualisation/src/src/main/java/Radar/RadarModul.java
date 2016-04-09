package Radar;

import Interfaces.IRadarData;
import Interfaces.IRadarInputData;
import Utils.Position;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;


/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarModul implements IRadarData {

    private IRadarInputData _radarInputData;
    private static final int _viewDistance=200;
    private float _angelOfSight;
    private int _sampingTime;
    private Timer timer; //for sampling
    private ArrayList<MapObjectData> _mapObjectValues;

    public RadarModul(IRadarInputData radarInputData, float angelOfSight, int samplingTime) {
        _radarInputData = radarInputData;
        _angelOfSight=angelOfSight;
        _sampingTime=samplingTime;

        _mapObjectValues = new ArrayList<MapObjectData>();

    }

    public double getOurCurrentSpeed(){
        return _radarInputData.getOurCurrentSpeed();
    }

    public ArrayList<Position> getIncomingPositionList(){
        return _radarInputData.getViewableObjectList();
    }

    public Position getOurCurrentPosition(){return _radarInputData.getOurCurrentPosition();}

    public ArrayList<MapObjectData> get_mapObjectValues() {
        return _mapObjectValues;
    }


    public Position getRadarPosition() {  //change here, if the camera position is not the same as our current pos.
        return _radarInputData.getOurCurrentPosition();
    }

    public Integer getRadarViewDistance() {
        return _viewDistance;
    }

    public Float getAngelOfSight() {
        return _angelOfSight;
    }

    public ArrayList<MapObjectData> getDetectedObjsRelativeSpeedAndDistance() {

        ArrayList<Position> viewableObjectList = _radarInputData.getViewableObjectList();
        Position ourCurrentPos= _radarInputData.getOurCurrentPosition();
        double ourCurrentSpeed= _radarInputData.getOurCurrentSpeed();

        if(viewableObjectList!=null && viewableObjectList.size()!=0 ){
            for (int i = 0; i < viewableObjectList.size(); i++) {
                double distance = getDistance(viewableObjectList.get(i),ourCurrentPos);

                if(_mapObjectValues.get(i)==null){
                    //if previous values are empty, we create them
                    MapObjectData mapObjData = new MapObjectData(0,distance,viewableObjectList.get(i));
                    _mapObjectValues.add(mapObjData);
                }else{
                    MapObjectData previous = _mapObjectValues.get(i);
                    Position currentPos = viewableObjectList.get(i);

                    double currentSpeed= getCurrentSpeedOfSpecificObj(previous,currentPos);
                    double relativeSpeed=calculateRelativeSpeed(ourCurrentSpeed,currentSpeed);

                    previous.setCurrentDistance(distance);
                    previous.setCurrentPosition(currentPos);
                    previous.setRelativeSpeed(relativeSpeed);
                }

            }
        }

        return _mapObjectValues;
    }

    public ArrayList<MapObjectData> getDetectedObjsRelativeSpeedDistance(ArrayList<Position> inPositions, Position currentPosition, double ourInputSpeed){

        ArrayList<Position> viewableObjectList = inPositions;
        Position ourCurrentPos= currentPosition;
        double ourCurrentSpeed= ourInputSpeed;

        ArrayList<MapObjectData> tempObjData = new ArrayList<MapObjectData>();

        if(viewableObjectList!=null && viewableObjectList.size()!=0 ){
            for (int i = 0; i < viewableObjectList.size(); i++) {
                double distance = getDistance(viewableObjectList.get(i),ourCurrentPos);

                if(_mapObjectValues.isEmpty()){
                    //if previous values are empty, we create them
                    MapObjectData mapObjData = new MapObjectData(0,distance,viewableObjectList.get(i));
                    tempObjData.add(mapObjData);
                }else{
                    MapObjectData previous = _mapObjectValues.get(i);
                    Position currentPos = viewableObjectList.get(i);

                    double currentSpeed= getCurrentSpeedOfSpecificObj(previous,currentPos);
                    double relativeSpeed=calculateRelativeSpeed(ourCurrentSpeed,currentSpeed);

                    _mapObjectValues.get(i).setCurrentDistance(distance);
                    _mapObjectValues.get(i).setCurrentPosition(currentPos);
                    _mapObjectValues.get(i).setRelativeSpeed(relativeSpeed);
                }

            }
        }

        if(_mapObjectValues.isEmpty()){
            _mapObjectValues=tempObjData;
            return _mapObjectValues;
        }
        else{
            return _mapObjectValues;
        }
    }

    private double getDistance(Position x, Position y){
            double xCoordinate = Math.pow(x.get_positionX() - y.get_positionX(),2);
            double yCoordinate= Math.pow(x.get_positionY() - y.get_positionY(),2);
            return Math.sqrt(xCoordinate+yCoordinate);
    }

    private double getCurrentSpeedOfSpecificObj(MapObjectData previous,Position current){
        return getDistance(previous.getCurrentPosition(),current)/_sampingTime;
    }

    private double calculateRelativeSpeed(double a, double b){ //A+B/1+AB
        return a+b/1+a*b;
    }
}
