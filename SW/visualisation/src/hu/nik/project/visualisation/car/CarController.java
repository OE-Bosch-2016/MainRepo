package hu.nik.project.visualisation.car;

import java.awt.event.KeyEvent;

/**
 * Created by haxxi on 2016.04.17..
 */
public class CarController {

    private static CarController mInstance;

    // Car pedals
    private float gas = 0;
    private float steeringWheel = 0; //+- 180

    private boolean gasPressed;
    private boolean shuntPressed;
    private boolean leftRotate;
    private boolean rightRotate;

    private boolean handle;

    public static CarController newInstance() {
        if (mInstance == null)
            mInstance = new CarController();

        return mInstance;
    }

    public void keyEvent(KeyEvent e, Car car) {

        handle = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            turnRight(car);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            turnLeft(car);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            goAhead(car);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shunt(car);
        }
    }

    public void goAhead(Car car) {
        gasPressed = true;
        gas += 0.1;
        car.move(gas);

        if (!handle) {
            handle = true;
            if (leftRotate)
                turnLeft(car);
            else if (rightRotate)
                turnRight(car);
        }
    }

    public void turnLeft(Car car) {
        leftRotate = true;
        //if (steeringWheel > -180)
            steeringWheel -= 5;

        car.rotation(steeringWheel);

        if (!handle) {
            handle = true;
            if (gasPressed)
                goAhead(car);
            else if (shuntPressed)
                shunt(car);
        }
    }

    public void turnRight(Car car) {
        rightRotate = true;
        //if (steeringWheel < 180)
            steeringWheel += 5;

        car.rotation(steeringWheel);

        if (!handle) {
            handle = true;
            if (gasPressed)
                goAhead(car);
            else if (shuntPressed)
                shunt(car);
        }
    }

    public void shunt(Car car) {
        shuntPressed = true;
        gas -= 0.3;
        car.move(gas);

        if (!handle) {
            handle = true;
            if (leftRotate)
                turnLeft(car);
            else if (rightRotate)
                turnRight(car);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            gasPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightRotate = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftRotate = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shuntPressed = false;
        }
    }

    public void engineBrake(Car car) {
        if (gas > 0) {
            gas -= 0.09;
            car.move(gas);
        }
    }

//    public void steeringWheelStabilizator(Car car) {
//        if (steeringWheel > -10 && steeringWheel < 0 || steeringWheel < 10 && steeringWheel > 0)
//            steeringWheel = 0;
//        else {
//            if (steeringWheel < 0)
//                steeringWheel += 10;
//            if (steeringWheel > 0)
//                steeringWheel -= 10;
//        }
//    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public boolean isGasPressed() {
        return gasPressed;
    }

    public boolean isLeftRotate() {
        return leftRotate;
    }

    public boolean isRightRotate() {
        return rightRotate;
    }
}
