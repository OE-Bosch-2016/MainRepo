package hu.nik.project.visualisation.car;

import hu.nik.project.visualisation.interfaces.IDriverInputEngine;
import hu.nik.project.visualisation.interfaces.IDriverInputGearBox;
import hu.nik.project.visualisation.interfaces.IDriverInputWheel;
import hu.nik.project.visualisation.interfaces.OnBreakSteeringWheelListener;
import hu.nik.project.visualisation.interfaces.OnGasListener;
import hu.nik.project.visualisation.interfaces.OnGearPositionListener;
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
