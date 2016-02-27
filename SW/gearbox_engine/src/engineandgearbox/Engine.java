package engineandgearbox;

/**
 *
 * @author Laci & Patrik
 */
public class Engine implements IEngine {

    final int maxRpm = 6000;
    final int minRpm = 500;
    private double rpm;
    private double torque;
    private int lastGearState;

    Engine(int gearState) {
        rpm = 0;
        torque = 0;
        lastGearState = gearState;
    }

    @Override
    public void operateEngine(int gearState, boolean throttle) {
        if (gearState == lastGearState) { //nincs váltás
            calculateRpm(throttle);
        } else if (gearState > lastGearState) { //fel váltás
            shiftUp(gearState);
        } else { //le váltás
            shiftDown(gearState);
        }
    }

    private void shiftUp(int gearState) {
        if (gearState == 0) {
            shiftToN(); //üresbe tesszük hátramenetből
        } else if (lastGearState == 0) {
            shiftFromN(gearState); //egyesbe tesszük
        } else {
            rpm -= 1500;
        }
        lastGearState = gearState;
    }

    private void shiftDown(int gearState) {
        if (gearState == 0) {
            shiftToN(); //üresbe tesszük előremenetből
        } else if (lastGearState == 0) {
            shiftFromN(gearState); //hátramenetbe tesszük
        } else {
            rpm += 500;
        }
        lastGearState = gearState;
    }

    private void shiftFromN(int gearState) {
    }

    private void shiftToN() {
    }

    private void calculateRpm(boolean throttle) {
        //double time = Math.cosh(rpm / 1600) - 5;
        double time = 5*(Math.pow(Math.E, rpm/1600)-1);
        if (throttle) {
            time += 1;
        } else {
            time -= 1;
        }
        if (time > 0) {
           // rpm = Math.log(time + 1 + Math.sqrt((time + 1) * (time + 1) - 1)) * 1600;
            rpm = (Math.log((time+5)/5))*1600;
            if (rpm > maxRpm) {
                rpm = maxRpm;
            } else if (rpm < minRpm) {
                rpm = minRpm;
            }
        }
        calculateTorque();
    }

    private void calculateTorque() {
        torque = (lastGearState - 1) * 6000 + rpm;
        if (lastGearState == 0) {
            torque = 0;
        }
    }

    @Override
    public double getRpm() {
        return rpm;
    }

    @Override
    public double getTorque() {
        return torque;
    }
}
