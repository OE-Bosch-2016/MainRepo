package hu.nik.project.radar;

import hu.nik.project.communication.*;
import hu.nik.project.environment.ISensorScene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.utils.Vector2D;
import hu.nik.project.wheels.WheelsMessagePackage;
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
    private CommBusConnector _commBusConnector;

    private static final int _viewDistance = 200;
    private float _angelOfSight;
    private int _sampingTime;

    private double _currentSpeed;

    private ArrayList<RadarMessagePacket> _radarPackets;
    private Boolean _isRadarEnabled;

    private HashMap<Integer, float[]> _previousVectorsHashMap;
    private ObservableList<RadarMessagePacket> _radarPacketObservableList;
    private OnRadarObjectsListener _radarObjectsListener;

    public RadarModul(ISensorScene sensorScene, CommBus commbus, float angelOfSight, int samplingTime) {
        _sensorScene = sensorScene;
        _communicationBus = commbus;
        _angelOfSight = angelOfSight;
        _sampingTime = samplingTime;

        _commBusConnector = commbus.createConnector(this, CommBusConnectorType.Sender);

        _isRadarEnabled = false;
        _previousVectorsHashMap = new HashMap<Integer, float[]>();

        _radarPackets = new ArrayList<RadarMessagePacket>();
        _radarPacketObservableList = FXCollections.observableList(new ArrayList<RadarMessagePacket>());

    }

    //<editor-fold desc="Properties of radar Modul">

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

    public void commBusDataArrived() {
        WheelsMessagePackage msgPacket;
        Class wheelMessagePacket = _commBusConnector.getDataType();
        if (wheelMessagePacket == WheelsMessagePackage.class) {
            try {
                msgPacket = (WheelsMessagePackage) _commBusConnector.receive();
                _currentSpeed = msgPacket.speed;
            } catch (CommBusException e) {
                e.printStackTrace();
            }
        }
    }

    public RadarMessagePacket getDetectedObjsRelativeSpeedAndDistance(int observerRotation, ScenePoint currentPosition) {
        Vector2D currentPos = new Vector2D((float) currentPosition.getX(), (float) currentPosition.getY());

        ArrayList<Car> incomingData = getSceneObjectsInSpecificArea(currentPos, observerRotation, (int) _angelOfSight, _viewDistance);
        if (incomingData != null) {
            ArrayList<Car> recent = getMostRecentVectorsFromEnv(incomingData);
            _radarPacketObservableList = getDetectedObjsRelativeSpeedDistance(recent, currentPos, _currentSpeed);
            RadarMessagePacket packet = getClosestObjectFromList(_radarPacketObservableList);
            //send packet here
            return packet;
        } else {
            return null;
        }
    }


    // Setter ----------------------------------------------------------------------------------------------------------
    public void setOnRadarObjectListListener(OnRadarObjectsListener radarObjectsListener) {
        _radarObjectsListener = radarObjectsListener;
    }

    //<editor-fold desc="Private methods">

    private void SendPacketToDatabus(RadarMessagePacket packet) throws CommBusException {
        _commBusConnector.send(packet);
    }


    private ArrayList<Car> getSceneObjectsInSpecificArea(Vector2D currentPos, int observerRotation, int viewAngle, int viewDistance) {
        ArrayList<SceneObject> sceneObjectArrayList =
                _sensorScene.getVisibleSceneObjects(new ScenePoint((int) currentPos.get_coordinateX(), (int) currentPos.get_coordinateX()), observerRotation, viewAngle, viewDistance);

        if (sceneObjectArrayList != null) {
            ArrayList<Car> carArrayList = new ArrayList<Car>();
            for (SceneObject item : sceneObjectArrayList) {
                if (item.getObjectType()== Car.CarType.CAR) {
                    Car car = (Car) item;
                    //with creating new vectors, their hashes will never be the same.
                    carArrayList.add(car);
                }
            }
            return carArrayList;
        } else {
            return null;
        }
    }

    //gives back speed and distance objects in terms of current vector values
    private ObservableList<RadarMessagePacket> getDetectedObjsRelativeSpeedDistance(ArrayList<Car> recentCarList, Vector2D ourCurrentPosition, double ourSpeed) {
        if (_radarPacketObservableList != null && _radarPacketObservableList.size() != 0) {
            if (recentCarList.size() != 0) {
                int itemIndex = 0;
                while (itemIndex != recentCarList.size()) {
                    Car currentCar = recentCarList.get(itemIndex);
                    RadarMessagePacket radarPacket = IsCarInRadarPacketList(currentCar);
                    if (radarPacket != null) {
                        Vector2D carPosition = new Vector2D(
                                (float)currentCar.getCenterPoint().getX(),
                                (float)currentCar.getCenterPoint().getY());
                        double distance = getDistance(ourCurrentPosition, carPosition);
                        if (isObjectMoving(currentCar)) {
                            double itemSpeed = getCurrentSpeedOfSpecificObj(radarPacket, currentCar);
                            double relativeSpeed = calculateRelativeSpeed(ourSpeed, itemSpeed);
                            radarPacket.setRelativeSpeed(relativeSpeed);
                            ChangePreviousHashMapValues(currentCar);
                        }
                        radarPacket.setCurrentDistance(distance);
                    } else {
                        RadarMessagePacket newSpeedDistObj = new RadarMessagePacket(0, 0,currentCar);
                        _radarPacketObservableList.add(newSpeedDistObj);
                    }
                    itemIndex++;
                }

                ListIterator myListIterator = _radarPacketObservableList.listIterator(itemIndex);
                while (myListIterator.hasNext()) {
                    RadarMessagePacket removableObj = ((RadarMessagePacket) myListIterator.next());
                    myListIterator.remove();
                }
            } else {
                _radarPacketObservableList = FXCollections.observableList(new ArrayList<RadarMessagePacket>());
                return _radarPacketObservableList;
            }
        } else {
            if (recentCarList.size() != 0) {
                for (int i = 0; i < recentCarList.size(); i++) {
                    Car car = recentCarList.get(i);
                    RadarMessagePacket radarPacket = new RadarMessagePacket(0, 0,car);
                    _radarPacketObservableList.add(radarPacket);
                }
            }
        }
        return _radarPacketObservableList;
    }

    //this function gets the incoming position data and creates a new list while checking the perviously saved positions
    private ArrayList<Car> getMostRecentVectorsFromEnv(ArrayList<Car> incomingCarDataList) {
        ArrayList<Car> recentCarList = new ArrayList<Car>();
        HashMap<Integer, float[]> actualHashMap = CreateHashMapFromArrayList(incomingCarDataList);

        if (!_previousVectorsHashMap.isEmpty()) {
            int index = 0;
            while (index != incomingCarDataList.size()) {
                Car car = incomingCarDataList.get(index);
                if (!isItemInHashMap(car)) {
                    float[] vector = {(float)car.getCenterPoint().getY(), (float)car.getCenterPoint().getY()};
                    _previousVectorsHashMap.put(car.hashCode(), vector);
                }
                recentCarList.add(car);
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
            for (int i = 0; i < incomingCarDataList.size(); i++) {
                Car car = incomingCarDataList.get(i);
                float[] vectorValues = {(float)car.getCenterPoint().getX(), (float)car.getCenterPoint().getY()};
                _previousVectorsHashMap.put(car.hashCode(), vectorValues);
            }
            recentCarList = incomingCarDataList;
        }
        return recentCarList;
    }

    private double getDistance(Vector2D x, Vector2D y) {
        double xCoordinate = Math.pow(x.get_coordinateX() - y.get_coordinateX(), 2);
        double yCoordinate = Math.pow(x.get_coordinateY() - y.get_coordinateY(), 2);
        return Math.sqrt(xCoordinate + yCoordinate);
    }

    private double getCurrentSpeedOfSpecificObj(RadarMessagePacket previous, Car car) {
        Vector2D actualCarVector = new Vector2D((float)car.getCenterPoint().getX(),(float)car.getCenterPoint().getY());
        Vector2D previousCarVector = new Vector2D(
                (float)previous.get_car().getCenterPoint().getX(),
                (float)previous.get_car().getCenterPoint().getY());

        return getDistance(previousCarVector, actualCarVector) / _sampingTime;
    }

    private void ChangePreviousHashMapValues(Car car) {
        _previousVectorsHashMap.get(car.hashCode())[0] = (float)car.getCenterPoint().getX();
        _previousVectorsHashMap.get(car.hashCode())[1] = (float)car.getCenterPoint().getY();
    }

    private double calculateRelativeSpeed(double firstSpeedValue, double seconedSpeedValue) { //A+B/1+AB
        return firstSpeedValue +
                seconedSpeedValue / 1 +
                firstSpeedValue * seconedSpeedValue;
    }


    private RadarMessagePacket IsCarInRadarPacketList(Car car) {
        int index = 0;
        while (index != _radarPackets.size() && car.hashCode()!=_radarPackets.get(index).get_car().hashCode()) {
            index++;
        }
        return (index < _radarPackets.size()) ? _radarPackets.get(index) : null;
    }

    private boolean isObjectMoving(Car currentCar) {
        float[] valuePairs = _previousVectorsHashMap.get(currentCar.hashCode());
        return valuePairs[0] != currentCar.getCenterPoint().getX() || valuePairs[1] != currentCar.getCenterPoint().getY();
    }

    private HashMap<Integer, float[]> CreateHashMapFromArrayList(ArrayList<Car> carArrayList) {
        HashMap<Integer, float[]> hashMap = new HashMap<Integer, float[]>();
        for (Car car : carArrayList) {
            int key = car.hashCode();
            float[] values = {car.getCenterPoint().getX(), car.getCenterPoint().getY()};
            hashMap.put(key, values);
        }
        return hashMap;
    }

    private boolean isItemInHashMap(Car car) {
        return _previousVectorsHashMap.containsKey(car.hashCode());
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


//</editor-fold>

    // Listener --------------------------------------------------------------------------------------------------------
    public interface OnRadarObjectsListener {
        void objectListChanged(ObservableList<RadarMessagePacket> result);
    }

}