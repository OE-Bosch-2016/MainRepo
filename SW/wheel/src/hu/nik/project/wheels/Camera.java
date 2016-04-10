package camera;

///class definitions are found in Team1 repo at OE-Bosch-2016-Team1/MainRepo/blob/master/SW/environment/src/hu/nik/project/environment/

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
		double distance;
		
	       	if (road.getObjectType() == SIMPLE_STRAIGHT)
	       	{
            	distance = pDistance(car.GetBasePosition().GetX(), car.GetBasePosition().GetY(), road.GetTopPoint().GetX(), road.GetTopPoint().GetY(), road.GetBottomPoint().GetX(), road.GetBottomPoint().GetY());
        	}
		else
		{
        	   double carDistanceToPoint = Math.sqrt(Math.pow(car.GetBasePosition().GetX() - road.getReferencePoint().GetX()) + Math.pow(car.GetBasePosition().GetY() - road.getReferencePoint().GetY()));
        	    if (carDistanceToPoint > road.getRadius())
        	    {
        	      distance = carDistanceToPoint - road.getRadius();
        	    }
        	    else
        	    {
        	        distance = road.getRadius() - carDistanceToPoint;
        	    }
        	}
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
