package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;

public interface ICamera {

	public SceneObject getClosestSign(); 	//given in the object itself
	public double getLaneDistance();	//meters or pixels define which one! 
	public boolean getIsLaneRestricted();	//given in degree 0-360
}
