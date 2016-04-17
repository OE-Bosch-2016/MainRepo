package hu.nik.project.radar;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.environment.ISensorScene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.utils.Vector2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;


/**
 * Created by secured on 2016. 03. 19..
 */
public class RadarModul implements IRadarSensor, ICommBusDevice {

    private CommBus _communicationBus;
    private ISensorScene _sensorScene;
    private CommBusConnector commBusConnector;

    private static final int _viewDistance = 200;
    private float _angelOfSight;
    private int _sampingTime;
    private Vector2D _currentPosition;

    private ArrayList<RadarMessagePacket> _radarPackets;
    private Boolean _isRadarEnabled;

    private HashMap<Integer, float[]> _previousVectorsHashMap;
    private ObservableList<RadarMessagePacket> _radarPacketObservableList;
    private OnRadarObjectsListener _radarObjectsListener;

    public RadarModul(ISensorScene sensorScene, CommBus commbus, float angelOfSight, int samplingTime, ScenePoint currentPos) {
        _sensorScene = sensorScene;
        _communicationBus = commbus;
        _angelOfSight = angelOfSight;
        _sampingTime = samplingTime;
        _currentPosition = new Vector2D((float) currentPos.getX(), (float) currentPos.getX());

        commBusConnector = commbus.createConnector(this, CommBusConnectorType.Sender);

        _isRadarEnabled = false;
        _previousVectorsHashMap = new HashMap<Integer, float[]>();

        _radarPackets = new ArrayList<RadarMessagePacket>();
        _radarPacketObservableList = FXCollections.observableList(_radarPackets);

    }

    //<editor-fold desc="Properties of radar Modul">

    public ScenePoint getRadarPosition() {
        return new ScenePoint((int) _currentPosition.get_coordinateX(), (int) _currentPosition.get_coordinateY());
    }

    public Integer getRadarViewDistance() {
        return _viewDistance;
    }

    public Float getAngelOfSight() {
        return _angelOfSight;
    }

    public void setIsRadarEnabled(Boolean _isRadarEnabled) {
        this._isRadarEnabled = _isRadarEnabled;
    }

    public Boolean isRadarEnabled() {
        return _isRadarEnabled;
    }

    //</editor-fold>

    public RadarMessagePacket getDetectedObjsRelativeSpeedAndDistance(double currentSpeed, int observerRotation) {
        if (_isRadarEnabled) {
            ArrayList<Vector2D> incomingData = getSceneObjectsInSpecificArea(_currentPosition, observerRotation, (int) _angelOfSight, _viewDistance);
            if (incomingData != null) {
                ArrayList<Vector2D> recent = getMostRecentVectorsFromEnv(incomingData);
                _radarPacketObservableList = getDetectedObjsRelativeSpeedDistance(recent, _currentPosition, currentSpeed);
                return getClosestObjectFromList(_radarPacketObservableList);
            } else {
                return null;
            }
        }
        else{
            return null;
        }
    }


    // Setter ----------------------------------------------------------------------------------------------------------
    public void setOnRadarObjectListListener(OnRadarObjectsListener radarObjectsListener) {
        _radarObjectsListener = radarObjectsListener;
    }

    //<editor-fold desc="Private methods">

    private ArrayList<Vector2D> getSceneObjectsInSpecificArea(Vector2D currentPos, int observerRotation, int viewAngle, int viewDistance) {
        ArrayList<SceneObject> sceneObjectArrayList =
                _sensorScene.getVisibleSceneObjects(new ScenePoint((int) currentPos.get_coordinateX(), (int) currentPos.get_coordinateX()), observerRotation, viewAngle, viewDistance);

        if (sceneObjectArrayList != null) {
            ArrayList<Vector2D> vector2DArrayList = new ArrayList<Vector2D>();
            for (SceneObject item : sceneObjectArrayList) {
                if(item.getObjectType().equals(Car.class)) {
                    Car car = (Car)item;
                    vector2DArrayList.add(new Vector2D(car.getCenterPoint().getX(),car.getCenterPoint().getY()));
                }
            }
            return vector2DArrayList;
        } else {
            return null;
        }
    }

    //gives back speed and distance objects in terms of current vector values
    private ObservableList<RadarMessagePacket> getDetectedObjsRelativeSpeedDistance(ArrayList<Vector2D> recentVectorList, Vector2D ourCurrentPosition, double ourSpeed) {
        if (_radarPackets != null && _radarPackets.size() != 0) {
            if (recentVectorList.size() != 0) {
                int itemIndex = 0;
                while (itemIndex != recentVectorList.size()) {
                    Vector2D currentVector = recentVectorList.get(itemIndex);
                    RadarMessagePacket radarPacket = IsVectorInSpeedandDistObjList(currentVector);
                    if (radarPacket != null) {
                        double distance = getDistance(ourCurrentPosition, currentVector);
                        if (isObjectMoving(currentVector)) {
                            double itemSpeed = getCurrentSpeedOfSpecificObj(radarPacket, currentVector);
                            double relativeSpeed = calculateRelativeSpeed(ourSpeed, itemSpeed);
                            radarPacket.setRelativeSpeed(relativeSpeed);
                            radarPacket.setCurrentPosition(currentVector);
                            ChangePreviousHashMapValues(currentVector);
                        }
                        radarPacket.setCurrentDistance(distance);
                    } else {
                        RadarMessagePacket newSpeedDistObj = new RadarMessagePacket(0, 0, currentVector);
                        _radarPacketObservableList.add(newSpeedDistObj);
                    }
                    itemIndex++;
                }

                ListIterator myListIterator = _radarPackets.listIterator(itemIndex);
                while (myListIterator.hasNext()) {
                    RadarMessagePacket removableObj = ((RadarMessagePacket) myListIterator.next());
                    myListIterator.remove();
                }
            } else {
                _radarPacketObservableList = FXCollections.observableList(new ArrayList<RadarMessagePacket>());
                return _radarPacketObservableList;
            }
        } else {
            if (recentVectorList.size() != 0) {
                for (int i = 0; i < recentVectorList.size(); i++) {
                    Vector2D item = recentVectorList.get(i);
                    RadarMessagePacket radarPacket = new RadarMessagePacket(0, 0, item);
                    _radarPacketObservableList.add(radarPacket);
                }
            }
        }
        return _radarPacketObservableList;
    }

    //this function gets the incoming position data and creates a new list while checking the perviously saved positions
    private ArrayList<Vector2D> getMostRecentVectorsFromEnv(ArrayList<Vector2D> incomingVectorDataList) {
        ArrayList<Vector2D> recentVectorsList = new ArrayList<Vector2D>();
        HashMap<Integer, float[]> actualHashMap = CreateHashMapFromArrayList(incomingVectorDataList);

        if (!_previousVectorsHashMap.isEmpty()) {
            int index = 0;
            while (index != incomingVectorDataList.size()) {
                Vector2D item = incomingVectorDataList.get(index);
                if (!isItemInHashMap(item)) {
                    float[] vector = {item.get_coordinateX(), item.get_coordinateY()};
                    _previousVectorsHashMap.put(item.hashCode(), vector);
                }
                recentVectorsList.add(item);
                index++;
            }
            //TODO: refactor later
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

    private double getCurrentSpeedOfSpecificObj(RadarMessagePacket previous, Vector2D vector2D) {
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

    private RadarMessagePacket IsVectorInSpeedandDistObjList(Vector2D vector) {
        int index = 0;
        while (index != _radarPackets.size() && !vector.equals(_radarPackets.get(index).getCurrentPosition())) {
            index++;
        }
        return (index < _radarPackets.size()) ? _radarPackets.get(index) : null;
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

    private RadarMessagePacket getClosestObjectFromList(ObservableList<RadarMessagePacket> radarPacketList) {
        int min = 0;
        if (IsAllDistanceZero()) {
            for (int i = 1; i < radarPacketList.size(); i++) {
                double current = radarPacketList.get(i).getCurrentDistance();
                double minValue = radarPacketList.get(min).getCurrentDistance();
                if (current < minValue) {
                    min = i;
                }
            }
            return radarPacketList.get(min);
        }
        return null;
    }

    private boolean IsAllDistanceZero() {
        int index = 0;
        double zero = 0;
        while (index != _radarPacketObservableList.size() && _radarPacketObservableList.get(index).getCurrentDistance() == zero) {
            index++;
        }
        return (index > _radarPacketObservableList.size());
    }

    public void commBusDataArrived() {

    }

//</editor-fold>

    // Listener --------------------------------------------------------------------------------------------------------
    public interface OnRadarObjectsListener {
        void objectListChanged(ObservableList<RadarMessagePacket> result);
    }

}