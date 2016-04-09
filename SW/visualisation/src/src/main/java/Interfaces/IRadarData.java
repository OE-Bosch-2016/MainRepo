package Interfaces;
import Radar.MapObjectData;
import Utils.Position;

import java.util.ArrayList;


/**
 * Created by secured on 2016. 03. 19..
 */

//this is the real output interface of modul "RADAR"
public interface IRadarData {
    Position getRadarPosition();
    Integer getRadarViewDistance();
    Float getAngelOfSight();

    ArrayList<MapObjectData> getDetectedObjsRelativeSpeedAndDistance();
}
