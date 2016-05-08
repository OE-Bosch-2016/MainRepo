package hu.nik.project.tsr;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.DirectionSign;
import hu.nik.project.environment.objects.PrioritySign;
import hu.nik.project.environment.objects.SpeedSign;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by CyberZero 2016. 04. 07..
 */
public class TsrTest {

    private static CommBus commBus;
    private static Tsr tsrModule;
    private static TestDevice senderDevice, tsrReceiverDevice;
    private static SpeedSign speedSignToSend;
    private static PrioritySign prioritySignToSend;
    private static DirectionSign directionSignToSend;

    @BeforeClass
    public static void setUp() throws Exception {
        speedSignToSend = new SpeedSign(new ScenePoint(235, 3908), 270, SpeedSign.SpeedSignType.LIMIT_50);
        prioritySignToSend = new PrioritySign(new ScenePoint(235, 3908), 270, PrioritySign.PrioritySignType.STOP);
        directionSignToSend = new DirectionSign(new ScenePoint(235, 3908), 270, DirectionSign.DirectionType.FORWARD_LEFT);
        commBus = new CommBus();
        senderDevice = new TestDevice(commBus, SpeedSign.class, CommBusConnectorType.SenderReceiver);
        tsrModule = new Tsr(commBus, CommBusConnectorType.SenderReceiver, new Car(new ScenePoint(100, 100), 0));
        tsrReceiverDevice = new TestDevice(commBus, TsrMessagePackage.class, CommBusConnectorType.SenderReceiver);
    }

    @Test
    public void testSendAndReceiveSpeedSign() throws Exception {

        // TSR on
        DriverInputMessagePackage driverInputMessagePackage = new DriverInputMessagePackage(0,0,0,0,0,0,false,false,true,false,false,false);
        senderDevice.getCommBusConnector().send(driverInputMessagePackage);
        Thread.sleep(100);
        tsrModule.doWork();

        // everyone sends a speed-limit sign onto the bus
        senderDevice.getCommBusConnector().send( speedSignToSend );
        // must wait a little for transmission, processing and forwarding as a TsrPacket
        Thread.sleep(100);
        tsrModule.doWork();

        // if TSR modul works fine the speed-sign will be forwarded as an expected TsrPacket:
        Thread.sleep(100);

        TsrMessagePackage expected = new TsrMessagePackage(50, speedSignToSend.getCenter()); // we except the central position of the sign!!!
        TsrMessagePackage received = tsrReceiverDevice.getTsrPacketData();

        // a test-trick: last error message must be empty (if not empty, we will see what's happened)
        Assert.assertEquals("", tsrReceiverDevice.getStringData());

        // must boxed into the given speed limitation value and the center-point
        Assert.assertEquals(expected.getSpeedLimit(), received.getSpeedLimit());
        Assert.assertEquals(expected.getCenterPoint().getY(), received.getCenterPoint().getY());
        Assert.assertEquals(expected.getCenterPoint().getX(), received.getCenterPoint().getX());
    }

    @Test
    public void testSendAndReceivePrioritySign() throws Exception {

        // TSR on
        DriverInputMessagePackage driverInputMessagePackage = new DriverInputMessagePackage(0,0,0,0,0,0,false,false,true,false,false,false);
        senderDevice.getCommBusConnector().send(driverInputMessagePackage);
        Thread.sleep(100);
        tsrModule.doWork();

        // everyone sends a speed-limit sign onto the bus
        senderDevice.getCommBusConnector().send( prioritySignToSend );
        // must wait a little for transmission, processing and forwarding as a TsrPacket
        Thread.sleep(100);
        tsrModule.doWork();

        // if TSR modul works fine the speed-sign will be forwarded as an expected TsrPacket:
        Thread.sleep(100);

        TsrMessagePackage expected = new TsrMessagePackage(0, prioritySignToSend.getCenter()); // we except the central position of the sign!!!
        TsrMessagePackage received = tsrReceiverDevice.getTsrPacketData();

        // a test-trick: last error message must be empty (if not empty, we will see what's happened)
        Assert.assertEquals("", tsrReceiverDevice.getStringData());

        // must boxed into the given speed limitation value and the center-point
        Assert.assertEquals(expected.getSpeedLimit(), received.getSpeedLimit());
        Assert.assertEquals(expected.getCenterPoint().getY(), received.getCenterPoint().getY());
        Assert.assertEquals(expected.getCenterPoint().getX(), received.getCenterPoint().getX());

    }

    @Test
    public void testSendAndReceiveDirectionSign() throws Exception {

        // TSR on
        DriverInputMessagePackage driverInputMessagePackage = new DriverInputMessagePackage(0,0,0,0,0,0,false,false,true,false,false,false);
        senderDevice.getCommBusConnector().send(driverInputMessagePackage);
        Thread.sleep(100);
        tsrModule.doWork();

        // everyone sends a speed-limit sign onto the bus
        senderDevice.getCommBusConnector().send( directionSignToSend );
        // must wait a little for transmission, processing and forwarding as a TsrPacket
        Thread.sleep(100);
        tsrModule.doWork();

        // if TSR modul works fine the speed-sign will be forwarded as an expected TsrPacket:
        Thread.sleep(100);

        TsrMessagePackage expected = new TsrMessagePackage(0, directionSignToSend.getCenter()); // we except the central position of the sign!!!
        TsrMessagePackage received = tsrReceiverDevice.getTsrPacketData();

        // a test-trick: last error message must be empty (if not empty, we will see what's happened)
        Assert.assertEquals("", tsrReceiverDevice.getStringData());

        // must boxed into the given speed limitation value and the center-point
        Assert.assertEquals(expected.getSpeedLimit(), received.getSpeedLimit());
        Assert.assertEquals(expected.getCenterPoint().getY(), received.getCenterPoint().getY());
        Assert.assertEquals(expected.getCenterPoint().getX(), received.getCenterPoint().getX());

    }
}
