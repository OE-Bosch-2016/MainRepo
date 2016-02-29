package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for people
 */
public class People extends Misc {

    public enum PeopleType {
        MAN
    }

    private PeopleType type;

    public People(int positionX, int positionY, double rotation, PeopleType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public PeopleType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " PeopleType: " + type.toString();
    }
}