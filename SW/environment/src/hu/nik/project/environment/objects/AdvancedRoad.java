package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

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

    public AdvancedRoad(ScenePoint basePosition, int rotation, AdvancedRoadType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public AdvancedRoadType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " AdvancedRoadType: " + type.toString();
    }
}
