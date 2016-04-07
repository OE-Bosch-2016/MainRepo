package Radar;

import Interfaces.IRadarData;
import Interfaces.IRadarInputData;
import Utils.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;


/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarModul implements IRadarData {

    private IRadarInputData _radarInputData;
    private static final int _viewDistance = 200;
    private float _angelOfSight;
    private int _sampingTime;
    private ArrayList<SpeedAndDistanceObj> _speedAndDistanceObjs;
    private Boolean _isRadarEnabled;
    private Timer _timer; //for sampling

    private HashMap<Integer, int[]> _previousVectorsHashMap;


    public RadarModul(IRadarInputData radarInputData, float angelOfSight, int samplingTime) {
        _radarInputData = radarInputData;
        _angelOfSight = angelOfSight;
        _sampingTime = samplingTime;

        _speedAndDistanceObjs = null;
        _isRadarEnabled = false;
        _previousVectorsHashMap = new HashMap<Integer, int[]>();
    }

    //<editor-fold desc="Properties of Radar Modul">

    public double getOurCurrentSpeed() {
        return _radarInputData.getOurCurrentSpeed();
    }

    public ArrayList<Vector2D> getIncomingPositionList() {
        return _radarInputData.getViewableObjectList();
    }

    public Vector2D getOurCurrentPosition() {
        return _radarInputData.getOurCurrentPosition();
    }

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

    @Deprecated
    public ArrayList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedAndDistance() {

        ArrayList<Vector2D> viewableObjectList = _radarInputData.getViewableObjectList();
        Vector2D ourCurrentPos = _radarInputData.getOurCurrentPosition();
        double ourCurrentSpeed = _radarInputData.getOurCurrentSpeed();

        if (viewableObjectList != null && viewableObjectList.size() != 0) {
            for (int i = 0; i < viewableObjectList.size(); i++) {
                double distance = getDistance(viewableObjectList.get(i), ourCurrentPos);

                if (_speedAndDistanceObjs.get(i) == null) {
                    //if previous values are empty, we create them
                    SpeedAndDistanceObj mapObjData = new SpeedAndDistanceObj(0, distance, viewableObjectList.get(i));
                    _speedAndDistanceObjs.add(mapObjData);
                } else {
                    SpeedAndDistanceObj previous = _speedAndDistanceObjs.get(i);
                    Vector2D currentPos = viewableObjectList.get(i);

                    double currentSpeed = getCurrentSpeedOfSpecificObj(previous, currentPos);
                    double relativeSpeed = calculateRelativeSpeed(ourCurrentSpeed, currentSpeed);

                    previous.setCurrentDistance(distance);
                    previous.setCurrentPosition(currentPos);
                    previous.setRelativeSpeed(relativeSpeed);
                }
            }
        }

        return _speedAndDistanceObjs;
    }

    //gives back speed and distance objects in terms of current vector values
    public ArrayList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedDistance(ArrayList<Vector2D> inputVectors, Vector2D ourCurrentPosition, double ourSpeed) {
        if (_speedAndDistanceObjs != null && _speedAndDistanceObjs.size() != 0) {
            if (inputVectors.size() != 0) {
                int itemIndex = 0;
                while (itemIndex != inputVectors.size()) {
                    Vector2D currentVector = inputVectors.get(itemIndex);
                    SpeedAndDistanceObj speedAndDistanceObj = IsVectorInSpeedandDistObjList(currentVector);
                    if (speedAndDistanceObj != null) {
                        double distance = getDistance(ourCurrentPosition, currentVector);
                        if (isObjectMoving(currentVector)) {
                            double itemSpeed = getCurrentSpeedOfSpecificObj(speedAndDistanceObj, currentVector);
                            double relativeSpeed = calculateRelativeSpeed(ourSpeed, itemSpeed);
                            speedAndDistanceObj.setRelativeSpeed(relativeSpeed);
                            speedAndDistanceObj.setCurrentPosition(currentVector);
                            ChangePreviousHashMapValues(currentVector);
                        }
                        speedAndDistanceObj.setCurrentDistance(distance);
                    } else {
                        SpeedAndDistanceObj newSpeedDistObj = new SpeedAndDistanceObj(0, 0, currentVector);
                        _speedAndDistanceObjs.add(newSpeedDistObj);
                    }
                    itemIndex++;
                }
                //REFACTOR THIS -> into another method, like: RemovingRemainingItems(int index)
                while (itemIndex < _speedAndDistanceObjs.size()) {
                    _speedAndDistanceObjs.remove(itemIndex);
                    itemIndex++;
                }
            } else {
                _speedAndDistanceObjs = new ArrayList<SpeedAndDistanceObj>();
                return _speedAndDistanceObjs;
            }
        } else {
            if (inputVectors.size() != 0) {
                _speedAndDistanceObjs = new ArrayList<SpeedAndDistanceObj>();
                for (int i = 0; i < inputVectors.size(); i++) {
                    Vector2D item = inputVectors.get(i);
                    SpeedAndDistanceObj speedAndDistanceObj = new SpeedAndDistanceObj(0, 0, item);
                    _speedAndDistanceObjs.add(speedAndDistanceObj);
                }
            }
        }
        return _speedAndDistanceObjs;
    }

    private double getDistance(Vector2D x, Vector2D y) {
        double xCoordinate = Math.pow(x.get_coordinateX() - y.get_coordinateX(), 2);
        double yCoordinate = Math.pow(x.get_coordinateY() - y.get_coordinateY(), 2);
        return Math.sqrt(xCoordinate + yCoordinate);
    }

    private double getCurrentSpeedOfSpecificObj(SpeedAndDistanceObj previous, Vector2D vector2D) {
        return getDistance(previous.getCurrentPosition(), vector2D) / _sampingTime;
    }

    private void ChangePreviousHashMapValues(Vector2D vector2D) {
        _previousVectorsHashMap.get(vector2D.hashCode())[0] = vector2D.get_coordinateX();
        _previousVectorsHashMap.get(vector2D.hashCode())[1] = vector2D.get_coordinateY();
    }

    private double calculateRelativeSpeed(double firstSpeedValue, double seconedSpeedValue) { //A+B/1+AB
        return firstSpeedValue +
                seconedSpeedValue / 1 +
                firstSpeedValue * seconedSpeedValue;
    }

    //PROG 1 ROCKS BOYS!!!
    private SpeedAndDistanceObj IsVectorInSpeedandDistObjList(Vector2D vector) {
        int index = 0;
        while (index != _speedAndDistanceObjs.size() && !vector.equals(_speedAndDistanceObjs.get(index).getCurrentPosition())) {
            index++;
        }
        return (index < _speedAndDistanceObjs.size()) ? _speedAndDistanceObjs.get(index) : null;
    }

    private boolean isObjectMoving(Vector2D current) {
        int[] valuePairs = _previousVectorsHashMap.get(current.hashCode());
        return valuePairs[0] != current.get_coordinateX() || valuePairs[1] != current.get_coordinateY();
    }

    //this function gets the incoming position data and creates a new list while checking the perviously saved positions
    public ArrayList<Vector2D> getMostRecentVectorsFromDataBus(ArrayList<Vector2D> incomingVectorDataList) {
        ArrayList<Vector2D> recentVectorsList = new ArrayList<Vector2D>();
        HashMap<Integer, int[]> actualHashMap = CreateHashMapFromArrayList(incomingVectorDataList);

        if (!_previousVectorsHashMap.isEmpty()) {
            int index = 0;
            while (index != incomingVectorDataList.size()) {
                Vector2D item = incomingVectorDataList.get(index);
                if (!isItemInHashMap(item)) {
                    int[] vector = {item.get_coordinateX(), item.get_coordinateY()};
                    _previousVectorsHashMap.put(item.hashCode(), vector);
                }
                recentVectorsList.add(item);
                index++;
            }
            //TODO: REFACTOR MEEE PLSSS, IT HURTS MY BUTT
            ArrayList<Integer> hashMapKeyList= new ArrayList<Integer>();
            for (int key : _previousVectorsHashMap.keySet()){
                if(!actualHashMap.containsKey(key)){
                    hashMapKeyList.add(key);
                }
            }
            for (int key:hashMapKeyList){
                _previousVectorsHashMap.remove(key);
            }
        } else {
            _previousVectorsHashMap.clear();
            for (int i = 0; i < incomingVectorDataList.size(); i++) {
                Vector2D item = incomingVectorDataList.get(i);
                int[] vectorValues = {item.get_coordinateX(), item.get_coordinateY()};
                _previousVectorsHashMap.put(item.hashCode(), vectorValues);
            }
            recentVectorsList = incomingVectorDataList;
        }
        return recentVectorsList;
    }

    private HashMap<Integer, int[]> CreateHashMapFromArrayList(ArrayList<Vector2D> vector2DsList) {
        HashMap<Integer, int[]> hashMap=new HashMap<Integer, int[]>();
        for (Vector2D vector : vector2DsList) {
            int key = vector.hashCode();
            int[] values = {vector.get_coordinateX(), vector.get_coordinateY()};
            hashMap.put(key,values);
        }
        return hashMap;
    }

    private boolean isItemInHashMap(Vector2D item) {
        return _previousVectorsHashMap.containsKey(item.hashCode());
    }
}