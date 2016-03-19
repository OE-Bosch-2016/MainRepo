package Interfaces;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by secured on 2016. 03. 19..
 */

//this will be a mocked interface, representing the incoming data
public interface IRadarInputData {
    Position getOurCurrentPosition();
    Position getDetectedObjectPosition();
    ArrayList<Objects> getViewableObjectList();
}
