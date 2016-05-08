package hu.nik.project.framework.main;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.XMLParserException;
import hu.nik.project.environment.logger.Logger;
import hu.nik.project.environment.logger.LoggerException;
import hu.nik.project.environment.objects.Car;
import hu.nik.project.environment.objects.SceneObjectException;
import hu.nik.project.framework.BoschCar;

import hu.nik.project.visualisation.main.Top;

/**
 * Created by Róbert on 2016.04.12..
 *
 * The MAIN class of the project!!!!!!
 */
public class Main {

    static Logger programLogger;
    public static Scene scene;
    static BoschCar car;
    static Top top;

    public static void main(String[] args) throws LoggerException {
        // Initialize logger
        programLogger = new Logger("ProgramLog.log");


        // Create the Scene
        programLogger.log("Creating scene...");
        try {
            String xmlPath;

            if (args.length > 0)
                xmlPath = args[0];
            else
                xmlPath = "sceneroads\\road_1.xml";

            scene = new Scene(xmlPath);
            programLogger.log("XML parsing completed! Scene created!");
        }
        catch (XMLParserException e) {
            System.out.println("Error during the XML parsing: " + e.getMessage());
            programLogger.log("Error during the XML parsing: " + e.getMessage());
            System.exit(1);
        }

        // Create the car
        programLogger.log("Creating car...");
        try {
            car = new BoschCar(new ScenePoint(3250, 2500), 0);
            programLogger.log("Car created!");
            // Cars can be created and added to the scene here
            // programLogger.log("Adding car to the scene...");
            // scene.addCarToScene();
            // programLogger.log("Car added!");

        } catch (SceneObjectException e) {
            System.out.println("Error during the car creation: " + e.getMessage());
            programLogger.log("Error during the car creation: " + e.getMessage());
            System.exit(1);
        }

        // Add dummy stopped cars into the environment
        try {
            Car dummyCar = new Car(new ScenePoint(3100, 365), 90);
        } catch (SceneObjectException e) {
            System.out.println("Error during the car creation: " + e.getMessage());
            programLogger.log("Error during the car creation: " + e.getMessage());
        }
        scene.addDummyCarToScene(car);

        // Start the visualisation
        programLogger.log("Starting visualisation...");
        try {
            top = new Top("sceneroads\\road_1.png", car, scene.getSceneWidth(), scene.getSceneHeight());
        }catch (Exception e) {
            System.out.println("Error in visualisation: " + e.getMessage());
            programLogger.log("Error in visualisation: " + e.getMessage());
            System.exit(1);
        }
    }
}
