package hu.nik.project.environment;

import hu.nik.project.environment.objects.SceneObject;

import java.util.ArrayList;

/**
 * Created by zhodvogner on 2016. márc. 19.
 */
public class SensorServer implements ISensorServer {

    private Scene scene = null;

    public SensorServer(Scene scene) {
        this.scene = scene;
    }

    public ArrayList<SceneObject> getVisibleSceneObjects(ScenePoint observerBase, int observerRotation, int viewAngle ) {
        return getVisibleSceneObjects( observerBase, observerRotation, viewAngle, 0 );
    }

    public ArrayList<SceneObject> getVisibleSceneObjects(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance ) {

        ArrayList<SceneObject> result = new ArrayList<SceneObject>();
        for (SceneObject sceneObject : scene.getSceneObjects()) {

            if (sceneObject.isVisibleFromObserver( observerBase, observerRotation, viewAngle, viewDistance))
                result.add( sceneObject );

        }
        return result;
    }

}
