package hu.nik.project.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.nio.ByteBuffer;
import hu.nik.project.environment.objects.SimpleRoad;
import hu.nik.project.environment.ScenePoint;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 * Testing of sending and receiving with test-devices
 */
public class CommBusIntegrationTest {

    private static CommBus commBus;
    private static TestDevice testDevice1;
    private static TestDevice testDevice2;
    private static TestDevice testDevice3;

    private static byte[] testIntByteData;

    private static String testStringData = "TESTDATA";
    private static byte[] testStringByteData;

    @BeforeClass
    public static void setUp() throws Exception {
        // test commBus only
        commBus = new CommBus();
        // test devices (they implements the ICommBusDevice interface)
        testDevice1 = new TestDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);
        testDevice2 = new TestDevice(commBus, String.class, CommBusConnectorType.Sender);
        testDevice3 = new TestDevice(commBus, String.class, CommBusConnectorType.Receiver);

        // test data for write and read tests
        testIntByteData = ByteBuffer.allocate(4).putInt(22222).array();
        testStringByteData = testStringData.getBytes();
    }

    @Test
    public void testGetDevice() throws Exception {
        Assert.assertEquals(testDevice1, testDevice1.getCommBusConnector().getDevice());
        Assert.assertEquals(testDevice2, testDevice2.getCommBusConnector().getDevice());
        Assert.assertEquals(testDevice3, testDevice3.getCommBusConnector().getDevice());
    }

    @Test
    public void testGetConnectorType() throws Exception {
        Assert.assertEquals( CommBusConnectorType.SenderReceiver, testDevice1.getCommBusConnector().getConnectorType());
        Assert.assertEquals( CommBusConnectorType.Sender, testDevice2.getCommBusConnector().getConnectorType());
        Assert.assertEquals( CommBusConnectorType.Receiver, testDevice3.getCommBusConnector().getConnectorType());
    }

    @Test
    public void testGetSetDataType() throws Exception {
        // must data exists in the buffer
        testDevice1.getCommBusConnector().setDataBuffer(testIntByteData);
        testDevice1.getCommBusConnector().setDataType(Integer.class);
        Assert.assertEquals(Integer.class, testDevice1.getCommBusConnector().getDataType());
    }

    @Test
    public void testSetDataBuffer() throws Exception {
        testDevice1.getCommBusConnector().setDataBuffer(testIntByteData);
        testDevice1.getCommBusConnector().setDataType(Integer.class);
        Assert.assertEquals(Integer.class, testDevice1.getCommBusConnector().getDataType());
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testConnectionTypeReceiver() throws Exception {
        expectedEx.expect(CommBusException.class);
        expectedEx.expectMessage("Error in CommBusConnector send: Cannot send with this connector");
        testDevice3.getCommBusConnector().send("Fail");
    }

    @Test
    public void testConnectionTypeSender() throws Exception {
        expectedEx.expect(CommBusException.class);
        expectedEx.expectMessage("Error in CommBusConnector receive: Cannot receive with this connector");
        testDevice2.getCommBusConnector().receive();
    }

    @Test
    public void testSendAndReceive() throws Exception {
        // Send testdata to device2
        Assert.assertTrue(testDevice1.getCommBusConnector().send("DataArrived"));
        Assert.assertFalse(testDevice2.getCommBusConnector().send("NewDataArrived1"));
        //
        // Check the first send arrives data arrived
        Assert.assertEquals("DataArrived", testDevice3.getStringData());
        //
        Assert.assertTrue(testDevice1.getCommBusConnector().send("NewDataArrived2"));
        Assert.assertTrue(testDevice1.getCommBusConnector().send("NewestDataArrived"));
        // Check the second send arrives data arrived
        Assert.assertNotEquals("NewDataArrived2", testDevice2.getStringData()); // it was overwritten with ...
        Assert.assertEquals("NewestDataArrived", testDevice3.getStringData()); // ... this one
        // Try to receive again, when there is no data on bus
        Assert.assertNull(testDevice3.getCommBusConnector().getDataType());
        Assert.assertNull(testDevice3.getCommBusConnector().receive());
    }

    @Test
    public void testStressCommunication() throws Exception{
        // Add new devices to the bus
        TestDevice deviceWithSimpleRoad = new TestDevice(commBus, SimpleRoad.class, CommBusConnectorType.SenderReceiver);
        TestDevice deviceWithInteger = new TestDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);
        TestDevice deviceWithScenePoint = new TestDevice(commBus, ScenePoint.class, CommBusConnectorType.SenderReceiver);

        deviceWithInteger.getCommBusConnector().send(new ScenePoint(120, 110));
        deviceWithScenePoint.getCommBusConnector().send((int)52125);
        deviceWithScenePoint.getCommBusConnector().send(new SimpleRoad(new ScenePoint(222,111), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT ));

        Assert.assertEquals(120, deviceWithScenePoint.getScenePointData().getX());
        Assert.assertEquals(52125, deviceWithInteger.getIntData());
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT, deviceWithSimpleRoad.getSimpleRoadData().getObjectType());
    }
}