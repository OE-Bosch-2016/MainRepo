package engineandgearbox;

/**
 *
 * @author Laci
 */
public interface IEngine {

    void operateEngine(int gearState, boolean throttle);

    double getRpm();

    double getTorque();
}
