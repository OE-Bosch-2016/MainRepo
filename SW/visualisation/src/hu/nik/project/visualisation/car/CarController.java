package hu.nik.project.visualisation.car;

import java.awt.event.KeyEvent;

/**
 * Created by haxxi on 2016.04.17..
 */
public class CarController {

    private static CarController mInstance;

    // Car pedals
    private float gas = 0;
    private float steeringWheel = 0;
    private boolean gasPressed;

    public static CarController newInstace() {
        if (mInstance == null)
            mInstance = new CarController();

        return mInstance;
    }

    public void keyEvent(KeyEvent e, Car car) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            gas += 0.1;
            gasPressed = true;
            car.move(gas);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (gas > 0)
                gas -= 0.3;
            car.move(gas);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            gasPressed = false;
        }
    }

    public void engineBrake(Car car) {
        if (gas > 0) {
            gas -= 0.06;
            car.move(gas);
        }
    }

    // Getter ----------------------------------------------------------------------------------------------------------
    public boolean isGasPressed() {
        return gasPressed;
    }
}
