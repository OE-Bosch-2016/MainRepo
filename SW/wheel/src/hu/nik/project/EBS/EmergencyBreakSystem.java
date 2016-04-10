package EBS;
import java.util.ArrayList;
import java.util.List;
import hu.nik.project.environment.objects.SceneObject;


public class EmergencyBreakSystem {
	
    private static double ebsTolerance = 100; //how sensitive is the EBS system misses in 0.001 of a hour
    private static double ebsDistance = 800; //how far does the ebs predict in pixels
    
    public boolean get_EBS_State(SceneObject car, List<SceneObject> jay_walkers)
    {
        
        if (!jay_walkers.isEmpty() && car.speed>0 && car.speed<80)
        {
            for (int i = 0; i < jay_walkers.size(); i++) //Every walker
            {
              SceneObject walker =jay_walkers.get(i); 
                          
             //path of walker
             double x1 = Math.tan(Math.toRadians(walker.direction)); //x is the coefficient of x in mx+n=y
             double n1 = walker.PosY - (x1 * walker.PosX ); //n is the running point
             //path of car
             double x2 = Math.tan( Math.toRadians(car.direction)); //x is the coefficient of x in mx+n=y
             double n2 = walker.PosY - (x2 * walker.PosX ); //n is the running point
             
             if (!(x1==x2)) //if lines are not parralel
                {
                double interPointX = (n1-n2)/(x1-x2);          
                double interPointY = x1*interPointX + n1;

                double dirVectorX = walker.PosX-interPointX;
                double dirVectorY = walker.PosY-interPointY;

                //angle between the vector from one point to another and the walker.direction vector
                double angleDiff = Math.acos(
                     (dirVectorX*Math.cos(walker.direction) + dirVectorY*Math.sin(walker.direction))
                     /(Math.sqrt(dirVectorX*dirVectorX + dirVectorY*dirVectorY) 
                             + Math.sqrt(Math.cos(walker.direction)*Math.cos(walker.direction) 
                             + Math.sin(walker.direction)*Math.sin(walker.direction))
                             ));

                    
                    if (Math.toDegrees(angleDiff) < 90) //if walker is going towards the intersection
                    {                
                    //calculating point of collision
                     double carDist = Math.sqrt((interPointX-car.PosX)*(interPointX-car.PosX) +
                                            (interPointY-car.PosY)*(interPointY-car.PosY));
                     double walkDist = Math.sqrt((interPointX-walker.PosX)*(interPointX-walker.PosX) +
                                            (interPointY-walker.PosY)*(interPointY-walker.PosY));
                    //t=s/v
                     double carT = carDist/car.speed;
                     double walkerT = walkDist/walker.speed;

                     if(Math.abs(carT-walkerT)<1/ebsTolerance && carDist<ebsDistance) //distance in pixel coordinates speed in km/h
                         {
                             return true;
                         }
                    }
                }
            }
        }
        
        return false;
    }
}
