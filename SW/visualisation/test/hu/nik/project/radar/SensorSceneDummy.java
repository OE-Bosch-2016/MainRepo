package hu.nik.project.radar;

import hu.nik.project.environment.ISensorScene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.environment.objects.SceneObjectException;

import java.util.ArrayList;

/**
 * Created by secured on 2016. 04. 21..
 */
public class SensorSceneDummy implements ISensorScene {

    Car carOne;
    Car carTwo;

    private int callCounter = 0; //tells, which arraylist to be returned

    public SensorSceneDummy() {
        try {
            carOne = new Car(new ScenePoint(10, 10), 5);
            carTwo = new Car(new ScenePoint(100, 100), 10);
        } catch (SceneObjectException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SceneObject> getVisibleSceneObjects(ScenePoint observerBase, int observerRotation, int viewAngle) {
        return null;
    }

    public ArrayList<SceneObject> getVisibleSceneObjects(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance) {
        if (callCounter == 0) {
            callCounter++;
            return null;
        } else if (callCounter == 1) {
            return getCarsFirstCycle();
        } else if (callCounter == 2) {
            return getCarsSecondCycle();
        } else if (callCounter == 3)
            return getCarsThirdCycle();
        else if (callCounter == 4) {
            return getCarsFourthCycle();
        } else
            return null;
    }

    private ArrayList<SceneObject> getCarsFirstCycle() {

        ArrayList<SceneObject> cars = new ArrayList<SceneObject>();
        cars.add(carOne);
        cars.add(carTwo);

        callCounter++;

        return cars;
    }

    //changing the values of car 1 and 2, like they were moving
    private ArrayList<SceneObject> getCarsSecondCycle() {
        ArrayList<SceneObject> cars = new ArrayList<SceneObject>();
        cars.add(carOne);
        cars.add(carTwo);

        callCounter++;

        return cars;
    }

    private ArrayList<SceneObject> getCarsThirdCycle() {
        ArrayList<SceneObject> cars = new ArrayList<SceneObject>();
        try {
            Car newCar = new Car(new ScenePoint(34, 39), 20);
            cars.add(newCar);
        } catch (SceneObjectException e) {
            e.printStackTrace();
        }

        cars.add(carOne);


        callCounter++;

        return cars;

    }

    private ArrayList<SceneObject> getCarsFourthCycle() {
        callCounter = 0;
        return null;
    }


}
