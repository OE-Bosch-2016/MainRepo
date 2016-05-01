package hu.nik.project.lka;

import hu.nik.project.camera.CameraMessagePackage;
import hu.nik.project.communication.*;
import hu.nik.project.environment.objects.SimpleRoad;
import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
import hu.nik.project.wheels.WheelsMessagePackage;

/**
 * Created by zhodvogner on 2016. ápr. 14.
 *
 * LKA - Lane Keeping Assistance module
 *
 * Developer: Máté, Újszászi (Team1)
 *
 */
public class Lka implements ICommBusDevice {

    private static final double HALF_TRACK_WIDTH = 87.5;
    private static final double ALLOWED_LANE_DISTANCE = 20;

    private CommBusConnector commBusConnector;
    private String lastErrorMessage;
    private double previousLaneDistance;
    private boolean enabled;

    public Lka(CommBus commBus, CommBusConnectorType commBusConnectorType) {
        commBusConnector = commBus.createConnector(this, commBusConnectorType);
        previousLaneDistance = HALF_TRACK_WIDTH;
        enabled = false;
        lastErrorMessage = "no messages";
    }

    @Override
    public void commBusDataArrived() {

        lastErrorMessage = "";

        Class dataType = commBusConnector.getDataType();

        if (dataType == DriverInputMessagePackage.class) {
            try {
                DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                enabled = data.lkaIsActive();
            } catch (CommBusException e) {
                lastErrorMessage = e.getMessage();
            }
        }

        if (dataType == CameraMessagePackage.class){
            try {

                // read data from the bus
                CameraMessagePackage data = (CameraMessagePackage)commBusConnector.receive();

                if (enabled) {

                    int requestedSteeringWheelAngle = 0; // no correction
                    int maximumSpeedForKeepingLane = 130; // no speed limit

                    // TODO: processing input messages and calculate PRECISE (EXACT) output values
                    //       it is possible only if we get relative car-angle rel lane-direction (from camera)

                    // Simplified solution:
                    if (data.LaneDistance > HALF_TRACK_WIDTH + ALLOWED_LANE_DISTANCE)
                        requestedSteeringWheelAngle = -5;
                    else
                        if (data.LaneDistance < HALF_TRACK_WIDTH - ALLOWED_LANE_DISTANCE)
                            requestedSteeringWheelAngle = +5;

                    // TODO: maximum speed limit calculation -> maximumSpeedForKeepingLane
                    //       it is possible only if we known what type of the next road segment
                    //       - BUT - currently we don't know it

                    // Simplified solution
                    double distanceDelta = Math.abs(previousLaneDistance - data.LaneDistance);
                    if (distanceDelta < HALF_TRACK_WIDTH) {
                        maximumSpeedForKeepingLane = (int)((1 - (distanceDelta / HALF_TRACK_WIDTH)) * 140);
                        if (maximumSpeedForKeepingLane < 40) maximumSpeedForKeepingLane = 40;
                    }
                    previousLaneDistance = data.LaneDistance;

                    // send response message onto the bus (if necessary)
                    commBusConnector.send(new LkaMessagePackage(requestedSteeringWheelAngle, maximumSpeedForKeepingLane));
                }
            } catch (CommBusException e) {
                lastErrorMessage = e.getMessage();
            }
        }
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    public boolean getEnabled() {
        return enabled;
    }

}
