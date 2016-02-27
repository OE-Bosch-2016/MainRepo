package egineandgearbox;

/**
 * @author Patrik
 */
public interface IGearbox {
    public void operateGearbox(int gearLever, double rpm);
    public int getStage();
}
