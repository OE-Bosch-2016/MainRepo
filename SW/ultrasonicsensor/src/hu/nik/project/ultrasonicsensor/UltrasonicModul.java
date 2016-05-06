/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.nik.project.ultrasonicsensor;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;
import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import java.util.ArrayList;

/**
 *
 * @author András
 */
public class UltrasonicModul implements ICommBusDevice {

    private double[] closestDistance;
    private Sonar[] sonars;
    private ScenePoint currPosition;
    private int fov = 45;
    private int startAngle = 0;
    private CommBusConnector commBusConnector;
    private Scene scene;
    private Car car;

    public double[] getClosestDistance() {
        return closestDistance;
    }

    public Sonar[] getSonars() {
        return sonars;
    }

    public UltrasonicModul(CommBus commBus, CommBusConnectorType commBusConnectorType, Car car, Scene scene) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        closestDistance = new double[8];
        sonars = new Sonar[8];
        this.car = car;
        currPosition = car.getBasePosition();
        this.scene = scene;
        for (int i = 0; i < sonars.length; i++) {
            sonars[i] = new Sonar(fov, startAngle, car.getBasePosition());
            startAngle += fov;
        }
    }

    @Override
    public void commBusDataArrived() {
    }
    
    public void doWork() {
        getNearestObjectDistances(car.getBasePosition(), car.getRotation());
        UltrasonicMessagePackage umsg = new UltrasonicMessagePackage(closestDistance);
        try {
            if (commBusConnector.send(umsg)) {
            }
        } catch (CommBusException e) {
            e.printStackTrace();
        }
    }

    public void getNearestObjectDistances(ScenePoint currPos, int rotation) {
        startAngle = 0;
        for (int i = 0; i < sonars.length; i++) {
            sonars[i] = new Sonar(fov, startAngle, currPos);
            startAngle += fov;
        }

        for (int i = 0; i < sonars.length; i++) {
            Sonar son = sonars[i];
            ScenePoint newSonarPos = ScenePoint.rotatePointAroundPoint(currPos, son.getSonarPos(), rotation);
//            System.out.println(son.getSonarPos() + "; " + newSonarPos + " ;" + son.getStartAngle() + "; " + (son.getStartAngle() + rotation));
            closestDistance[i] = son.getNearestObjectDistance(scene.getVisibleSceneObjects(newSonarPos, (son.getStartAngle() + rotation), (int) son.getFov(), son.getSonarViewDis()), newSonarPos);

        }

    }

}
