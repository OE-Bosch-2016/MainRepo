package hu.nik.project.communication;

import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SimpleRoad;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 * Testing of sending and receiving with test-devices
 */
public class CommBusMultipleSendingIntegrationTest {

    private static CommBus commBus;
    private static TestChainSendDevice testDevice1;
    private static TestChainSendDevice testDevice2;
    private static TestChainSendDevice testDevice3;
    private static TestChainSendDevice testDevice4;


    @BeforeClass
    public static void setUp() throws Exception {
        // test commBus only
        commBus = new CommBus();
        // test devices (they implement the ICommBusDevice interface)
        testDevice1 = new TestChainSendDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);
        testDevice2 = new TestChainSendDevice(commBus, String.class, CommBusConnectorType.SenderReceiver);
        testDevice3 = new TestChainSendDevice(commBus, SimpleRoad.class, CommBusConnectorType.SenderReceiver);
        testDevice4 = new TestChainSendDevice(commBus, ScenePoint.class, CommBusConnectorType.SenderReceiver);
    }

    @Test
    public void testChainCommunication() throws Exception{

        commBus = new CommBus();
        // Add new devices to the bus
        TestChainSendDevice deviceWithSimpleRoad = new TestChainSendDevice(commBus, SimpleRoad.class, CommBusConnectorType.SenderReceiver);
        TestChainSendDevice deviceWithInteger = new TestChainSendDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);
        TestChainSendDevice deviceWithScenePoint = new TestChainSendDevice(commBus, ScenePoint.class, CommBusConnectorType.SenderReceiver);
        TestChainSendDevice deviceWithString = new TestChainSendDevice(commBus, String.class, CommBusConnectorType.SenderReceiver);

        // Int sending will invoke other send
        deviceWithScenePoint.getCommBusConnector().send((int)52125);

        // All devices received there needed data
        Assert.assertEquals(120, deviceWithScenePoint.getScenePointData().getX());
        Assert.assertEquals(52125, deviceWithInteger.getIntData());
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT, deviceWithSimpleRoad.getSimpleRoadData().getObjectType());
        Assert.assertEquals("InnerSend", deviceWithString.getStringData());
    }

    @Test
    public void testMultipleSending() throws Exception{
        commBus = new CommBus();

        TestChainSendDevice deviceWithString = new TestChainSendDevice(commBus, String.class, CommBusConnectorType.SenderReceiver);
        TestChainSendDevice deviceWithInteger1 = new TestChainSendDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);
        TestChainSendDevice deviceWithInteger2 = new TestChainSendDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);

        // Multiple sending (one can send to all other device)
        deviceWithString.getCommBusConnector().send(5555, true);
        Assert.assertEquals(5555, deviceWithInteger1.getIntData());
        Assert.assertEquals(5555, deviceWithInteger2.getIntData());

        // Multiple sending is cleared when finished, the next multipleSending parameterless sending will be received only once
        deviceWithString.getCommBusConnector().send(6666);
        Assert.assertEquals(6666, deviceWithInteger1.getIntData());
        Assert.assertNotEquals(6666, deviceWithInteger2.getIntData());
    }
}