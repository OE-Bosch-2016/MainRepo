/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.nik.project.ultrasonicsensor;

import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.Car.CarType;
import hu.nik.project.environment.objects.DirectionSign;
import hu.nik.project.environment.objects.Misc;
import hu.nik.project.environment.objects.ParkingSign;
import hu.nik.project.environment.objects.PrioritySign;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.environment.objects.SpeedSign;
import hu.nik.project.environment.objects.Tree;
import java.util.ArrayList;

/**
 *
 * @author András
 */
public class Sonar implements IUltrasonic {

    private int carWidth = 100;
    private int carHeight = 240;
    private int viewDis = 500;
    private double fov;
    private int startAngle;
    private ScenePoint currPosition;
    private ScenePoint sonarPosition;
    ArrayList<SceneObject> filteredObjs;

    public Sonar(double fov, int startAngle, ScenePoint currCarPos) {
        this.fov = fov;
        this.startAngle = startAngle;
        this.currPosition = currCarPos;
        this.filteredObjs = new ArrayList<>();
    }

    public ArrayList<SceneObject> getFilteredObjs() {
        return filteredObjs;
    }

    public ScenePoint getCurrPos() {
        return currPosition;
    }

    public void setCurrPosition(ScenePoint currPosition) {
        this.currPosition = currPosition;
    }
    
    public ScenePoint getSonarPos() {
        if (startAngle < 180) {
            sonarPosition = new ScenePoint(currPosition.getX() + (carWidth / 2), currPosition.getY()); //front bumper
            return sonarPosition;
        } else {
            sonarPosition = new ScenePoint(currPosition.getX() + (carWidth / 2), currPosition.getY() + carHeight); //rear bumper
            return sonarPosition;
        }
    }

    public int getSonarViewDis() {
        return viewDis;
    }

    public double getFov() {
        return fov;
    }

    public double getNearestObjectDistance(ArrayList<SceneObject> viewableObjList, ScenePoint currSonarPos) {

        filteredObjs = filterObjects(viewableObjList);
        
        SceneObject closestObj;
        double closestObjDistance = -1;

        if (filteredObjs != null && filteredObjs.size() > 0) {
            closestObj = filteredObjs.get(0);  //first object
            closestObjDistance = getDistance(currSonarPos, filteredObjs.get(0).getBasePosition()); //first corner checking
            ObjPositions o = new ObjPositions(new Pos(closestObj.getBasePosition().getX(),closestObj.getBasePosition().getY()), 80, 80); //4 corners
            for (int i = 1; i < o.getPositions().length; i++) //other corners checking
            {
                double cornerPointDistance = getDistance(currSonarPos, new ScenePoint(o.getPositions()[i].getPosX(),o.getPositions()[i].getPosY()));
                if(cornerPointDistance < closestObjDistance)
                {
                    closestObjDistance = cornerPointDistance;
                }
            }
            for (int i = 1; i < filteredObjs.size(); i++)  //other objects
            {
                o = new ObjPositions(new Pos(filteredObjs.get(i).getBasePosition().getX(),filteredObjs.get(i).getBasePosition().getY()), 80, 80); //4corner
                for (int j = 0; j < o.getPositions().length; j++) {
                    double cornerPointDistance = getDistance(currSonarPos, new ScenePoint(o.getPositions()[j].getPosX(),o.getPositions()[j].getPosY()));
                    if (cornerPointDistance < closestObjDistance) {
                    closestObj = filteredObjs.get(i);
                    closestObjDistance = cornerPointDistance; 
                }
                }
                
            }
            return closestObjDistance;
        } else {
            return closestObjDistance;
        }
    }

    private double getDistance(ScenePoint sonPos, ScenePoint objPos) {
        double x = Math.pow(sonPos.getX() - objPos.getX(), 2);
        double y = Math.pow(sonPos.getY() - objPos.getY(), 2);
        double distance = Math.sqrt(x + y);
        return distance;
    }

    public int getStartAngle() {
        return startAngle;
    }

    private ArrayList<SceneObject> filterObjects(ArrayList<SceneObject> viewableObjList) {
        ArrayList<SceneObject> filteredObjList = new ArrayList<>();
        for (int i = 0; i < viewableObjList.size(); i++) {
            if (viewableObjList.get(i).getObjectType() == Tree.TreeType.TREE_TOP_VIEW
                    || viewableObjList.get(i).getObjectType() == DirectionSign.DirectionType.FORWARD
                    || viewableObjList.get(i).getObjectType() == DirectionSign.DirectionType.FORWARD_LEFT
                    || viewableObjList.get(i).getObjectType() == DirectionSign.DirectionType.FORWARD_RIGHT
                    || viewableObjList.get(i).getObjectType() == DirectionSign.DirectionType.LEFT
                    || viewableObjList.get(i).getObjectType() == DirectionSign.DirectionType.RIGHT
                    || viewableObjList.get(i).getObjectType() == DirectionSign.DirectionType.ROUNDABOUT
                    || viewableObjList.get(i).getObjectType() == ParkingSign.ParkingSignType.PARKING_BOLLARD
                    || viewableObjList.get(i).getObjectType() == ParkingSign.ParkingSignType.PARKING_LEFT
                    || viewableObjList.get(i).getObjectType() == ParkingSign.ParkingSignType.PARKING_RIGHT
                    || viewableObjList.get(i).getObjectType() == PrioritySign.PrioritySignType.GIVEAWAY
                    || viewableObjList.get(i).getObjectType() == PrioritySign.PrioritySignType.PRIORITY_ROAD
                    || viewableObjList.get(i).getObjectType() == PrioritySign.PrioritySignType.STOP
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_10
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_70
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_100
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_20
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_40
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_50
                    || viewableObjList.get(i).getObjectType() == SpeedSign.SpeedSignType.LIMIT_90) {
                filteredObjList.add(viewableObjList.get(i));
            }
        }
        return filteredObjList;
    }

}
