package hu.nik.project.camera;


import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.DirectionSign;
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
        Car car = new Car(new ScenePoint(3375, 2525), 0);
        CommBus commBus = new CommBus();
        //Car car = new Car(new ScenePoint(3201,3001), 0);

        camera = new Camera(commBus, CommBusConnectorType.Sender, scene, car);
    }

    @Test
    public void getClosestSign() throws Exception {
        camera.doWork();
        Assert.assertEquals(DirectionSign.DirectionType.ROUNDABOUT, camera.getClosestSign().getObjectType());
    }


    public void getLaneDistance() throws Exception {
        camera.doWork();
       // Assert.assertEquals("", camera.getLaneDistance());
    }

}