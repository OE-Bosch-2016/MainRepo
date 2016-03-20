package camera;

///class definitions are found in Team1 repo at OE-Bosch-2016-Team1/MainRepo/blob/master/SW/environment/src/hu/nik/project/environment/
import hu.nik.project.environment.SceneObject;	 //for understanding SceneObjects
import hu.nik.project.environment.ScenePoint;	 //for understanding ScenePoints
import hu.nik.project.environment.Sign;			//for understanding Signs
import hu.nik.project.environment.DirectionSign;
import hu.nik.project.environment.ParkingSing;	
import hu.nik.project.environment.PrioritySign;	
import hu.nik.project.environment.SpeedSign;

public class Camera implements ICamera {
	
	Object closestSign; 	//given in the object itself
	double laneDistance;	//meters or pixels define which one! 
	Object laneType;	//given in degree 0-360
	
	private void calcClosestSign(SceneObject[] visibleObjects, SceneObject car) 
	{
		//set closest sign
		double min=999999;	//irrationally high number for minimum selection
		int minIndex=-1;
		int distX=0;
		int distY=0;
		for(int i=0;i<visibleObjects.length;i++) 					//calculate (x1-x2)^2*(y1-y2)^2 for each
		{
		distX = (visibleObjects[i].getBasePosition.getX- car.getBasePosition.getX)*
		(visibleObjects[i].getBasePosition.getX- car.getBasePosition.getX);
		
		distY =	(visibleObjects[i].getBasePosition.getY- car.getBasePosition.getY)*
		(visibleObjects[i].getBasePosition.getY- car.getBasePosition.getY);
		
			if(Math.sqrt(distX+distY)<min && 
				(
				visibleObjects[i].getObjectType ==DirectionSign ||
				visibleObjects[i].getObjectType ==ParkingSign ||
				visibleObjects[i].getObjectType ==PrioritySign ||
				visibleObjects[i].getObjectType ==SpeedSign
				))
			{
				min=Math.sqrt(distX+distY);
				minIndex=i;
			}
		}
		
		if(minIndex==-1)
		{
			closestSign=null;
		}
		else
		{
			closestSign=visibleObjects[minIndex];
		}
	}
	
	private void calcLaneDistance(Object car, Object road)
	{
		//set distance from lane
		//set lane type
	}
	
	//+interface implementációk via Apor:
	//what is X and Y? we don't have to return any poritions. ;F.Viktor
	public int posX(){
		return 0;	//have to implement the calculation of position X
	}
	public int posY(){
		return 0;	//have to implement the calculation of position X
	}
	
}
