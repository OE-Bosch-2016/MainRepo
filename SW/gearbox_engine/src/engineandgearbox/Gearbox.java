package engineandgearbox;

/**
 *
 * @author András & Gergő
 */
public class Gearbox implements IGearbox {

    private int stage;

    Gearbox(int gearLever) {
        stage = gearLever;
    }

    @Override
    public void operateGearbox(int gearLever, double rpm) {
        switch (gearLever) {
            case 1:
                if (stage > 0 && stage < 5 && rpm > 3500) {
                    stage++;
                } else if (stage > 2 && rpm < 1500) {
                    stage--;
                }
                if (stage < 1) {
                    stage = 1;
                }
                break;
            case 0:
                stage = 0;
                break;
            default:
                stage = -1;
                break;
        }
    }

    @Override
    public int getStage() {
        return stage;
    }
}
