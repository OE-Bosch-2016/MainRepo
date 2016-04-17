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
    public double[] getClosestDistance() {
        return closestDistance;
    }
    
    public Sonar[] getSonars() {
        return sonars;
    }
    

    public UltrasonicModul(CommBus commBus, CommBusConnectorType commBusConnectorType, ScenePoint currPos) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        closestDistance = new double[8];
        sonars = new Sonar[8];
        currPosition = currPos;
        
        for (int i = 0; i < sonars.length; i++) {
            sonars[i] = new Sonar(fov, startAngle, currPos);
            startAngle += fov;
        }
    }

    @Override
    public void commBusDataArrived() {
    }

    public void SendToBus() {
        UltrasonicMessagePackage umsg = new UltrasonicMessagePackage(closestDistance);
        boolean sent = false;
        while (!sent) {
            try {
                if (commBusConnector.send(umsg)) {
                    sent = true;
                }
            } catch (CommBusException e) {
                break;
            }
        }
    }
    public void getNearestObjectDistances(ScenePoint currPos)
    {
        ArrayList ures = new ArrayList();
        for (int i = 0; i < sonars.length; i++) {
            Sonar son = sonars[i];
            closestDistance[i] = son.getNearestObjectDistance(ures, currPos);
        }
        
//        for (int i = 0; i < sonars.length; i++) {
//            Sonar son = sonars[i];
//            closestDistance[i] = son.getNearestObjectDistance(scene.getVisibleSceneObjects(son.getSonarPos(), son.getStartAngle(), (int) son.getFov(), son.getSonarViewDis()), currPos);
//            
//        }
    }

}
