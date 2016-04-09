package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for people
 */
public class People extends Misc implements Serializable {

    public enum PeopleType {
        MAN
    }

    private PeopleType type;

    public People(ScenePoint basePosition, int rotation, PeopleType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public PeopleType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " PeopleType: " + type.toString();
    }
}