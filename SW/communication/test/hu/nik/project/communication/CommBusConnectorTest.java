package hu.nik.project.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by hodvogner.zoltan on 2016.03.24..
 */
public class CommBusConnectorTest {

    private static CommBus commBus;
    private static TestDevice1 testDevice1;
    private static TestDevice2 testDevice2;
    private static TestDevice3 testDevice3;
    private static CommBusConnector commBusConnector1;
    private static CommBusConnector commBusConnector2;
    private static CommBusConnector commBusConnector3;
    private static byte[] testData;

    @BeforeClass
    public static void setUp() throws Exception {
        // test commBus only
        commBus = new CommBus();
        // test devices (they implements the ICommBusDevice interface)
        testDevice1 = new TestDevice1();
        testDevice2 = new TestDevice2();
        testDevice3 = new TestDevice3();
        // bus-independent connector classes for tests
        commBusConnector1 = new CommBusConnector(commBus, testDevice1, CommBusConnectorType.ReadWrite);
        commBusConnector2 = new CommBusConnector(commBus, testDevice2, CommBusConnectorType.ReadOnly);
        commBusConnector3 = new CommBusConnector(commBus, testDevice3, CommBusConnectorType.WriteOnly);
        // test data for write and read tests
        testData = "SPEED;45;km/h".getBytes();
    }

    @Test
    public void testGetDevice() throws Exception {
        Assert.assertEquals(testDevice1, commBusConnector1.getDevice());
        Assert.assertEquals(testDevice2, commBusConnector2.getDevice());
        Assert.assertEquals(testDevice3, commBusConnector3.getDevice());
    }

    @Test
    public void testGetConnectorType() throws Exception {
        Assert.assertEquals( CommBusConnectorType.ReadWrite, commBusConnector1.getConnectorType());
        Assert.assertEquals( CommBusConnectorType.ReadOnly, commBusConnector2.getConnectorType());
        Assert.assertEquals( CommBusConnectorType.WriteOnly, commBusConnector3.getConnectorType());
    }

    @Test
    public void testSetDataType() throws Exception {
        // must data exists in the buffer
        commBusConnector1.setDataBuffer(testData);
        commBusConnector1.setDataType(25);
        Assert.assertEquals(25, commBusConnector1.getDataType());
        // reset
        commBusConnector1.read();
    }

    @Test
    public void testGetDataType() throws Exception {
        // no data in buffer
        commBusConnector1.setDataType(5);
        Assert.assertNotEquals(5, commBusConnector1.getDataType()); // no data in buffer
        commBusConnector1.setDataType(0);
        // data in buffer
        commBusConnector1.setDataBuffer(testData);
        commBusConnector1.setDataType(5);
        Assert.assertEquals(5, commBusConnector1.getDataType());
        // reset
        commBusConnector1.read();
    }

    @Test
    public void testSetDataBuffer() throws Exception {
        commBusConnector1.setDataBuffer(testData);
        Assert.assertArrayEquals(testData, commBusConnector1.read());
    }

    @Test
    public void testWrite() throws Exception {
        // write is testeable only on CommBus level, because connectors must be registered on bus
        boolean thrown = false;
        try {
            commBusConnector1.write(2,testData);
        } catch (CommBusException e) {
            if( e.getMessage() == "Unknown connector.")
                thrown = true; // We are waiting this exception!
        }
        assertTrue(thrown);
    }

    @Test
    public void testRead() throws Exception {
        // at beginning no data in buffer
        Assert.assertEquals(0, commBusConnector1.read().length); // buffer is empty
        Assert.assertEquals(0, commBusConnector2.read().length); // buffer is empty
        Assert.assertEquals(0, commBusConnector3.read().length); // buffer is empty
        // set data into buffer
        commBusConnector1.setDataBuffer(testData);
        commBusConnector2.setDataBuffer(testData);
        commBusConnector3.setDataBuffer(testData);
        // re-read and compare
        byte[] excepted = testData.clone();
        byte[] readed = commBusConnector1.read();
        Assert.assertTrue(Arrays.equals(excepted, readed));
        Assert.assertTrue(Arrays.equals(testData.clone(), commBusConnector2.read()));
        Assert.assertEquals(0, commBusConnector3.read().length); // it is a write-only connector!
    }
}