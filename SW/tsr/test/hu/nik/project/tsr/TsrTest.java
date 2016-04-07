package hu.nik.project.tsr;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SpeedSign;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by CyberZero 2016. 04. 07..
 */
public class TsrTest {

    private static CommBus commBus;
    private static Tsr tsrModule;
    private static TestDevice device, device2;

    @Before
    public void setUp() throws Exception {
        commBus = new CommBus();
        tsrModule = new Tsr(commBus, CommBusConnectorType.ReadWrite);
        device = new TestDevice(commBus, SpeedSign.class, CommBusConnectorType.ReadWrite);
        device2 = new TestDevice(commBus, Tsr.class, CommBusConnectorType.ReadWrite);
    }

    @Test
    public void testSendAndReceive() throws Exception {
        device.getCommBusConnector().send(SpeedSign.class, new SpeedSign(new ScenePoint(235, 3908), 270, SpeedSign.SpeedSignType.LIMIT_50));
        //device sends speed sign, that the tsrModule recieves
        // the tsrModule sends a TsrPacket, that the device2 receives
        Assert.assertEquals(new TsrPacket(50,new ScenePoint(235,3908)),device2.getCommBusConnector().receive());
    }
}
