/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class DriverInput implements IDriverInputWheel {

    OnBreakSteeringWheelListener _breakSteeringWheelListener;

    public DriverInput(){
    }

    public void setWheelAngle(float degree){
        if(_breakSteeringWheelListener!=null){
            _breakSteeringWheelListener.steeringWheelAngleChanged(degree);
        }else{
            throw new NullPointerException("BreakStearingWheelListener is null!");
        }

    }

    @Override
    public void addBreakSteeringWheelListener(OnBreakSteeringWheelListener listener) {
        _breakSteeringWheelListener=listener;
    }
}
