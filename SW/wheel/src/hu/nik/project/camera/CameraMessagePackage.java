package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;

public class CameraMessagePackage {
	public SceneObject ClosestSign;
	public double LaneDistance;
	public SceneObject LaneType;
	public SceneObject[] visibleObjects;
	
	public CameraMessagePackage(SceneObject closestSign, double laneDistance, SceneObject laneType, SceneObject[] visibleObjects)
	{
		this.ClosestSign = closestSign;
		this.LaneDistance = laneDistance;
		this.LaneType = laneType;
		this.visibleObjects= visibleObjects;

	}
}
