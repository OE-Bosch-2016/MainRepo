package acc;

/**
 *
 * @author Laci
 */
public class ACC implements IACC {

    private final int minFollowingDistance = 20; //in meters
    private final int maxSpeed = 180; //in km/h

    private int pedal;

    @Override
    public int getPedal() {
        return pedal;
    }

    public ACC() {
        pedal = 0;
    }

    @Override
    public void PedalState(int wheelStateInDegrees, boolean ACCState, int currentSpeed, int nearestObstacleDistance) {
        if (ACCState) {//ACC is on
            if ((currentSpeed <= maxSpeed - Math.abs(wheelStateInDegrees * 2)) && (nearestObstacleDistance > minFollowingDistance)) {//Should we accelerate?
                //throttling
                pedal = 1;
                System.out.println("1");
            } else if ((nearestObstacleDistance <= minFollowingDistance) || (currentSpeed > maxSpeed - Math.abs(wheelStateInDegrees * 2))) {//Should we brake?
                //breaking
                pedal = -1;
                System.out.println("-1");
            } else {
                //do nothing
                pedal = 0;
                System.out.println("0");
            }
        }else{
            //do nothing
            pedal = 0;
            System.out.println("0");
        }
    }
}
