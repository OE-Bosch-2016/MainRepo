package hu.nik.project.ebs;
        import java.util.ArrayList;

        import hu.nik.project.communication.ICommBusDevice;
        import hu.nik.project.communication.CommBus;
        import hu.nik.project.communication.CommBusConnector;
        import hu.nik.project.communication.CommBusConnectorType;
        import hu.nik.project.communication.CommBusException;
        import hu.nik.project.environment.objects.SceneObject;
        import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
        import hu.nik.project.radar.RadarMessagePacket;

//import hu.nik.project.radar.RadarMessagePackage;

        //should implement interface, does not yet
        public class EmergencyBreakSystem implements ICommBusDevice {

            private boolean enabled;
            //communications
            private CommBusConnector commBusConnector;
            private double currDist;
            private double currRelSpeed;
            private float driverWheel;

            //adjustable constants
            private static double ebsTolerance = 100; //how sensitive is the EBS system misses in 0.001 of a hour
            private static double ebsDistance = 800; //how far does the ebs predict in pixels

            //intermediate variables
            private ArrayList<SceneObject> jay_walkers;

            //output
            private float EBSState;

            public void commBusDataArrived() {
                Class dataType = commBusConnector.getDataType();

                if (dataType == DriverInputMessagePackage.class) {
                    try {
                        DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                        enabled = data.aebIsActive();

                         driverWheel= ((DriverInputMessagePackage) commBusConnector.receive()).getWheelAngle();
                    } catch (CommBusException e) {

                    }
                }

                if (commBusConnector.getDataType() == RadarMessagePacket.class) {

                    //dataType = commBusConnector.getDataType();
                    if (commBusConnector.getDataType() == RadarMessagePacket.class) {
                        try {
                            currDist = ((RadarMessagePacket) commBusConnector.receive()).getCurrentDistance() ;
                            currRelSpeed = ((RadarMessagePacket) commBusConnector.receive()).getRelativeSpeed() ;
                        } catch (CommBusException e) {
                            //stringData = e.getMessage();
                        }
                    }
                }
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


            public void calcEBSState() {
               if(Math.abs(driverWheel)>20 && currRelSpeed<0 && currDist<75)
               {
                EBSState=(float)currRelSpeed;

               }
                EBSState= 0;
            }
        }
