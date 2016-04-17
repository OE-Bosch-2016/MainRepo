package hu.nik.project.radar;

import hu.nik.project.environment.ScenePoint;


/**
 * Created by secured on 2016. 03. 19..
 */

//this is the real output interface of modul "RADAR"
public interface IRadarSensor {
    ScenePoint getRadarPosition();
    Integer getRadarViewDistance();
    Float getAngelOfSight();
    RadarMessagePacket getDetectedObjsRelativeSpeedAndDistance(double currentSpeed,int obsererRotation);
}
