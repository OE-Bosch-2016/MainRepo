package hu.nik.project.camera;

import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.objects.*;


import hu.nik.project.communication.ICommBusDevice;
import hu.nik.project.communication.CommBus;
import hu.nik.project.communication.CommBusConnector;
import hu.nik.project.communication.CommBusConnectorType;
import hu.nik.project.communication.CommBusException;

public class Camera implements ICamera, ICommBusDevice {
	
	SceneObject closestSign; 	//given in the object itself
	double laneDistance;	//meters or pixels define which one! 
	boolean IsLaneRestricted;	//given in degree 0-360
	Scene currentScene;
	Car currentCar;

public	SceneObject[] visibleObjects;

	private CommBusConnector commBusConnector;

	@Override
	public void commBusDataArrived(){}

	public void SendToCom() {
		boolean sent = false;
		CameraMessagePackage message = new CameraMessagePackage(closestSign,laneDistance,IsLaneRestricted); //so it doesnt have to remake it every time
		while(!sent)
		{
			try {
				if(commBusConnector.send(message)) {
					sent = true;
				}
			}
			catch(CommBusException e)
			{
				break;
			}
		}
	}

	public void calcOnTick()
	{
		calcClosestSign();
		calcLaneDistance();
	}

	public Camera(CommBus commBus, CommBusConnectorType commBusConnectorType, Scene scene, Car car) //scene has to be given in pointer?
	{
		commBusConnector = commBus.createConnector(this, commBusConnectorType);

		closestSign=null;
		laneDistance=-1;
		IsLaneRestricted=false;
		currentScene =scene;
		currentCar = car;
	}
        
    
	private void calcClosestSign() //need to get the car ???!!
	{
		visibleObjects = (SceneObject[]) currentScene.getVisibleSceneObjects(currentCar.getBasePosition(),currentCar.getRotation(),70).toArray();

		//set closest sign
		double min=999999;	//irrationally high number for minimum selection
		int minIndex=-1;
		int distX=0;
		int distY=0;
		for(int i=0;i<visibleObjects.length;i++) 					//calculate (x1-x2)^2*(y1-y2)^2 for each
		{
		distX = (visibleObjects[i].getBasePosition().getX()- currentCar.getBasePosition().getX())*
		(visibleObjects[i].getBasePosition().getX()- currentCar.getBasePosition().getX());
		
		distY =	(visibleObjects[i].getBasePosition().getY()- currentCar.getBasePosition().getY())*
		(visibleObjects[i].getBasePosition().getY()- currentCar.getBasePosition().getY());

                
			if(Math.sqrt(distX+distY)<min && 
				(
				 visibleObjects[i].getObjectType() ==DirectionSign.DirectionType.FORWARD ||
                 visibleObjects[i].getObjectType() ==DirectionSign.DirectionType.FORWARD_LEFT ||
				 visibleObjects[i].getObjectType() ==DirectionSign.DirectionType.FORWARD_RIGHT ||
                                visibleObjects[i].getObjectType() ==DirectionSign.DirectionType.LEFT ||
                                visibleObjects[i].getObjectType() ==DirectionSign.DirectionType.RIGHT ||
                                visibleObjects[i].getObjectType() ==DirectionSign.DirectionType.ROUNDABOUT ||
								visibleObjects[i].getObjectType() ==ParkingSign.ParkingSignType.PARKING_BOLLARD ||
                                visibleObjects[i].getObjectType() ==ParkingSign.ParkingSignType.PARKING_LEFT ||
                                visibleObjects[i].getObjectType() ==ParkingSign.ParkingSignType.PARKING_RIGHT ||
								visibleObjects[i].getObjectType() ==PrioritySign.PrioritySignType.GIVEAWAY ||
                                visibleObjects[i].getObjectType() ==PrioritySign.PrioritySignType.PRIORITY_ROAD ||
                                visibleObjects[i].getObjectType() ==PrioritySign.PrioritySignType.STOP ||
								visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_10 ||
                                visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_70 ||
                                visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_100 ||
                                visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_20 ||
                                visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_40 ||
                                visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_50 ||
                                visibleObjects[i].getObjectType() ==SpeedSign.SpeedSignType.LIMIT_90
                                
				)
			)
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
	
	private void calcLaneDistance()
	{
		//Road road = currentScene.getVisibleSceneObjects(car.getBasePosition(),car.getRotation(),70);
		Road road = null; //temporary to make it complie^
	       	if (road.getObjectType() == SimpleRoad.SimpleRoadType.SIMPLE_STRAIGHT)
	       	{
            	laneDistance = pDistance(currentCar.getBasePosition().getX(), currentCar.getBasePosition().getY(), road.getTopPoint().getX(), road.getTopPoint().getY(), road.getBottomPoint().getX(), road.getBottomPoint().getY());
        	}
                else 
		{
        	   double carDistanceToPoint = Math.sqrt(
                           Math.pow(currentCar.getBasePosition().getX() - ((CurvedRoad)road).getReferencePoint().getX(),2)
                         + Math.pow(currentCar.getBasePosition().getY() - ((CurvedRoad)road).getReferencePoint().getY(),2)
                           );
        	    if (carDistanceToPoint > (((CurvedRoad)road).getRadius()))
                        
        	    {
        	      laneDistance = carDistanceToPoint - ((CurvedRoad)road).getRadius();
        	    }
        	    else
        	    {
        	        laneDistance = ((CurvedRoad)road).getRadius() - carDistanceToPoint;
        	    }
        	}

	}
	
	private double pDistance(double x,double y,double x1,double y1,double x2,double y2)
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
	//deleted x and y, what was that even?. ;F.Viktor
                
        @Override
        public SceneObject getClosestSign()
        {
            return closestSign;
        }
        
        @Override
	public double getLaneDistance()
        {
            return laneDistance;
        } 
        
        @Override
	public boolean getIsLaneRestricted()
        {
            return IsLaneRestricted;
        }
	
}
