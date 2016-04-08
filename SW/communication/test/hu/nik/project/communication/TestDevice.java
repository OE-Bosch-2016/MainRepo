package hu.nik.project.communication;

import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SimpleRoad;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 *  It's a device with two-way connenction, it can write onto the bus and can receive messages with type=1
 */
class TestDevice implements ICommBusDevice {

    private Class dataType = null;
    private CommBusConnector commBusConnector;
    private Class neededDataType;

    private int intData = 0;
    private String stringData = "";
    private ScenePoint scenePointData;
    private SimpleRoad simpleRoadData;

    public TestDevice(CommBus commBus, Class whatKindOfObjectIsNeededToTest, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        neededDataType = whatKindOfObjectIsNeededToTest;
    }

    @Override
    public void commBusDataArrived() {

        if (commBusConnector.getDataType() == neededDataType) {

            dataType = commBusConnector.getDataType();
            if (commBusConnector.getDataType() == Integer.class) {
                try {
                    intData = (Integer) commBusConnector.receive();
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
            if (commBusConnector.getDataType() == String.class) {
                try {
                    stringData = (String) commBusConnector.receive();
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
            if (commBusConnector.getDataType() == ScenePoint.class) {
                try {
                    scenePointData = (ScenePoint) commBusConnector.receive();
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
            if (commBusConnector.getDataType() == SimpleRoad.class) {
                try {
                    simpleRoadData = (SimpleRoad) commBusConnector.receive();
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
        }
    }

    public int getIntData() {
        return intData;
    }
    public String getStringData() {
        return stringData;
    }
    public ScenePoint getScenePointData() { return scenePointData; }
    public SimpleRoad getSimpleRoadData() { return simpleRoadData; }

    public CommBusConnector getCommBusConnector() { return commBusConnector;}
}
