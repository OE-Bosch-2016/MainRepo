package hu.nik.project.communication;

import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SceneObjectException;
import hu.nik.project.environment.objects.SimpleRoad;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 *  It's a device with two-way connenction, it can write onto the bus and can receive messages with type=1
 */
class TestChainSendDevice implements ICommBusDevice {

    private Class dataType = null;
    private CommBusConnector commBusConnector;
    private Class neededDataType;

    private int intData = 0;
    private String stringData;
    private ScenePoint scenePointData;
    private SimpleRoad simpleRoadData;

    public TestChainSendDevice(CommBus commBus, Class whatKindOfObjectIsNeededToTest, CommBusConnectorType commBusConnectorType) {
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
                    commBusConnector.send("InnerSend");
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
            if (commBusConnector.getDataType() == String.class) {
                try {
                    stringData = (String) commBusConnector.receive();
                    commBusConnector.send(new ScenePoint(120, 110));
                } catch (CommBusException e) {
                    stringData = e.getMessage();
                }
            }
            if (commBusConnector.getDataType() == ScenePoint.class) {
                try {
                    scenePointData = (ScenePoint) commBusConnector.receive();
                    try {
                        commBusConnector.send(new SimpleRoad(new ScenePoint(200, 300), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT));
                    } catch (SceneObjectException e) {

                    }

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
