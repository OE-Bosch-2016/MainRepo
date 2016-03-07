package engineandgearbox;

/*
 * @author Laci & Patrik
 */
public class Engine implements IEngine {

    final int maxRpm = 4000;
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
        if (gearState > lastGearState) { //shift up
            shiftUp(gearState);
        } else if(gearState < lastGearState){ //shift down
            shiftDown(gearState);
        }
            calculateRpm(throttle);
    }

    private void shiftUp(int gearState) {
        if (gearState == 0) {
            shiftToN(); //shift from R to N
        } else if (lastGearState == 0) {
            shiftFromN(gearState); //shift to 1 from N
        } else {
            rpm -= 1500;
        }
        lastGearState = gearState;
    }

    private void shiftDown(int gearState) {
        if (gearState == 0) {
            shiftToN(); //shift to N
        } else if (lastGearState == 0) {
            shiftFromN(gearState); //shift to R
        } else {
            rpm += 500;
        }
        lastGearState = gearState;
    }

    private void shiftFromN(int gearState) {
        rpm -= 1500;
    }

    private void shiftToN() {
    }

    private void calculateRpm(boolean throttle) {
        double time = 5 * (Math.pow(Math.E, rpm / 1600) - 1);
        if (throttle) {
            time += 0.5;
        } else {
            time -= 0.5;
        }
        if (time < 0) {
            time = 0;
        }
        if (time >= 0) {
            rpm = (Math.log((time + 5) / 5)) * 1600;
            if (rpm > maxRpm) {
                rpm = maxRpm;
            } else if (rpm < minRpm) {
                rpm = minRpm;
            }
        }
        calculateTorque();
    }

    private void calculateTorque() {
        switch (lastGearState) {
            case 0://now in N
                torque = 0;
                break;
            case -1://now in R
                torque = -1 * rpm / 10;
                break;
            case 1://now in 1
                torque = rpm / 10;
                break;
            default://now in > 1
                torque = rpm / 10 - lastGearState * 20;
                break;
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
