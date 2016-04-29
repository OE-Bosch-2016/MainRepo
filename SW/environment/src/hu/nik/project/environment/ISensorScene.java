package hu.nik.project.environment;

import hu.nik.project.environment.objects.SceneObject;

import java.util.ArrayList;

/**
 * Created by zhodvogner on 2016. márc. 19.
 */
public interface ISensorScene {

    ArrayList<SceneObject> getVisibleSceneObjects(ScenePoint observerBase, int observerRotation, int viewAngle );
    ArrayList<SceneObject> getVisibleSceneObjects(ScenePoint observerBase, int observerRotation, int viewAngle, int viewDistance );

}
