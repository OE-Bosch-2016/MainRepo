package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for Tree
 */
public class TreeTest {

    private static Tree tree;
    private static int ultraDistance, ultraViewAngle, radarDistance, radarViewAngle, cameraDistance, cameraViewAngle, cameraViewWideAngle;

    @BeforeClass
    public static void setUp() throws Exception {
        //tree = new Tree(new ScenePoint(500, 88), 333, Tree.TreeType.TREE_TOP_VIEW);
        tree = new Tree(new ScenePoint(176, 836), 333, Tree.TreeType.TREE_TOP_VIEW);
        ultraDistance=300; //5m
        ultraViewAngle=60;
        radarDistance=12000; //200m
        radarViewAngle=180;
        cameraDistance=100000; //infinite
        cameraViewAngle=75;
        cameraViewWideAngle=170;
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(Tree.TreeType.TREE_TOP_VIEW, tree.getObjectType());
    }

    @Test
    public void testObjectBaseClass() {
        Assert.assertTrue(tree instanceof Misc);
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        //Assert.assertEquals(500, tree.getBasePosition().getX());
        //Assert.assertEquals(88, tree.getBasePosition().getY());
        Assert.assertEquals(176, tree.getBasePosition().getX());
        Assert.assertEquals(836, tree.getBasePosition().getY());
        Assert.assertEquals(333, tree.getRotation(), 0.00001);
    }

    @Test
    public void testIsVisibleFromObserver() throws Exception {
        ScenePoint observerBase = new ScenePoint(352, 544);
        int observerRotation = 225;

        //too little angle
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, 40, 10000) );
        //too little distance
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, 41, 380) );
        //it fits well (angle & distance)
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 41, 381) );

        //observer rotation: 244°; view: 3°; target: 244-1,5° .. 244+1,5° (between 242,5 and 245,5°)
        observerRotation = 244;
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 3, 10000) );
        //observer rotation: 243°; view: 5°; target: 243-2,5° .. 243+2,5° (between 240,5 and 245,5°)
        observerRotation = 243;
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 5, 10000) );
        //it's false because, we don't perceive float angle :(
        //observer rotation: 245°; view: 1°; target: 245-0,5° .. 245+0,5° (between 244,5 and 245,5°)
        observerRotation = 245;
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, 0, 10000) );

        //too big view parameter
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, 361, 1000) );
        //negative view parameter
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, -361 , 10000) );

        observerRotation = 245; //it's important; and view least 1
        //view is near 90°
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 89, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 90, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 91, 10000) );
        //view is near 180°
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 179, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 180, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 181, 10000) );
        //view is near 270°
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 269, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 270, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 271, 10000) );
        //view is near 360°
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 360, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 1, 10000) );

        //ERROR: the 229° still good, but between 230 and 360° have been wrong
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 229, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 230, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 359, 10000) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 360, 10000) );

        //with real sensors
        observerRotation = 225;
        //perfect
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, cameraViewWideAngle, cameraDistance) );
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, radarViewAngle, radarDistance) );
        //too far
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, ultraViewAngle, ultraDistance) );

    }
}