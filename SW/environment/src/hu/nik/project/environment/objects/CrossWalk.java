package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Crosswalks
 */
public class CrossWalk extends Misc {

    public enum CrossWalkType {
        CROSSWALK_5
    }

    private CrossWalkType type;

    public CrossWalk(int positionX, int positionY, double rotation, CrossWalkType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public CrossWalkType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " CrossWalkType: " + type.toString();
    }
}
