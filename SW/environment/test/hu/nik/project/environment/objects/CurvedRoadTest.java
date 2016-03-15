package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.03.15..
 *
 * Test class for curved roads
 */
public class CurvedRoadTest {

    private static CurvedRoad curvedRoad90Left;
    private static CurvedRoad curvedRoad90Right;

    private static CurvedRoad curvedRoad45Right;

    @BeforeClass
    public static void setUp() throws Exception {
        // Object 8
        curvedRoad90Left = new CurvedRoad(new ScenePoint(3050, 688), 0, CurvedRoad.CurvedRoadType.SIMPLE_90_LEFT);
        // Object 23
        curvedRoad90Right = new CurvedRoad(new ScenePoint(775, 1913), 90, CurvedRoad.CurvedRoadType.SIMPLE_90_RIGHT);
        // Object 14
        curvedRoad45Right = new CurvedRoad(new ScenePoint(1000, 3508), 135, CurvedRoad.CurvedRoadType.SIMPLE_45_RIGHT);
    }

    @Test
    public void testReferencePointSetup() throws Exception {
        Assert.assertEquals(2875, curvedRoad90Left.getReferencePoint().getX());
        Assert.assertEquals(688, curvedRoad90Left.getReferencePoint().getY());

        Assert.assertEquals(775, curvedRoad90Right.getReferencePoint().getX());
        Assert.assertEquals(1738, curvedRoad90Right.getReferencePoint().getY());

        Assert.assertEquals(876, curvedRoad45Right.getReferencePoint().getX());
        Assert.assertEquals(3384, curvedRoad45Right.getReferencePoint().getY());
    }

    @Test
    public void testGetRadius() throws Exception {
        Assert.assertEquals(350, curvedRoad90Left.getRadius());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("ClassType: CurvedRoad ->  Position X: 3050 Position Y: 688 Rotation: 0 CurvedRoadType: SIMPLE_90_LEFT", curvedRoad90Left.toString());
    }

    @Test
    public void testTopAndBottomPoints() throws Exception {
        Assert.assertEquals(2875, curvedRoad90Left.getTopPoint().getX());
        Assert.assertEquals(338, curvedRoad90Left.getTopPoint().getY());

        Assert.assertEquals(3225, curvedRoad90Left.getBottomPoint().getX());
        Assert.assertEquals(688, curvedRoad90Left.getBottomPoint().getY());

        Assert.assertEquals(425, curvedRoad90Right.getTopPoint().getX());
        Assert.assertEquals(1738, curvedRoad90Right.getTopPoint().getY());

        Assert.assertEquals(775, curvedRoad90Right.getBottomPoint().getX());
        Assert.assertEquals(2088, curvedRoad90Right.getBottomPoint().getY());

        Assert.assertEquals(876, curvedRoad45Right.getTopPoint().getX());
        Assert.assertEquals(3734, curvedRoad45Right.getTopPoint().getY());

        Assert.assertEquals(1124, curvedRoad45Right.getBottomPoint().getX());
        Assert.assertEquals(3632, curvedRoad45Right.getBottomPoint().getY());
    }
}