package hu.nik.project.camera;


import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.DirectionSign;
import hu.nik.project.environment.objects.SpeedSign;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lmpala on 2016.04.14..
 */
public class CameraTest {

    Camera camera1;
    Camera camera2;

    @Before
    public void setUp() throws Exception {

        Scene scene = new Scene("..\\SceneRoads\\road_1.xml");
        CommBus commBus = new CommBus();
        Car car1 = new Car(new ScenePoint(3375, 2525), 0);
        //Car car = new Car(new ScenePoint(3201,3001), 0);
        Car car2 = new Car(new ScenePoint(3250, 2900), 0);

        camera1 = new Camera(commBus, CommBusConnectorType.Sender, scene, car1);
        camera2 = new Camera(commBus, CommBusConnectorType.Sender, scene, car2);
    }

    @Test
    public void getClosestSign() throws Exception {
        camera1.doWork();
        camera2.doWork();
        Assert.assertEquals(DirectionSign.DirectionType.ROUNDABOUT, camera1.getClosestSign().getObjectType());
        Assert.assertEquals(SpeedSign.SpeedSignType.LIMIT_40, camera2.getClosestSign().getObjectType());
    }


    public void getLaneDistance() throws Exception {
        //camera1.doWork();
       // Assert.assertEquals("", camera.getLaneDistance());
    }

}