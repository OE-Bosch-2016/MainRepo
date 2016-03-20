package hu.nik.project.environment;

import hu.nik.project.environment.logger.Logger;
import hu.nik.project.environment.logger.LoggerException;
import hu.nik.project.environment.objects.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Róbert on 2016.02.24..
 *
 * Main worker class of the environment
 */
public class Scene {

    private int sceneHeight;
    private int sceneWidth;
    private String xmlPath;

    private ArrayList<SceneObject> sceneObjects;
    private Logger logger;

    public Scene(String xmlPath) throws XMLParserException {

        if (xmlPath.equals("")) throw new XMLParserException("XML file needed!");

        this.xmlPath = xmlPath;
        try {
            logger = new Logger("XML-parse.log");
        } catch (LoggerException e) {
            // No logging happens
        }

        try {
            parseXML();
        } catch (XMLParserException e) {
            throw new XMLParserException("Error during the XML parse: " + e.getMessage());
        }
    }

    public int getSceneWidth() {
        return sceneWidth;
    }
    public int getSceneHeight() {
        return sceneHeight;
    }

    public ArrayList<SceneObject> getSceneObjects() { return sceneObjects; }

    public SceneObject getSceneObjectByPosition(ScenePoint basePosition) {
        for (SceneObject s : sceneObjects) {
            if (s.getBasePosition().getX() == basePosition.getX() && s.getBasePosition().getY() == basePosition.getY())
                return s;
        }
        return null;
    }

    private void createSceneObject(int id, String objectName, ScenePoint basePosition, int rotation) {

        int size = sceneObjects.size();
        try {
            switch (objectName) {
                case "data/misc/crosswalks/crosswalk_5_stripes.svg":
                    sceneObjects.add(new CrossWalk(basePosition, rotation, CrossWalk.CrossWalkType.CROSSWALK_5));
                    break;
                case "data/misc/parking/parking_0.svg":
                    sceneObjects.add(new Parking(basePosition, rotation, Parking.ParkingType.PARKING_0));
                    break;
                case "data/misc/parking/parking_90.svg":
                    sceneObjects.add(new Parking(basePosition, rotation, Parking.ParkingType.PARKING_90));
                    break;
                case "data/misc/parking/parking_bollard.pix":
                    sceneObjects.add(new ParkingSign(basePosition, rotation, ParkingSign.ParkingSignType.PARKING_BOLLARD));
                    break;
                case "data/misc/people/man03.pix":
                    sceneObjects.add(new People(basePosition, rotation, People.PeopleType.MAN));
                    break;
                case "data/misc/trees/tree_top_view.pix":
                    sceneObjects.add(new Tree(basePosition, rotation, Tree.TreeType.TREE_TOP_VIEW));
                    break;
                case "data/road_signs/direction/209-30_.svg":
                    sceneObjects.add(new DirectionSign(basePosition, rotation, DirectionSign.DirectionType.FORWARD));
                    break;
                case "data/road_signs/direction/211-10_.svg":
                    sceneObjects.add(new DirectionSign(basePosition, rotation, DirectionSign.DirectionType.LEFT));
                    break;
                case "data/road_signs/direction/211-20_.svg":
                    sceneObjects.add(new DirectionSign(basePosition, rotation, DirectionSign.DirectionType.RIGHT));
                    break;
                case "data/road_signs/direction/214-10_.svg":
                    sceneObjects.add(new DirectionSign(basePosition, rotation, DirectionSign.DirectionType.FORWARD_LEFT));
                    break;
                case "data/road_signs/direction/214-20_.svg":
                    sceneObjects.add(new DirectionSign(basePosition, rotation, DirectionSign.DirectionType.FORWARD_RIGHT));
                    break;
                case "data/road_signs/direction/215_.svg":
                    sceneObjects.add(new DirectionSign(basePosition, rotation, DirectionSign.DirectionType.ROUNDABOUT));
                    break;
                case "data/road_signs/parking/314_10_.svg":
                    sceneObjects.add(new ParkingSign(basePosition, rotation, ParkingSign.ParkingSignType.PARKING_LEFT));
                    break;
                case "data/road_signs/parking/314_20_.svg":
                    sceneObjects.add(new ParkingSign(basePosition, rotation, ParkingSign.ParkingSignType.PARKING_RIGHT));
                    break;
                case "data/road_signs/priority/205_.svg":
                    sceneObjects.add(new PrioritySign(basePosition, rotation, PrioritySign.PrioritySignType.GIVEAWAY));
                    break;
                case "data/road_signs/priority/206_.svg":
                    sceneObjects.add(new PrioritySign(basePosition, rotation, PrioritySign.PrioritySignType.STOP));
                    break;
                case "data/road_signs/priority/306_.svg":
                    sceneObjects.add(new PrioritySign(basePosition, rotation, PrioritySign.PrioritySignType.PRIORITY_ROAD));
                    break;
                case "data/road_signs/speed/274_51_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_10));
                    break;
                case "data/road_signs/speed/274_52_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_20));
                    break;
                case "data/road_signs/speed/274_54_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_40));
                    break;
                case "data/road_signs/speed/274_55_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_50));
                    break;
                case "data/road_signs/speed/274_57_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_70));
                    break;
                case "data/road_signs/speed/274_59_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_90));
                    break;
                case "data/road_signs/speed/274_60_.svg":
                    sceneObjects.add(new SpeedSign(basePosition, rotation, SpeedSign.SpeedSignType.LIMIT_100));
                    break;
                case "data/road_tiles/2_lane_advanced/2_crossroads_2.tile":
                    sceneObjects.add(new AdvancedRoad(basePosition, rotation, AdvancedRoad.AdvancedRoadType.CROSSROADS));
                    break;
                case "data/road_tiles/2_lane_advanced/2_rotary.tile":
                    sceneObjects.add(new AdvancedRoad(basePosition, rotation, AdvancedRoad.AdvancedRoadType.ROTARY));
                    break;
                case "data/road_tiles/2_lane_advanced/2_t_junction_l.tile":
                    sceneObjects.add(new AdvancedRoad(basePosition, rotation, AdvancedRoad.AdvancedRoadType.JUNCTIONLEFT));
                    break;
                case "data/road_tiles/2_lane_advanced/2_t_junction_r.tile":
                    sceneObjects.add(new AdvancedRoad(basePosition, rotation, AdvancedRoad.AdvancedRoadType.JUNCTIONRIGHT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_45l.tile":
                    sceneObjects.add(new CurvedRoad(basePosition, rotation, CurvedRoad.CurvedRoadType.SIMPLE_45_LEFT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_45r.tile":
                    sceneObjects.add(new CurvedRoad(basePosition, rotation, CurvedRoad.CurvedRoadType.SIMPLE_45_RIGHT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_65l.tile":
                    sceneObjects.add(new CurvedRoad(basePosition, rotation, CurvedRoad.CurvedRoadType.SIMPLE_65_LEFT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_65r.tile":
                    sceneObjects.add(new CurvedRoad(basePosition, rotation, CurvedRoad.CurvedRoadType.SIMPLE_65_RIGHT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_90l.tile":
                    sceneObjects.add(new CurvedRoad(basePosition, rotation, CurvedRoad.CurvedRoadType.SIMPLE_90_LEFT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_90r.tile":
                    sceneObjects.add(new CurvedRoad(basePosition, rotation, CurvedRoad.CurvedRoadType.SIMPLE_90_RIGHT));
                    break;
                case "data/road_tiles/2_lane_simple/2_simple_s.tile":
                    sceneObjects.add(new SimpleRoad(basePosition, rotation, SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT));
                    break;
            }
            logger.log("SceneObject with ID:" + id + " added.");
            logger.log(sceneObjects.get(size).toString());
            logger.emptyLine();

        } catch (SceneObjectException e) {
            try {
                logger.log("SceneObject with ID:" + id + " can't be added -> " + e.getMessage());
                logger.emptyLine();
            } catch (LoggerException ex) {
                // No logging happens
            }

        } catch (LoggerException e) {
            // No logging happens
        }
    }

    private void parseXML() throws XMLParserException {

        sceneObjects = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLParserException("Error during the XML parse initialization: " + e.getMessage());
        }

        Document doc;

        try {
            doc = db.parse(new File(xmlPath));

            try {
                logger.log("XML parsing started, source-file:" + xmlPath);
                logger.emptyLine();
            } catch (LoggerException e) {
                // No logging happens!
            }

            NodeList sceneNodes = doc.getElementsByTagName("Scene");
            Element sceneNode = (Element) sceneNodes.item(0);
            this.sceneWidth = Integer.parseInt(sceneNode.getAttribute("width"));
            this.sceneHeight = Integer.parseInt(sceneNode.getAttribute("height"));

            NodeList objectsNodes = doc.getElementsByTagName("Objects");
            Element element = (Element) objectsNodes.item(0);

            NodeList objectList = element.getElementsByTagName("Object");
            for (int i = 0; i < objectList.getLength(); i++) {
                Element objectElement = (Element) objectList.item(i);
                String objectName = objectElement.getAttribute("name");
                int id = Integer.parseInt(objectElement.getAttribute("id"));

                NodeList positionList = objectElement.getElementsByTagName("Position");
                Element positionElement = (Element) positionList.item(0);

                ScenePoint basePosition = new ScenePoint((int) Math.round(Double.parseDouble(positionElement.getAttribute("x"))),
                        (int) Math.round(Double.parseDouble(positionElement.getAttribute("y"))));
                //int positionX = (int) Math.round(Double.parseDouble(positionElement.getAttribute("x")));
                //int positionY = (int) Math.round(Double.parseDouble(positionElement.getAttribute("y")));

                NodeList transformList = objectElement.getElementsByTagName("Transform");
                Element transformElement = (Element) transformList.item(0);

                int rotation = (int)Math.round(Math.toDegrees(Math.atan2(-1 * Double.parseDouble(transformElement.getAttribute("m12")),
                        Double.parseDouble(transformElement.getAttribute("m11")))));

                if (rotation == -0) rotation = 0;
                else
                    if (rotation < 0) rotation = 360 + rotation;


                createSceneObject(id, objectName, basePosition, rotation);
            }

            try {
                logger.log(String.format("XML parsing completed in %1d milliseconds!", logger.getElapsedTime() ));
            } catch (LoggerException e) {
                // No logging happens!
            }

        } catch (IOException|SAXException e) {
            throw new XMLParserException("Error during the XML parse: " + e.getMessage());
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " Width: " + getSceneWidth() + " Height: " + getSceneHeight() + " Number of SceneObjects: " + sceneObjects.size();
    }
}
