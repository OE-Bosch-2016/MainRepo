package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for Advanced roads
 */
public class AdvancedRoad extends Road {

    public enum AdvancedRoadType {
        CROSSROADS,
        ROTARY,
        JUNCTIONLEFT,
        JUNCTIONRIGHT
    }

    private AdvancedRoadType type;

    public AdvancedRoad(int positionX, int positionY, int rotation, AdvancedRoadType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public AdvancedRoadType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " AdvancedRoadType: " + type.toString();
    }
}
