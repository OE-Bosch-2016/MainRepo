/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.nik.project.ultrasonicsensor;

import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.CrossWalk;
import hu.nik.project.environment.objects.PrioritySign;
import hu.nik.project.environment.objects.SceneObject;
import hu.nik.project.environment.objects.SceneObjectException;
import hu.nik.project.environment.objects.Tree;
import hu.nik.project.environment.Scene;
import hu.nik.project.environment.XMLParserException;
import hu.nik.project.environment.objects.Car;
import java.util.ArrayList;

/**
 *
 * @author András
 */
public class Ultrasonic {

    public static void main(String[] args) throws SceneObjectException, XMLParserException, CommBusException {

        ScenePoint currPosition = new ScenePoint(0, 240);
        Sonar sonar = new Sonar(45, 0, currPosition);
        
        System.out.println("Car pos:" + currPosition.getX()+","+currPosition.getY());
        System.out.println("Sonar pos:"+ sonar.getSonarPos().getX()+","+sonar.getSonarPos().getY());
        
        ArrayList<SceneObject> viewableObjs = new ArrayList<>();
        ScenePoint s = new ScenePoint(50, 330);
        ScenePoint s2 = new ScenePoint(50, 325);
        ScenePoint s3 = new ScenePoint(50, 332);
        ScenePoint s4 = new ScenePoint(50, 323);
        SceneObject tree = new Tree(s, 0, Tree.TreeType.TREE_TOP_VIEW);
        viewableObjs.add(tree);
        
        System.out.println("Tree pos:"+tree.getBasePosition().getX()+","+tree.getBasePosition().getY());
        ObjPositions o = new ObjPositions(new Pos(s.getX(), s.getY()), 80, 80);
        for (int i = 0; i < o.getPositions().length; i++) {
            System.out.print("{"+o.getPositions()[i].getPosX()+","+o.getPositions()[i].getPosY()+"} ");
        }
        
        System.out.println("");
        SceneObject zebra = new CrossWalk(s2, 0, CrossWalk.CrossWalkType.CROSSWALK_5);
        viewableObjs.add(zebra);
        SceneObject zebra2 = new CrossWalk(s2, 0, CrossWalk.CrossWalkType.CROSSWALK_5);
        viewableObjs.add(zebra2);
        
        
        SceneObject tabla = new PrioritySign(s2, 0, PrioritySign.PrioritySignType.STOP);
        viewableObjs.add(tabla);
        
        System.out.println("Sign pos:"+tabla.getBasePosition().getX()+","+tabla.getBasePosition().getY());
        ObjPositions o2 = new ObjPositions(new Pos(s2.getX(), s2.getY()), 80, 80);
        for (int i = 0; i < o2.getPositions().length; i++) {
            System.out.print("{"+o2.getPositions()[i].getPosX()+","+o2.getPositions()[i].getPosY()+"} ");
        }
        System.out.println("");
        
        SceneObject tree2 = new Tree(s3, 0, Tree.TreeType.TREE_TOP_VIEW);
        viewableObjs.add(tree2);
        System.out.println("Tree2 pos:"+tree2.getBasePosition().getX()+","+tree2.getBasePosition().getY());
        ObjPositions o3 = new ObjPositions(new Pos(s3.getX(), s3.getY()), 80, 80);
        for (int i = 0; i < o3.getPositions().length; i++) {
            System.out.print("{"+o3.getPositions()[i].getPosX()+","+o3.getPositions()[i].getPosY()+"} ");
        }
        System.out.println("");
        
        
        
        System.out.println("-------");
        System.out.println("Closest distance: "+sonar.getNearestObjectDistance(viewableObjs, currPosition));
        System.out.println("-------");
        System.out.println("Viewable objs:");
        for (int i = 0; i < viewableObjs.size(); i++) {
            System.out.println(viewableObjs.get(i).getObjectType());
        }
        System.out.println("-------");
        System.out.println("Filtered objs:");
        for (int i = 0; i < sonar.getFilteredObjs().size(); i++) {
            System.out.println(sonar.getFilteredObjs().get(i).getObjectType());
        }
        
        SceneObject tree3 = new Tree(s4, 0, Tree.TreeType.TREE_TOP_VIEW);
        viewableObjs.add(tree3);
        System.out.println("Tree3 pos:"+tree3.getBasePosition().getX()+","+tree3.getBasePosition().getY());
        ObjPositions o4 = new ObjPositions(new Pos(s4.getX(), s4.getY()), 80, 80);
        for (int i = 0; i < o4.getPositions().length; i++) {
            System.out.print("{"+o4.getPositions()[i].getPosX()+","+o4.getPositions()[i].getPosY()+"} ");
        }
        System.out.println("");
        System.out.println("Closest distance: "+sonar.getNearestObjectDistance(viewableObjs, currPosition));
        
        System.out.println("|----------------|");
        CommBus cb = new CommBus();
        CommBusConnectorType cmtype = CommBusConnectorType.Sender;
        Scene scene = new Scene("src\\hu\\nik\\project\\sceneroads\\road_1.xml");
        
////        for (int i = 0; i < scene.getSceneObjects().size(); i++) {
////            if(scene.getSceneObjects().get(i).getObjectType() == Tree.TreeType.TREE_TOP_VIEW)
////            {
////                ObjPositions oTree = new ObjPositions(new Pos(scene.getSceneObjects().get(i).getBasePosition().getX(), scene.getSceneObjects().get(i).getBasePosition().getY()), 80, 80);
////                System.out.print("Tree pos: ");
////                for (int j = 0; j < oTree.getPositions().length; j++) {
////                    System.out.print("{"+oTree.getPositions()[j].getPosX()+","+oTree.getPositions()[j].getPosY()+"} ");
////                }
////                System.out.println("");
////            }
////        }

//      
        int rot = 270;
        int rot2 =180; 
        ScenePoint sce = new ScenePoint(3100, 500);
        Car car = new Car(sce, rot);

        System.out.println("car base: " +car.getBasePosition());
        System.out.println("car centre: "+car.getCenterPoint());

        UltrasonicModul um = new UltrasonicModul(cb,cmtype, car, scene);

        um.getNearestObjectDistances(sce,rot);
        for (int i = 0; i < um.getClosestDistance().length ; i++) {
            System.out.println("Closest distance in zone:" +i+ " ; " +um.getClosestDistance()[i]);
        }
        um.SendToBus();
        
        
        
        
    }

}
