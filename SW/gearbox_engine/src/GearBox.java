
package engineandgearbox;

/**
 *
 * @author András
 */
public class GearBox implements IGearBox{

    private int stage;

    public GearBox() {
        stage = 0;
    }
    
    public void operateGearbox(int gearLever, double rpm) {
    }


    public int getStage() {
        return stage;
    }
    
    
}
