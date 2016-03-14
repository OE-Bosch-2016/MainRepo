package hu.nik.project.environment.objects;

import hu.nik.project.environment.ScenePoint;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract Class for Scene Objects
 */
abstract public class Sign<T> extends SceneObject {

    public Sign(ScenePoint basePosition, int rotation) throws SceneObjectException {
        super(basePosition, rotation);
    }

    public abstract T getObjectType();
}
