package hu.nik.project.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by hodvogner.zoltan on 2016.03.24.
 *
 * Testing of sending and receiving with test-devices
 */
public class CommBusIntegrationTest {

    private static CommBus commBus;
    private static TestDevice1 testDevice1;
    private static TestDevice2 testDevice2;
    private static TestDevice3 testDevice3;
    private static CommBusConnector commBusConnector1;
    private static CommBusConnector commBusConnector2;
    private static CommBusConnector commBusConnector3;
    private static byte[] testData1;
    private static byte[] testData2;

    @BeforeClass
    public static void setUp() throws Exception {
        // commBus instanmce for tests
        // commBus = new CommBus();
        // test devices (they implements the ICommBusDevice interface)
        testDevice1 = new TestDevice1();
        testDevice2 = new TestDevice2();
        testDevice3 = new TestDevice3();
        // test data for write and read tests
        testData1 = "SPEED;45;km/h".getBytes();
        testData2 = "STEERING;90;degrees".getBytes();
    }

    @Test
    public void testSendingAndReceivingData() throws Exception {
        // reset
        commBus = null;
        commBus = new CommBus();
        // bus-attached connectors for tests
        commBusConnector1 = commBus.createConnector(testDevice1, CommBusConnectorType.ReadWrite);
        commBusConnector2 = commBus.createConnector(testDevice2, CommBusConnectorType.ReadOnly);
        commBusConnector3 = commBus.createConnector(testDevice3, CommBusConnectorType.WriteOnly);

        Assert.assertEquals(0, commBusConnector1.getDataType());
        Assert.assertEquals(0, commBusConnector2.getDataType());
        Assert.assertEquals(0, commBusConnector3.getDataType());

        //
        // connector1 send a data with type(2)                      DATA from DEVICE1 ===> BUS
        //
        commBusConnector1.write(2,testData1);

        Assert.assertFalse(commBus.isBusFree()); // bus is busy (data is on the way)
        Thread.sleep(500);                       // wait for transmission
        Assert.assertTrue(commBus.isBusFree());  // bus is already free (data has been sent)

        //
        // connector2 is interested for type=2 data-packages        DATA to DEVICE2 <=== BUS
        //
        Assert.assertEquals(2, testDevice2.getLastEventDataType());
        // connector2 received the message
        byte[] lastDataReceivedByTestDevice2 = testDevice2.getLastData();
        Assert.assertEquals(testData1.length,lastDataReceivedByTestDevice2.length);

        // check the send and received data (they are same content)
        Assert.assertTrue(Arrays.equals(lastDataReceivedByTestDevice2,testData1));

        //
        // connector3 send a data with type(1)                      DATA from DEVICE3 ===> BUS
        //
        commBusConnector3.write(1,testData2);

        Assert.assertFalse(commBus.isBusFree()); // bus is busy (data is on the way)
        Thread.sleep(500);                       // wait for transmission
        Assert.assertTrue(commBus.isBusFree());  // bus is already free (data has been sent)

        //
        // connector2 is not interested for type=1 data-packages    DATA to DEVICE2 ==X== BUS
        //
        Assert.assertEquals(2, testDevice2.getLastEventDataType());
        // connector2 didn't received the message
        lastDataReceivedByTestDevice2 = testDevice2.getLastData();
        Assert.assertEquals(testData1.length,lastDataReceivedByTestDevice2.length);

        //
        // connector1 is interested for type=1 data-packages        DATA to DEVICE1 <=== BUS
        //
        Assert.assertEquals(1, testDevice1.getLastEventDataType());
        // connector2 didn't received the message
        byte[] lastDataReceivedByTestDevice1 = testDevice1.getLastData();
        Assert.assertNotEquals(0,lastDataReceivedByTestDevice1.length);

        // check the send and received data (they are same content)
        Assert.assertTrue(Arrays.equals(lastDataReceivedByTestDevice1,testData2));

    }

}