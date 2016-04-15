/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.nik.project.ultrasonicsensor;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.SceneObject;
import java.util.ArrayList;

/**
 *
 * @author András
 */
public interface IUltrasonic {
    ScenePoint getSonarPos();
    int getSonarViewDis();
    double getFov();
    int getStartAngle();
    
    double getNearestObjectDistance(ArrayList<SceneObject> viewAbleObjs,ScenePoint currPos);
}
