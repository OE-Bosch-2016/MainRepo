package hu.nik.project.environment.objects;

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

    @BeforeClass
    public static void setUp() throws Exception {
        tree = new Tree(500, 88, 333, Tree.TreeType.TREE_TOP_VIEW);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(Tree.TreeType.TREE_TOP_VIEW, tree.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(500, tree.getPositionX());
        Assert.assertEquals(88, tree.getPositionY());
        Assert.assertEquals(333, tree.getRotation(), 0.00001);
    }
}