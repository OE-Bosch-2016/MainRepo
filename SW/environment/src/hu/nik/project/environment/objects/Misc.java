package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Misc
 */
abstract public class Misc<T> extends SceneObject {
    private ScenePoint center;

    public Misc(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);
    }

    public abstract T getObjectType();

    public ScenePoint getCenter() {
        return center;
    }
}
