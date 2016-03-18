package camera;

public class Camera implements ICamera {
	
	Object closestSign; 						    //given in the object itself
	double laneDistance;							//meters or pixels define which one! 
	Object laneType;								//given in degree 0-360
	
	private void getClosestSign(Object[] visibleObjects, Object car) 
	{
		//set closest sign
		double min=999999;
		int minIndex=-1;
		int distX=0;
		int distY=0;
		for(int i=0;i<visibleObjects.length;i++)
		{
		distX = (visibleObjects[i].xPos- car.xPos)*(visibleObjects[i].xPos- car.xPos);
		distY =	(visibleObjects[i].yPos- car.yPos)*(visibleObjects[i].yPos- car.yPos);
			if(Math.sqrt(distX+distY)<min)
			{
				min=Math.sqrt(distX+distY);
				minIndex=i;
			}
		}
		if(minIndex=-1){
			closestSign=null;
		}
		else
		{
			closestSign=visibleObjects[minIndex];
		}
			
	}
	
	private void getLaneDistance(Object car, Object road)
	{
		//set distance from lane
		//set lane type
	}
	
	//+interface implementációk
	
}
