package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;
import java.util.Arrays;

public class CameraMessagePackage {
	public SceneObject ClosestSign;
	public double LaneDistance;
	public SceneObject LaneType;
	
	public void CameraMessagePackage(SceneObject closestSign, double laneDistance, SceneObject laneType, Arrays[] visibleObjects){
		this.ClosestSign = closestsign;
		this.LaneDistance = laned;
		this.LaneType = laneType
	}
}
