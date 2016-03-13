import Interfaces.IDriverInputEngine;
import Interfaces.IDriverInputWheel;
import Listeners.OnBreakSteeringWheelListener;
import Listeners.OnGasListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class DriverInput implements IDriverInputWheel, IDriverInputEngine {

    private OnBreakSteeringWheelListener _breakSteeringWheelListener;
    private OnGasListener _gasListener;

    public DriverInput(){
    }

    public void setWheelAngle(float degree){
        if(_breakSteeringWheelListener!=null){
            _breakSteeringWheelListener.steeringWheelAngleChanged(degree);
        }else{
            throw new NullPointerException("BreakStearingWheelListener is null!");
        }

    }

    public void PushGas(){
        if(_gasListener!=null){
            _gasListener.gasPushed();
        }else{
            throw new NullPointerException("GasListner is null");
        }
    }

    public void PushBreak(){
        if(_breakSteeringWheelListener!=null){
            _breakSteeringWheelListener.breakPushed();
        }
        else{
            throw new NullPointerException("BreakStearingWheelListener is null!");
        }

    }

    //@Override
    public void addBreakSteeringWheelListener(OnBreakSteeringWheelListener listener) {
        _breakSteeringWheelListener=listener;
    }

    public void addGasListener(OnGasListener listener) {
        _gasListener=listener;
    }
}
