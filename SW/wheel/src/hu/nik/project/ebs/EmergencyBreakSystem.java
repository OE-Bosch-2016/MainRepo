package hu.nik.project.ebs;

        import hu.nik.project.communication.ICommBusDevice;
        import hu.nik.project.communication.CommBus;
        import hu.nik.project.communication.CommBusConnector;
        import hu.nik.project.communication.CommBusConnectorType;
        import hu.nik.project.communication.CommBusException;
        import hu.nik.project.visualisation.car.model.DriverInputMessagePackage;
        import hu.nik.project.radar.RadarMessagePacket;

        //should implement interface, does not yet
        public class EmergencyBreakSystem implements ICommBusDevice {

            private boolean enabled;
            //communications
            private CommBusConnector commBusConnector;
            private double currDist;
            private double currRelSpeed;
            private float driverWheel;



            //output
            private float EBSState;

            public void commBusDataArrived() {
                Class dataType = commBusConnector.getDataType();

                if (dataType == DriverInputMessagePackage.class) {
                    try {
                        DriverInputMessagePackage data = (DriverInputMessagePackage) commBusConnector.receive();
                        enabled = data.aebIsActive();
                        driverWheel = data.getWheelAngle();
                    } catch (CommBusException e) {
                        e.printStackTrace();
                    }
                }

                if (commBusConnector.getDataType() == RadarMessagePacket.class) {

                    //dataType = commBusConnector.getDataType();
                    if (commBusConnector.getDataType() == RadarMessagePacket.class) {
                        try {
                            RadarMessagePacket data = (RadarMessagePacket) commBusConnector.receive();
                            currDist = data.getCurrentDistance() ;
                            currRelSpeed = data.getRelativeSpeed() ;
                        } catch (CommBusException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            public void doWork() {
                if (enabled) {
                    boolean sent = false;
                    EmergencyBreakSystemMessagePackage message = new EmergencyBreakSystemMessagePackage(EBSState); //so it doesnt have to remake it every time
                    while (!sent) {
                        try {
                            if (commBusConnector.send(message)) {
                                sent = true;
                            }
                        } catch (CommBusException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            public EmergencyBreakSystem(CommBus commBus, CommBusConnectorType commBusConnectorType) {
                commBusConnector = commBus.createConnector(this, commBusConnectorType);
            }


            public void calcEBSState() {
                if(Math.abs(driverWheel)>20 && currRelSpeed<0 && currDist<5000 ) //50m
                {
                    EBSState= (float)(-0.16*currRelSpeed)+1 ;
                }

                if(Math.abs(driverWheel)>20 && currRelSpeed<0 && currDist<500 ) //5m
               {
                EBSState= 10;
               }

                EBSState= 0;
            }
        }
