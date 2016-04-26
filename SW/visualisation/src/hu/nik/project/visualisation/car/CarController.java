package hu.nik.project.visualisation.car;

import hu.nik.project.utils.Vector2D;

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
    private Car car;

    private boolean handle;

    public static CarController newInstance() {
        if (mInstance == null)
            mInstance = new CarController();

        return mInstance;
    }

    public void keyEvent(KeyEvent e) {

        if(car != null) {
            handle = false;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && gas != 0) {
                turnRight();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && gas != 0) {
                turnLeft();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                goAhead();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                shunt();
            }
        }
    }

    public void goAhead() {
        gasPressed = true;
        gas += 0.1;
        car.move(gas);

        if (!handle) {
            handle = true;
            if (leftRotate)
                turnLeft();
            else if (rightRotate)
                turnRight();
        }
    }

    public void turnLeft() {
        leftRotate = true;
        //if (steeringWheel > -180)
        steeringWheel -= 5;

        car.rotation(steeringWheel);

        if (!handle) {
            handle = true;
            if (gasPressed)
                goAhead();
            else if (shuntPressed)
                shunt();
        }
    }

    public void turnRight() {
        rightRotate = true;
        //if (steeringWheel < 180)
        steeringWheel += 5;

        car.rotation(steeringWheel);

        if (!handle) {
            handle = true;
            if (gasPressed)
                goAhead();
            else if (shuntPressed)
                shunt();
        }
    }

    public void shunt() {
        shuntPressed = true;
        gas -= 0.3;
        car.move(gas);

        if (!handle) {
            handle = true;
            if (leftRotate)
                turnLeft();
            else if (rightRotate)
                turnRight();
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
            if (gas - 0.09 < 0)
                gas = 0;
            else
                gas -= 0.09;
            car.move(gas);
        } else {
            if (gas + 0.09 > 0)
                gas = 0;
            else
                gas += 0.09;
            car.move(gas);
        }
    }

    public void autonomousController(int carAngle, float speed){


    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public boolean isGasPressed() {
        return gasPressed;
    }

    public float getSteeringWheel() {
        return steeringWheel;
    }

    public float getGas() {
        return gas;
    }

    public boolean isLeftRotate() {
        return leftRotate;
    }

    public boolean isRightRotate() {
        return rightRotate;
    }

    public Vector2D getCarPosition(){
        return car.getPosition();
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    public void setCar(Car car) {
        this.car = car;
    }
}
