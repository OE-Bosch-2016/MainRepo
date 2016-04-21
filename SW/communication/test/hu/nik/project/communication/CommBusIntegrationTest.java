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
        testDevice3 = new TestDevice(commBus, SimpleRoad.class, CommBusConnectorType.Receiver);

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

    @Test
    public void testConnectorCount() throws Exception {
        Assert.assertEquals(3, commBus.getConnectorCount());
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
        // Send testdata to device1
        Assert.assertTrue(testDevice2.getCommBusConnector().send(4444));

        // Check the first send arrives
        Assert.assertEquals(4444, testDevice1.getIntData());
        //
        // Check the second send arrives
        Assert.assertTrue(testDevice1.getCommBusConnector().send(new SimpleRoad(new ScenePoint(444,555), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT)));
        Assert.assertEquals("ClassType: SimpleRoad ->  Position X: 444 Position Y: 555 Rotation: 90 SimpleRoadType: SIMPLE_STRAIGHT", testDevice3.getSimpleRoadData().toString());
        // Try to receive again, when there is no data on bus
        Assert.assertNull(testDevice3.getCommBusConnector().getDataType());
        Assert.assertNull(testDevice3.getCommBusConnector().receive());
    }

    @Test
    public void testStressCommunication() throws Exception{

        //Remove old devices from the bus
        commBus.removeConnector(testDevice1.getCommBusConnector());
        commBus.removeConnector(testDevice2.getCommBusConnector());
        commBus.removeConnector(testDevice3.getCommBusConnector());

        // Add new devices to the bus
        TestDevice deviceWithSimpleRoad = new TestDevice(commBus, SimpleRoad.class, CommBusConnectorType.SenderReceiver);
        TestDevice deviceWithInteger = new TestDevice(commBus, Integer.class, CommBusConnectorType.SenderReceiver);
        TestDevice deviceWithScenePoint = new TestDevice(commBus, ScenePoint.class, CommBusConnectorType.SenderReceiver);

        // Test connectorCount
        Assert.assertEquals(3, commBus.getConnectorCount());

        deviceWithInteger.getCommBusConnector().send(new ScenePoint(120, 110));
        deviceWithScenePoint.getCommBusConnector().send((int)52125);
        deviceWithScenePoint.getCommBusConnector().send(new SimpleRoad(new ScenePoint(222,111), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT ));

        Assert.assertEquals(120, deviceWithScenePoint.getScenePointData().getX());
        Assert.assertEquals(52125, deviceWithInteger.getIntData());
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT, deviceWithSimpleRoad.getSimpleRoadData().getObjectType());
    }
}