package Interfaces;


import Utils.Position;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by secured on 2016. 03. 19..
 */

//this will be a mocked interface, representing the incoming data
public interface IRadarInputData {
    Position getOurCurrentPosition();
    Double getOurCurrentSpeed();
    ArrayList<Position> getViewableObjectList();
}
