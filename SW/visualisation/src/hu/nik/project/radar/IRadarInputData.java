package hu.nik.project.radar;

import hu.nik.project.utils.Vector2D;
import java.util.ArrayList;


/**
 * Created by secured on 2016. 03. 19..
 */

//this will be a mocked interface, representing the incoming data
public interface IRadarInputData {
    Vector2D getOurCurrentPosition();
    double getOurCurrentSpeed();
    ArrayList<Vector2D> getViewableObjectList();
}
