package Radar;

import Interfaces.IRadarData;
import Interfaces.IRadarInputData;
import Utils.Vector2D;

import java.util.ArrayList;
import java.util.Timer;


/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarModul implements IRadarData {

    private IRadarInputData _radarInputData;
    private static final int _viewDistance=200;
    private float _angelOfSight;
    private int _sampingTime;
    private ArrayList<SpeedAndDistanceObj> _speedAndDistanceObjs;
    private Boolean _isRadarEnabled;
    private Timer _timer; //for sampling

    private ArrayList<Vector2D> _previousVectors;
    private ArrayList<Vector2D> _currentVectors;


    public RadarModul(IRadarInputData radarInputData, float angelOfSight, int samplingTime) {
        _radarInputData = radarInputData;
        _angelOfSight=angelOfSight;
        _sampingTime=samplingTime;

        _speedAndDistanceObjs = new ArrayList<SpeedAndDistanceObj>();
        _isRadarEnabled =false;
        _previousVectors = new ArrayList<Vector2D>();
        _currentVectors = new ArrayList<Vector2D>();
    }

    //<editor-fold desc="Properties of Radar Modul">

    public double getOurCurrentSpeed(){
        return _radarInputData.getOurCurrentSpeed();
    }

    public ArrayList<Vector2D> getIncomingPositionList(){
        return _radarInputData.getViewableObjectList();
    }

    public Vector2D getOurCurrentPosition(){return _radarInputData.getOurCurrentPosition();}

    public ArrayList<SpeedAndDistanceObj> get_speedAndDistanceObjs() {
        return _speedAndDistanceObjs;
    }

    public Vector2D getRadarPosition() {  //change here, if the camera position is not the same as our current pos.
        return _radarInputData.getOurCurrentPosition();
    }

    public Integer getRadarViewDistance() {
        return _viewDistance;
    }

    public Float getAngelOfSight() {
        return _angelOfSight;
    }

    public Boolean isRadarEnabled() {
        return _isRadarEnabled;
    }

    public void setIsRadarEnabled(Boolean isRadarEnabled) {
        this._isRadarEnabled = isRadarEnabled;
    }

    //</editor-fold>

    public ArrayList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedAndDistance() {

        ArrayList<Vector2D> viewableObjectList = _radarInputData.getViewableObjectList();
        Vector2D ourCurrentPos= _radarInputData.getOurCurrentPosition();
        double ourCurrentSpeed= _radarInputData.getOurCurrentSpeed();

        if(viewableObjectList!=null && viewableObjectList.size()!=0 ){
            for (int i = 0; i < viewableObjectList.size(); i++) {
                double distance = getDistance(viewableObjectList.get(i),ourCurrentPos);

                if(_speedAndDistanceObjs.get(i)==null){
                    //if previous values are empty, we create them
                    SpeedAndDistanceObj mapObjData = new SpeedAndDistanceObj(0,distance,viewableObjectList.get(i));
                    _speedAndDistanceObjs.add(mapObjData);
                }else{
                    SpeedAndDistanceObj previous = _speedAndDistanceObjs.get(i);
                    Vector2D currentPos = viewableObjectList.get(i);

                    double currentSpeed= getCurrentSpeedOfSpecificObj(previous,currentPos);
                    double relativeSpeed=calculateRelativeSpeed(ourCurrentSpeed,currentSpeed);

                    previous.setCurrentDistance(distance);
                    previous.setCurrentPosition(currentPos);
                    previous.setRelativeSpeed(relativeSpeed);
                }

            }
        }

        return _speedAndDistanceObjs;
    }

    //.equels()
    public ArrayList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedDistance(ArrayList<Vector2D> inPositions, Vector2D currentPosition, double ourInputSpeed){

        ArrayList<Vector2D> viewableObjectList = inPositions;
        Vector2D ourCurrentPos= currentPosition;
        double ourCurrentSpeed= ourInputSpeed;

        ArrayList<SpeedAndDistanceObj> tempObjData = new ArrayList<SpeedAndDistanceObj>();

        if(viewableObjectList!=null && viewableObjectList.size()!=0 ){
            for (int i = 0; i < viewableObjectList.size(); i++) {
                double distance = getDistance(viewableObjectList.get(i),ourCurrentPos);

                if(_speedAndDistanceObjs.isEmpty()){
                    //if previous values are empty, we create them
                    SpeedAndDistanceObj mapObjData = new SpeedAndDistanceObj(0,distance,viewableObjectList.get(i));
                    tempObjData.add(mapObjData);
                }else{
                    SpeedAndDistanceObj previous = _speedAndDistanceObjs.get(i);
                    Vector2D currentPos = viewableObjectList.get(i);

                    double currentSpeed= getCurrentSpeedOfSpecificObj(previous,currentPos);
                    double relativeSpeed=calculateRelativeSpeed(ourCurrentSpeed,currentSpeed);

                    _speedAndDistanceObjs.get(i).setCurrentDistance(distance);
                    _speedAndDistanceObjs.get(i).setCurrentPosition(currentPos);
                    _speedAndDistanceObjs.get(i).setRelativeSpeed(relativeSpeed);
                }
            }
        }

        if(_speedAndDistanceObjs.isEmpty()){
            _speedAndDistanceObjs =tempObjData;
            return _speedAndDistanceObjs;
        }
        else{
            return _speedAndDistanceObjs;
        }
    }

    private double getDistance(Vector2D x, Vector2D y){
            double xCoordinate = Math.pow(x.get_coordinateX() - y.get_coordinateX(),2);
            double yCoordinate= Math.pow(x.get_coordinateY() - y.get_coordinateY(),2);
            return Math.sqrt(xCoordinate+yCoordinate);
    }

    private double getCurrentSpeedOfSpecificObj(SpeedAndDistanceObj previous, Vector2D current){
        return getDistance(previous.getCurrentPosition(),current)/_sampingTime;
    }

    private double calculateRelativeSpeed(double firstSpeedValue, double seconedSpeedValue){ //A+B/1+AB
        return  firstSpeedValue+
                seconedSpeedValue/1+
                firstSpeedValue*seconedSpeedValue;
    }

    public  ArrayList<Vector2D> getMostRecentVectorsFromDataBus(ArrayList<Vector2D> incomingVectorDataList){
        ArrayList<Vector2D> recentVectorsList=_previousVectors;

        if(!_previousVectors.isEmpty()){
            for (int i = 0; i < incomingVectorDataList.size(); i++) {
                    Vector2D item = incomingVectorDataList.get(i);
                    if(item!=null && isItemInList(_previousVectors,item)){
                        int itemIndex = _previousVectors.indexOf(item);
                        recentVectorsList.get(itemIndex).set_coordinateX(item.get_coordinateX());
                        recentVectorsList.get(itemIndex).set_coordinateY(item.get_coordinateY());
                    }
                    else if(item==null && _previousVectors.get(i)!=null){
                        recentVectorsList.remove(_previousVectors.get(i));
                    }
                    else{
                        recentVectorsList.add(item);
                    }
            }
        }
        else{
            for (int i = 0; i < incomingVectorDataList.size(); i++) {
                Vector2D item = incomingVectorDataList.get(i);
                SpeedAndDistanceObj speedAndDistObj = new SpeedAndDistanceObj(0,0,item);
                _speedAndDistanceObjs.add(speedAndDistObj);
                _previousVectors.add(item);
            }
            recentVectorsList=incomingVectorDataList;
        }

        return recentVectorsList;
    }

    private boolean isItemInList(ArrayList<Vector2D> list, Vector2D item){
        return list.contains(item);
    }
}
