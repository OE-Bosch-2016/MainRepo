package egineandgearbox;

/**
 *
 * @author Laci
 */
public class Engine implements IEngine{

    private double rpm;
    private double torque;

    Engine(int gearState) {
        rpm = 0;
        torque = 0;
    }

    @Override
    public void operateEngine(int gearState, boolean throttle) {
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
