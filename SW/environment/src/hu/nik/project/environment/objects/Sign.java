package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract Class for Scene Objects
 */
abstract public class Sign<T> extends SceneObject {

    public Sign(int positionX, int positionY, double rotation) throws SceneObjectException {
        super(positionX, positionY, rotation);
    }

    public abstract T getObjectType();
}
