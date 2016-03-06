package Light;

/**
 * Created by Perec on 2016.03.06..
 */

public class Lighter implements ILightable {

    private boolean turned;

    private int horizontalDirection;

    private int verticalDirection;

    private TypeOfLight type;

    public void setReadyToWork(boolean readyToWork) {
        this.readyToWork = readyToWork;
        bugReport("notReady!");
    }

    private boolean readyToWork;

    public Lighter() {

        type = getTypeOfLamp();

    }

    public Lighter(String bugReport) {

        type = getTypeOfLamp();
        bugReport(bugReport);
    }


    @Override
    public boolean isTurned() {
        return turned;
    }

    @Override
    public TypeOfLight getTypeOfLamp() {
        return TypeOfLight.Lamp;
    }

    @Override
    public String bugReport(String Message) {
        return Message+" Changed:"+ new java.util.Date().toString() ;
    }
}
