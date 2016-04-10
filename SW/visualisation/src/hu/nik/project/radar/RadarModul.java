package radar;

import utils.Vector2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;


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

    private HashMap<Integer, float[]> _previousVectorsHashMap;
    private ObservableList<SpeedAndDistanceObj> _speedAndDistanceObjObservableList;
    private OnRadarObjectsListener _radarObjectsListener;

    public RadarModul(IRadarInputData radarInputData, float angelOfSight, int samplingTime) {
        _radarInputData = radarInputData;
        _angelOfSight = angelOfSight;
        _sampingTime = samplingTime;

        _speedAndDistanceObjs = null;
        _isRadarEnabled = false;
        _previousVectorsHashMap = new HashMap<Integer, float[]>();
        _timer = new Timer();

        _speedAndDistanceObjs = new ArrayList<SpeedAndDistanceObj>();
        _speedAndDistanceObjObservableList = FXCollections.observableList(_speedAndDistanceObjs);

//        _speedAndDistanceObjObservableList.addListener(new ListChangeListener<SpeedAndDistanceObj>() {
//            public void onChanged(Change<? extends SpeedAndDistanceObj> change) {
//                if(_radarObjectsListener!=null){
//                    _radarObjectsListener.objectListChanged(_speedAndDistanceObjObservableList);
//                }
//            }
//        });

        if (_radarObjectsListener != null)
            _radarObjectsListener.objectListChanged(_speedAndDistanceObjObservableList);
    }

    //<editor-fold desc="Properties of radar Modul">


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

    //</editor-fold>


    //TODO: We need to clarify how we will know the OUR current speed and position
    public ObservableList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedAndDistance(ArrayList<Vector2D> incomingData) {
        double currentSpeed = 20;
        Vector2D currentPosition = new Vector2D(0, 4);

        ArrayList<Vector2D> recent = getMostRecentVectorsFromDataBus(incomingData);
        _speedAndDistanceObjObservableList = getDetectedObjsRelativeSpeedDistance(recent, currentPosition, currentSpeed);

        return _speedAndDistanceObjObservableList;
    }

    //into consttructor
    public ArrayList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedAndDistance() {
        return null;
    }


    // Setter ----------------------------------------------------------------------------------------------------------
    public void setOnRadarObjectListListener(OnRadarObjectsListener radarObjectsListener) {
        _radarObjectsListener = radarObjectsListener;
    }

    //<editor-fold desc="Private methods">
    //gives back speed and distance objects in terms of current vector values
    private ObservableList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedDistance(ArrayList<Vector2D> recentVectorList, Vector2D ourCurrentPosition, double ourSpeed) {
        if (_speedAndDistanceObjs != null && _speedAndDistanceObjs.size() != 0) {
            if (recentVectorList.size() != 0) {
                int itemIndex = 0;
                while (itemIndex != recentVectorList.size()) {
                    Vector2D currentVector = recentVectorList.get(itemIndex);
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
                        _speedAndDistanceObjObservableList.add(newSpeedDistObj);
                    }
                    itemIndex++;
                }
                //REFACTOR THIS -> into another method, like: RemovingRemainingItems(int index)

                ListIterator myListIterator = _speedAndDistanceObjs.listIterator(itemIndex);
                while (myListIterator.hasNext()) {
                    SpeedAndDistanceObj removableObj = ((SpeedAndDistanceObj) myListIterator.next());
                    myListIterator.remove();
                }
            } else {
                _speedAndDistanceObjObservableList = FXCollections.observableList(new ArrayList<SpeedAndDistanceObj>());
                return _speedAndDistanceObjObservableList;
            }
        } else {
            if (recentVectorList.size() != 0) {
                for (int i = 0; i < recentVectorList.size(); i++) {
                    Vector2D item = recentVectorList.get(i);
                    SpeedAndDistanceObj speedAndDistanceObj = new SpeedAndDistanceObj(0, 0, item);
                    _speedAndDistanceObjObservableList.add(speedAndDistanceObj);
                }
            }
        }
        return _speedAndDistanceObjObservableList;
    }

    //this function gets the incoming position data and creates a new list while checking the perviously saved positions
    private ArrayList<Vector2D> getMostRecentVectorsFromDataBus(ArrayList<Vector2D> incomingVectorDataList) {
        ArrayList<Vector2D> recentVectorsList = new ArrayList<Vector2D>();
        HashMap<Integer, float[]> actualHashMap = CreateHashMapFromArrayList(incomingVectorDataList);

        if (!_previousVectorsHashMap.isEmpty()) {
            int index = 0;
            while (index != incomingVectorDataList.size()) {
                Vector2D item = incomingVectorDataList.get(index);
                if (!isItemInHashMap(item)) {
                    float[] vector = {item.get_coordinateX(),item.get_coordinateY()};
                    _previousVectorsHashMap.put(item.hashCode(), vector);
                }
                recentVectorsList.add(item);
                index++;
            }
            //TODO: REFACTOR MEEE PLSSS, IT HURTS MY BUTT
            ArrayList<Integer> hashMapKeyList = new ArrayList<Integer>();
            for (int key : _previousVectorsHashMap.keySet()) {
                if (!actualHashMap.containsKey(key)) {
                    hashMapKeyList.add(key);
                }
            }
            for (int key : hashMapKeyList) {
                _previousVectorsHashMap.remove(key);
            }
        } else {
            _previousVectorsHashMap.clear();
            for (int i = 0; i < incomingVectorDataList.size(); i++) {
                Vector2D item = incomingVectorDataList.get(i);
                float[] vectorValues = {item.get_coordinateX(), item.get_coordinateY()};
                _previousVectorsHashMap.put(item.hashCode(), vectorValues);
            }
            recentVectorsList = incomingVectorDataList;
        }
        return recentVectorsList;
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
        float[] valuePairs = _previousVectorsHashMap.get(current.hashCode());
        return valuePairs[0] != current.get_coordinateX() || valuePairs[1] != current.get_coordinateY();
    }

    private HashMap<Integer, float[]> CreateHashMapFromArrayList(ArrayList<Vector2D> vector2DsList) {
        HashMap<Integer, float[]> hashMap = new HashMap<Integer, float[]>();
        for (Vector2D vector : vector2DsList) {
            int key = vector.hashCode();
            float[] values = {vector.get_coordinateX(), vector.get_coordinateY()};
            hashMap.put(key, values);
        }
        return hashMap;
    }

    private boolean isItemInHashMap(Vector2D item) {
        return _previousVectorsHashMap.containsKey(item.hashCode());
    }
//</editor-fold>

    // Listener --------------------------------------------------------------------------------------------------------
    public interface OnRadarObjectsListener {
        void objectListChanged(ObservableList<SpeedAndDistanceObj> result);
    }
}