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
        //int observerRotation = 240;
        int observerRotation = 120;
        //bárhonnan mindig látnia kéne és mégse
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 360, 10000) );
        //259-re se meg, szóval nem ez a hiba
        Assert.assertTrue( tree.isVisibleFromObserver(observerBase, observerRotation, 259, 10000) );
        //361°>360 ???
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation, 361, radarDistance) );
        //-1°<360 ???
        Assert.assertFalse( tree.isVisibleFromObserver(observerBase, observerRotation,-1, radarDistance) );
    }
}