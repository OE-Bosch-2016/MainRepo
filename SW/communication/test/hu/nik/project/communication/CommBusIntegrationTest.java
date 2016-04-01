package hu.nik.project.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Console;
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

    private static byte[] testIntByteData;

    private static String testStringData = "TESTDATA";
    private static byte[] testStringByteData;

    @BeforeClass
    public static void setUp() throws Exception {
        // test commBus only
        commBus = new CommBus();
        // test devices (they implements the ICommBusDevice interface)
        testDevice1 = new TestDevice(commBus, Integer.class, CommBusConnectorType.ReadWrite);
        testDevice2 = new TestDevice(commBus, String.class, CommBusConnectorType.ReadOnly);

        // test data for write and read tests
        testIntByteData = ByteBuffer.allocate(4).putInt(22222).array();
        testStringByteData = testStringData.getBytes();
    }

    @Test
    public void testGetDevice() throws Exception {
        Assert.assertEquals(testDevice1, testDevice1.getCommBusConnector().getDevice());
        Assert.assertEquals(testDevice2, testDevice2.getCommBusConnector().getDevice());
    }

    @Test
    public void testGetConnectorType() throws Exception {
        Assert.assertEquals( CommBusConnectorType.ReadWrite, testDevice1.getCommBusConnector().getConnectorType());
        Assert.assertEquals( CommBusConnectorType.ReadOnly, testDevice2.getCommBusConnector().getConnectorType());
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
    public void testSendAndReceive() throws Exception {
        // Send testdata to device2
        testDevice1.getCommBusConnector().send(String.class, "DataArrived");
        // Send new object, and make sure that the sending is failed, because there is in progress sending on the bus
        Assert.assertFalse(testDevice1.getCommBusConnector().send(String.class, "NewDataArrived"));
        // Send new object, and make sure that the sending is not failed, because there was enough time for ending previous sending
        Thread.sleep(100);
        // Check the first send arrives data arrived
        Assert.assertEquals("DataArrived", testDevice2.getStringData());
        //
        Thread.sleep(300);
        Assert.assertTrue(testDevice1.getCommBusConnector().send(String.class, "NewDataArrived"));
        Thread.sleep(300);
        Assert.assertTrue(testDevice1.getCommBusConnector().send(String.class, "NewestDataArrived"));
        // Check the second send arrives data arrived
        Thread.sleep(500);
        Assert.assertNotEquals("NewDataArrived", testDevice2.getStringData());
        Assert.assertEquals("NewestDataArrived", testDevice2.getStringData()); // the data is overwritten
        // Try to receive again, when there is no data on bus
        Assert.assertNull(testDevice2.getCommBusConnector().getDataType());
        Assert.assertNull(testDevice2.getCommBusConnector().receive());
    }

    @Test
    public void testStressCommunication() throws Exception{
        // Add new devices to the bus
        TestDevice deviceWithSimpleRoad = new TestDevice(commBus, SimpleRoad.class, CommBusConnectorType.ReadWrite);
        TestDevice deviceWithInteger = new TestDevice(commBus, Integer.class, CommBusConnectorType.ReadWrite);
        TestDevice deviceWithScenePoint = new TestDevice(commBus, ScenePoint.class, CommBusConnectorType.ReadWrite);

        deviceWithInteger.getCommBusConnector().send(ScenePoint.class, new ScenePoint(120, 110));
        deviceWithScenePoint.getCommBusConnector().send(Integer.class, (int)52125);
        deviceWithScenePoint.getCommBusConnector().send(SimpleRoad.class, new SimpleRoad(new ScenePoint(222,111), 90, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT ));

        Thread.sleep(2000);

        Assert.assertEquals("???", deviceWithScenePoint.getStringData());          // if exception occured, the error-message appeared in the stringData
        Assert.assertEquals(120, deviceWithScenePoint.getScenePointData().getX());

        Assert.assertEquals("???", deviceWithInteger.getStringData());
        Assert.assertEquals(52125, deviceWithInteger.getIntData());

        Assert.assertEquals("???", deviceWithSimpleRoad.getStringData());
        Assert.assertEquals(SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT, deviceWithSimpleRoad.getSimpleRoadData().getObjectType());
    }
}