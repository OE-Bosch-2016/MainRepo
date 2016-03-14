package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Road
 */
abstract public class Road<T> extends SceneObject {

    public Road(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);
    }

    public abstract T getObjectType();
}
