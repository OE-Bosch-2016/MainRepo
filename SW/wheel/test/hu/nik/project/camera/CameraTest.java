package hu.nik.project.camera;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lmpala on 2016.04.14..
 */
public class CameraTest {

    Camera camera;

    @Before
    public void setUp() throws Exception {

        Scene scene = new Scene("..\\SceneRoads\\road_1.xml");
        Car car = new Car(new ScenePoint(3250, 2900), 0);
        CommBus commBus = new CommBus();

        camera = new Camera(commBus, CommBusConnectorType.Sender, scene, car);
    }

    @Test
    public void getClosestSign() throws Exception {
        camera.doWork();
        Assert.assertEquals("", camera.getClosestSign());
    }


    public void getLaneDistance() throws Exception {
        camera.doWork();
        Assert.assertEquals("", camera.getLaneDistance());
    }

}