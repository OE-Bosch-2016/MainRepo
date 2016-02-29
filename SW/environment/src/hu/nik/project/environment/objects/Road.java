package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Abstract class for Road
 */
abstract public class Road<T> extends SceneObject {

    public Road(int positionX, int positionY, double rotation) throws SceneObjectException {
        super(positionX, positionY, rotation);
    }

    public abstract T getObjectType();
}
