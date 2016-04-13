package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;

public class CameraMessagePackage {
	public SceneObject ClosestSign;
	public double LaneDistance;
	public SceneObject LaneType;
	
	public void CameraMessagePackage(SceneObject closestsign, double laned, SceneObject laneType){
		this.ClosestSign = closestsign;
		this.LaneDistance = laned;
		this.LaneType = laneType
	}
}
