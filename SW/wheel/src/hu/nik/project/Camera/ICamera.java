package hu.nik.project.camera;

import hu.nik.project.environment.objects.SceneObject;

public interface ICamera {
	int posX();		//or double
	int posY();
	
	public SceneObject getClosestSign(); 	//given in the object itself
	public double getLaneDistance();	//meters or pixels define which one! 
	public SceneObject getLaneType();	//given in degree 0-360
}
