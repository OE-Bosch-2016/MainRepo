package Listeners;

/**
 * Created by secured on 2016. 03. 06..
 */
public interface OnBreakSteeringWheelListener {
    Boolean breakPushed();
    void steeringWheelAngleChanged(float angle);
}
