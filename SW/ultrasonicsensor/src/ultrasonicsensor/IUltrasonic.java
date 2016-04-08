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
public interface IUltrasonic {
    Pos getSonarPos();
    int getSonarViewDis();
    double getFov();
    int getStartAngle();
    
    double getNearestObjectDistance(ArrayList<Pos> viewAbleObjs,Pos currPos);
}
