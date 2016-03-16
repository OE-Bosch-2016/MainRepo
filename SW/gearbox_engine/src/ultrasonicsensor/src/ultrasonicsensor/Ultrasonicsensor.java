/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultrasonicsensor;

import java.util.List;

/**
 *
 * @author Patrik
 */
public class Ultrasonicsensor {

    private final double fov = 45; //in degrees
    private double x; //sensor position ON CAR
    private double y; //sensor position ON CAR
    private double startAngle; //sensor facing ON CAR
    private double c; //distance from center of car

    /**
     * @return the fov
     */
    public double getFov() {
        return fov;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @return the startAngle
     */
    public double getStartAngle() {
        return startAngle;
    }

    //start angle must be between -180 and +180 where 0° is the facing direction of the vehicle
    public Ultrasonicsensor(double x, double y, double startAngle) {
        this.x = x;
        this.y = y;
        this.startAngle = startAngle;
        c = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getNearestObjectDistance(double carX, double carY, double carFacing, List<Object> objects) {
        //sensor position in environment
        double sensorX = carX + mathfügvény()  + másikmathfüggvény() * ;
        return 0;
    }
}
