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
		//set lane type
		//set distance from lane
		double distance;
		
		double width = 175;		//scene width
		double height = 175;	//scene height
		
		double laneXUpDistance = width / 2;		//lane upper end distance
		double laneXUp = (road.BasePosition.GetX() + width) / 2;	//lane upper end x coordinate
		double laneYUp = road.BasePosition.GetY();		//lane upper end y coordinate
		double laneXDownDistance = Math.sqrt((width / 2) * (width / 2) + height * height);		//lane bottom end distance
		double laneXDown = road.BasePosition.GetX() + width / 2;	//lane bottom end x coordinate
		double laneYDown = road.BasePosition.GetY() - height;		//lane bottom end y coordinate
		double laneDownDegree = Math.toDegrees(Math.atan((width / 2) / height)) + 270;		//lane bottom end degree, 270 degree plus because of the default direction
		
		//lane upper end x coordinate with rotation
		double xUpRotationed = road.BasePosition.GetX() + laneXUpDistance * Math.cos(road.rotation);
		double yUpRotationed = road.BasePosition.GetY() + laneYUpDistance * Math.sin(road.rotation);
		//lane bottom end x coordinate with rotation
		double xDownRotationed = road.BasePosition.GetX() + laneXDownDistance * Math.cos(road.rotation + laneDownDegree);
		double yDownRotationed = road.BasePosition.GetY() + laneYDownDistance * Math.sin(road.rotation + laneDownDegree);
		
		switch (road.getObjectType()) {
			case SIMPLE_STRAIGHT:
				distance = pDistance(car.BasePosition.GetX(), car.BasePosition.GetY(), xUpRotationed, yUpRotationed, XDownRotationed, YDownRotationed);
				break;
			case SIMPLE_45_LEFT:
				
				break;	
			case SIMPLE_45_RIGHT:
				
				break;
			case SIMPLE_65_LEFT:
				
				break;
			case SIMPLE_65_RIGHT:
				
				break;
			case SIMPLE_90_LEFT:
				
				break;
			case SIMPLE_90_RIGHT:
				
				break;
		}

		//set lane type
	}
	
	private double pDistance(x, y, x1, y1, x2, y2)
	{
  		double A = x - x1;
		double B = y - y1;
  		double C = x2 - x1;
	  	double D = y2 - y1;

  		double dot = A * C + B * D;
	  	double len_sq = C * C + D * D;
  		double param = -1;
  		if (len_sq != 0) //in case of 0 length line
      		param = dot / len_sq;

  		double xx, yy;

  		if (param < 0)
  		{
    			xx = x1;	
    			yy = y1;
  		}
  		else if (param > 1)
  		{
    			xx = x2;
    			yy = y2;
  		}
  		else
  		{
    			xx = x1 + param * C;
    			yy = y1 + param * D;
  		}

  		double dx = x - xx;
  		double dy = y - yy;
  		return Math.sqrt(dx * dx + dy * dy);
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
