package Interfaces;

import Light.TypeOfLight;

/**
 * Created by Perec on 2016.03.06..
 */
public interface ILightable {

    boolean isTurned();

    TypeOfLight getTypeOfLamp();

    String bugReport(String Message);

}