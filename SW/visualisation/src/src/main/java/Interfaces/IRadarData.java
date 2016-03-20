package Interfaces;

import javax.swing.text.Position;
import java.util.ArrayList;

/**
 * Created by secured on 2016. 03. 19..
 */

//this is the real output interface of modul "RADAR"
public interface IRadarData {
    Position getRadarPosition();
    Integer getRadarViewDistance();
    Float getAngelOfSight();

    Integer getRelativeSpeedOfDetectedObject(Object detectedObject);
    Integer getDistanceOfDetectedObject(Object detectedObject);
}
