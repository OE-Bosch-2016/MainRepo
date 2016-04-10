package visualisation2.Car;

import visualisation2.Interfaces.IDriverInputEngine;
import visualisation2.Interfaces.IDriverInputGearBox;
import visualisation2.Interfaces.IDriverInputWheel;
import visualisation2.Interfaces.OnBreakSteeringWheelListener;
import visualisation2.Interfaces.OnGasListener;
import visualisation2.Interfaces.OnGearPositionListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class DriverInput implements IDriverInputWheel, IDriverInputEngine, IDriverInputGearBox {

    private OnBreakSteeringWheelListener _breakSteeringWheelListener;
    private OnGasListener _gasListener;
    private OnGearPositionListener _gearPositionListener;

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

    public void StartVehicle(){
        throw new NotImplementedException();
    }


    //@Override
    public void addBreakSteeringWheelListener(OnBreakSteeringWheelListener listener) {
        _breakSteeringWheelListener=listener;
    }

    public void addGasListener(OnGasListener listener) {
        _gasListener=listener;
    }

    public void addGearPositionListener(OnGearPositionListener listener) {
        _gearPositionListener = listener;
    }
}
