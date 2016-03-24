package hu.nik.project.communication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by hodvogner.zoltan on 2016.03.24..
 */
public class CommBusTest {

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
        // commBus instanmce for tests
        // commBus = new CommBus();
        // test devices (they implements the ICommBusDevice interface)
        testDevice1 = new TestDevice1();
        testDevice2 = new TestDevice2();
        testDevice3 = new TestDevice3();
        // test data for write and read tests
        testData = "SPEED;45;km/h".getBytes();
    }

    @Test
    public void testCreateConnector() throws Exception {
        // reset
        commBus = null;
        commBus = new CommBus();
        // bus-attached connectors for tests
        commBusConnector1 = commBus.createConnector(testDevice1, CommBusConnectorType.ReadWrite);
        commBusConnector2 = commBus.createConnector(testDevice2, CommBusConnectorType.ReadOnly);
        commBusConnector3 = commBus.createConnector(testDevice3, CommBusConnectorType.WriteOnly);
        //
        Assert.assertEquals(3, commBus.getConnectorCount());
        Assert.assertTrue(commBus.isBusFree());
    }

    @Test
    public void testRemoveConnector() throws Exception {
        // reset
        commBus = null;
        commBus = new CommBus();
        // bus-attached connectors for tests
        commBusConnector1 = commBus.createConnector(testDevice1, CommBusConnectorType.ReadWrite);
        commBusConnector2 = commBus.createConnector(testDevice2, CommBusConnectorType.ReadOnly);
        Assert.assertEquals(2, commBus.getConnectorCount());
        //
        commBus.removeConnector(commBusConnector1);
        Assert.assertEquals(1, commBus.getConnectorCount());
        //
        commBus.removeConnector(commBusConnector2);
        Assert.assertEquals(0, commBus.getConnectorCount());
        //
        commBus.removeConnector(commBusConnector1); // already removed before
        Assert.assertEquals(0, commBus.getConnectorCount());
    }

    @Test
    public void testWrite() throws Exception {
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

        // connector1 send a data with type(2)
        commBusConnector1.write(2,testData);
        Assert.assertFalse(commBus.isBusFree()); // bus is busy (data is on the way)

        Thread.sleep(500);                      // wait for transmission

        Assert.assertTrue(commBus.isBusFree()); // bus is already free (data has been sent)

        Assert.assertEquals(0, commBusConnector1.getDataType()); // zero because it's yourself
        Assert.assertEquals(0, commBusConnector2.getDataType()); // zero because it's the recipient and it read all data
        Assert.assertEquals(0, commBusConnector3.getDataType()); // zera bacause it's write-only
        // so the write is OK
    }
}