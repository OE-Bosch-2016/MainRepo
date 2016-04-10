package radar;
import utils.Vector2D;

import java.util.ArrayList;


/**
 * Created by secured on 2016. 03. 19..
 */

//this is the real output interface of modul "RADAR"
public interface IRadarData {
    Vector2D getRadarPosition();
    Integer getRadarViewDistance();
    Float getAngelOfSight();


    ArrayList<SpeedAndDistanceObj> getDetectedObjsRelativeSpeedAndDistance();
}
