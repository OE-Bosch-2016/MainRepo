package hu.nik.project.visualisation.interfaces;

public interface OnBreakSteeringWheelListener {
    Boolean breakPushed();
    void steeringWheelAngleChanged(double angle);
}
