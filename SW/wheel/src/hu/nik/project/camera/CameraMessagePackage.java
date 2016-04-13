package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;
import java.lang.reflect.Array;

public class CameraMessagePackage {
	public SceneObject ClosestSign;
	public double LaneDistance;
	public SceneObject LaneType;
	
	public void CameraMessagePackage(SceneObject closestSign, double laneDistance, SceneObject laneType, Array[] visibleObjects){
		this.ClosestSign = closestsign;
		this.LaneDistance = laned;
		this.LaneType = laneType
	}
}
