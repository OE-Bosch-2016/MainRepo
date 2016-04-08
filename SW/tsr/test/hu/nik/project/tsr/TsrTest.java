package hu.nik.project.tsr;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SpeedSign;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by CyberZero 2016. 04. 07..
 */
public class TsrTest {

    private static CommBus commBus;
    private static Tsr tsrModule;
    private static TestDevice speedSignSenderDevice, tsrReceiverDevice;
    private static SpeedSign speedSignToSend;

    @BeforeClass
    public static void setUp() throws Exception {
        speedSignToSend = new SpeedSign(new ScenePoint(235, 3908), 270, SpeedSign.SpeedSignType.LIMIT_50);
        commBus = new CommBus();
        speedSignSenderDevice = new TestDevice(commBus, SpeedSign.class, CommBusConnectorType.SenderReceiver);
        tsrModule = new Tsr(commBus, CommBusConnectorType.SenderReceiver);
        tsrReceiverDevice = new TestDevice(commBus, TsrPacket.class, CommBusConnectorType.SenderReceiver);
    }

    @Test
    public void testSendAndReceive() throws Exception {
        // everyone sends a speed-limit sign onto the bus
        speedSignSenderDevice.getCommBusConnector().send( speedSignToSend );

        // must wait a little for transmission, processing and forwarding as a TsrPacket
        Thread.sleep(200);

        // if TSR modul works fine the speed-sign will be forwarded as an expected TsrPacket:

        TsrPacket expected = new TsrPacket(50, speedSignToSend.getCenter()); // we except the central position of the sign!!!
        TsrPacket received = tsrReceiverDevice.getTsrPacketData();

        // a test-trick: last error message must be empty (if not empty, we will see what's happened)
        Assert.assertEquals("", tsrReceiverDevice.getStringData());

        // must boxed into the given speed limitation value and the center-point
        Assert.assertEquals(expected.getSpeedLimit(), received.getSpeedLimit());
        Assert.assertEquals(expected.getCenterPoint().getY(), received.getCenterPoint().getY());
        Assert.assertEquals(expected.getCenterPoint().getX(), received.getCenterPoint().getX());

    }
}
