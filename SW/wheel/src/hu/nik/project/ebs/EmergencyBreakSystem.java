package hu.nik.project.ebs;
        import java.util.ArrayList;

        import hu.nik.project.communication.ICommBusDevice;
        import hu.nik.project.communication.CommBus;
        import hu.nik.project.communication.CommBusConnector;
        import hu.nik.project.communication.CommBusConnectorType;
        import hu.nik.project.communication.CommBusException;
        import hu.nik.project.environment.objects.SceneObject;

        //import hu.nik.project.radar.RadarMessagePackage;

        //should implement interface, does not yet
        public class EmergencyBreakSystem implements ICommBusDevice {

            //communications
            private CommBusConnector commBusConnector;
            public SceneObject[] visibleObjectArray;

            //adjustable constants
            private static double ebsTolerance = 100; //how sensitive is the EBS system misses in 0.001 of a hour
            private static double ebsDistance = 800; //how far does the ebs predict in pixels

            //intermediate variables
            private ArrayList<SceneObject> jay_walkers;

            //output
            private float EBSState;

            public void commBusDataArrived() {
/*
                if (commBusConnector.getDataType() == RadarMessagePackage.class) {

                    //dataType = commBusConnector.getDataType();
                    if (commBusConnector.getDataType() == RadarMessagePackage.class) {
                        try {
                            visibleObjectArray = ((RadarMessagePackage) commBusConnector.receive()).visibleObjects;

                        } catch (CommBusException e) {
                            //stringData = e.getMessage();
                        }
                    }
                }*/
            }

            public void SendToCom() {
                boolean sent = false;
                EmergencyBreakSystemMessagePackage message = new EmergencyBreakSystemMessagePackage(EBSState); //so it doesnt have to remake it every time
                while (!sent) {
                    try {
                        if (commBusConnector.send(message)) {
                            sent = true;
                        }
                    } catch (CommBusException e) {
                        break;
                    }
                }
            }


            public EmergencyBreakSystem(CommBus commBus, CommBusConnectorType commBusConnectorType) {
                commBusConnector = commBus.createConnector(this, commBusConnectorType);
            }


            public void calcEBSState(SceneObject car) {
                if (!jay_walkers.isEmpty())//&& car.getSpeed>0 && car.getSpeed<80)
                {
                    for (int i = 0; i < jay_walkers.size(); i++) //Every walker
                    {
                        SceneObject walker = jay_walkers.get(i);




                        /*
                        //path of walker
                        double x1 = Math.tan(Math.toRadians(walker.getRotation())); //x is the coefficient of x in mx+n=y
                        double n1 = walker.getBasePosition().getY() - (x1 * walker.getBasePosition().getX()); //n is the running point
                        //path of car
                        double x2 = Math.tan(Math.toRadians(car.getRotation())); //x is the coefficient of x in mx+n=y
                        double n2 = walker.getBasePosition().getY() - (x2 * walker.getBasePosition().getX()); //n is the running point

                        if (!(x1 == x2)) //if lines are not parralel
                        {
                            double interPointX = (n1 - n2) / (x1 - x2);
                            double interPointY = x1 * interPointX + n1;

                            double dirVectorX = walker.getBasePosition().getX() - interPointX;
                            double dirVectorY = walker.getBasePosition().getY() - interPointY;

                            //angle between the vector from one point to another and the walker.direction vector
                            double angleDiff = Math.acos(
                                    (dirVectorX * Math.cos(walker.getRotation()) + dirVectorY * Math.sin(walker.getRotation()))
                                            / (Math.sqrt(dirVectorX * dirVectorX + dirVectorY * dirVectorY)
                                            + Math.sqrt(Math.cos(walker.getRotation()) * Math.cos(walker.getRotation())
                                            + Math.sin(walker.getRotation()) * Math.sin(walker.getRotation()))
                                    ));


                            if (Math.toDegrees(angleDiff) < 90) //if walker is going towards the intersection
                            {
                                //calculating point of collision
                                double carDist = Math.sqrt((interPointX - car.getBasePosition().getX()) * (interPointX - car.getBasePosition().getX()) +
                                        (interPointY - car.getBasePosition().getY()) * (interPointY - car.getBasePosition().getY()));
                                double walkDist = Math.sqrt((interPointX - walker.getBasePosition().getX()) * (interPointX - walker.getBasePosition().getX()) +
                                        (interPointY - walker.getBasePosition().getY()) * (interPointY - walker.getBasePosition().getY()));
                                //t=s/v
                                double carT = 0; //carDist/car.getSpeed; //to be implemented?
                                double walkerT = 0; // walkDist/walker.getSpeed; //to be implemented?

                                if (Math.abs(carT - walkerT) < 1 / ebsTolerance && carDist < ebsDistance) //distance in pixel coordinates speed in km/h
                                {
                                    EBSState = true;
                                    return;
                                }
                            }
                        }*/
                    }
                }

                EBSState = 0;
            }
        }
