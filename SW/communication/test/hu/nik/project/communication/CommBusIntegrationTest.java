package hu.nik.project.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

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

    private static int testIntData = 22222;
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
        // Test device with no combus registration

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
        // reset
        testDevice1.getCommBusConnector().reset();
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
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {

        }
        // Check the data arrived
        Assert.assertEquals("DataArrived", testDevice2.getStringData());
    }
}