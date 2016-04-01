/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultrasonicsensor;

import java.util.ArrayList;

/**
 *
 * @author András
 */
public class Sonar implements IUltrasonic{

    
    private int viewDis = 5;
    private double fov;
    private int startAngle;
    private Pos currPosition;
    ArrayList<Pos> viewableObjs;
    public Sonar(double fov,int startAngle, Pos currCarPos)
    {
        this.fov = fov;
        this.startAngle = startAngle;
        this.currPosition = currCarPos;
        this.viewableObjs = new ArrayList<>();
    }
    
    public ArrayList<Pos> getViewableObjs(){
        return viewableObjs;
    }
    public Pos getCurrPos(){
        return currPosition;
    }
    public Pos getSonarPos() {
        return currPosition;
    }
    public int getSonarViewDis() {
        return viewDis;
    }
    public double getFov() {
        return fov;
    }
    public double getNearestObjectDistance(ArrayList<Pos> viewableObjList,Pos currPos) {
        
        viewableObjs = viewableObjList;
        currPosition = currPos;
        Pos closestObj;
        double closestObjDistance = -1;
        if(viewableObjs != null && viewableObjs.size() > 0){
            closestObj = viewableObjs.get(0);
            closestObjDistance = getDistance(currPosition, viewableObjs.get(0));
            for (int i = 1; i < viewableObjs.size() ; i++) {
                double dis = getDistance(currPosition, viewableObjs.get(i));
                if(dis < closestObjDistance)
                {
                    closestObj = viewableObjs.get(i);
                    closestObjDistance = dis;
                }
            }
            return closestObjDistance;
        }
        else{
            return closestObjDistance;
        }
    }
    private double getDistance(Pos ourPos,Pos objPos){
        double x = Math.pow(ourPos.getPosX() - objPos.getPosX(),2);
        double y = Math.pow(ourPos.getPosY() - objPos.getPosY(),2);
        double distance = Math.sqrt(x+y);
        return distance;
    }
    public int getStartAngle() {
        return startAngle;
    }

    
}
