package hu.nik.project.camera;

public interface ICamera {
	int posX();		//or double
	int posY();
	
	Object closestSign(); 	//given in the object itself
	double laneDistance();	//meters or pixels define which one! 
	Object laneType();	//given in degree 0-360
}
