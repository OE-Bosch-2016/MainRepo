package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Test class for people
 */
public class PeopleTest {

    private static People people;

    @BeforeClass
    public static void setUp() throws Exception {
        people = new People(new ScenePoint(300, 300), 0, People.PeopleType.MAN);
    }

    @Test
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(People.PeopleType.MAN, people.getObjectType());
    }

    @Test
    public void testPositionsAndRotationGetters() throws Exception{
        Assert.assertEquals(300, people.getBasePosition().getX());
        Assert.assertEquals(300, people.getBasePosition().getY());
        Assert.assertEquals(0, people.getRotation(), 0.00001);
    }
}