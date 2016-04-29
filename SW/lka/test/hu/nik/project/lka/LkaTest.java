package hu.nik.project.lka;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhodvogner on 2016. ápr. 15..
 */
public class LkaTest {

    private static CommBus commBus;
    private static Lka lka;
    private static TestCamera testCamera;
    private static TestDriverInput testDriverInput;
    private static TestDevice testDevice;

    @BeforeClass
    public static void setUp() throws Exception {

        // initialize test-variables
        commBus = new CommBus();
        lka = new Lka(commBus, CommBusConnectorType.SenderReceiver);
        testCamera = new TestCamera(commBus);
        testDriverInput = new TestDriverInput(commBus);
        testDevice = new TestDevice(commBus, LkaMessagePackage.class, CommBusConnectorType.Receiver);
    }

    @Test
    public void testLkaProcess() throws Exception {

        LkaMessagePackage mp;

        // LKA on
        testDriverInput.SendAsDriverInput(true);
        Thread.sleep(100);

        Assert.assertEquals("", lka.getLastErrorMessage());
        Assert.assertTrue(lka.getEnabled());

        lka.setLastErrorMessage("errormessage-reset");
        // Camera send a distance
        testCamera.SendAsCameraMessage(87); // it's a normal distance
        Thread.sleep(100);

        Assert.assertEquals("", lka.getLastErrorMessage());

        Assert.assertEquals("", testDevice.getStringData());
        mp = (LkaMessagePackage)testDevice.getLastData();
        Assert.assertNotNull(mp);
        Assert.assertEquals(0, mp.getRequestedSteeringWheelAngle());
        Assert.assertTrue(mp.getMaximumSpeedForKeepingLane() > 40);

        // Camera send a higher distance
        testCamera.SendAsCameraMessage(87 + 30); // it's a normal distance
        Thread.sleep(100);

        mp = (LkaMessagePackage)testDevice.getLastData();
        Assert.assertNotNull(mp);
        Assert.assertEquals(-5, mp.getRequestedSteeringWheelAngle());
        Assert.assertTrue(mp.getMaximumSpeedForKeepingLane() > 40);

        // Camera send a lower distance
        testCamera.SendAsCameraMessage(87 - 30); // it's a normal distance
        Thread.sleep(100);

        mp = (LkaMessagePackage)testDevice.getLastData();
        Assert.assertNotNull(mp);
        Assert.assertEquals(+5, mp.getRequestedSteeringWheelAngle());
        Assert.assertTrue(mp.getMaximumSpeedForKeepingLane() > 40);

    }



}


