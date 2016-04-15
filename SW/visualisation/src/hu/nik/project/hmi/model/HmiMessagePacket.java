package hu.nik.project.hmi.model;

/**
 * Created by haxxi on 2016.04.15..
 */
public class HmiMessagePacket {

    private float currentKm;
    private float currentWheelTurn;
    private int currentGearboxPosition;

    public HmiMessagePacket(float currentKm, float currentWheelTurn) {
        this.currentKm = currentKm;
        this.currentWheelTurn = currentWheelTurn;
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public float getCurrentKm() {
        return currentKm;
    }

    public float getCurrentWheelTurn() {
        return currentWheelTurn;
    }

    public int getCurrentGearboxPosition() {
        return currentGearboxPosition;
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setCurrentGearboxPosition(int currentGearboxPosition) {
        this.currentGearboxPosition = currentGearboxPosition;
    }
}
