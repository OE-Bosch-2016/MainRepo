package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;

import java.io.Serializable;

public class CameraMessagePackage implements Serializable {
	public SceneObject ClosestSign;
	public double LaneDistance;
	public boolean IsLaneRestricted;
	public SceneObject[] visibleObjects;
	
	public CameraMessagePackage(SceneObject closestSign, double laneDistance, boolean IsLaneRestricted)
	{
		this.ClosestSign = closestSign;
		this.LaneDistance = laneDistance;
		this.IsLaneRestricted = IsLaneRestricted;
	}
}
