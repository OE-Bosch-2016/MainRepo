package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Misc
 */
abstract public class Misc<T> extends SceneObject {
    public Misc(int positionX, int positionY, int rotation) throws SceneObjectException {
        super(positionX, positionY, rotation);
    }

    public abstract T getObjectType();
}
