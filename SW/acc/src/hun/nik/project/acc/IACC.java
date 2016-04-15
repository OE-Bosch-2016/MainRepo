/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hun.nik.project.acc;

/**
 *
 * @author Laci
 */
public interface IACC {
    int getPedal();
    void PedalState(int wheelStateInDegrees, boolean ACCState, int currentSpeed, int nearestObstacleDistance);
}
