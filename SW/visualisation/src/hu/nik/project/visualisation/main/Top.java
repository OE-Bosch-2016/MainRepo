package hu.nik.project.visualisation.main;

import hu.nik.project.hmi.Hmi;
import hu.nik.project.parkingPilot.PPMain;
import hu.nik.project.parkingPilot.util.ParkingCalculator;
import hu.nik.project.utils.ImageLoader;
import hu.nik.project.utils.Vector2D;
import hu.nik.project.visualisation.car.AutonomousCar;
import hu.nik.project.visualisation.car.SteeringWheel;
import hu.nik.project.visualisation.VisualizationRenderer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by haxxi on 2016.03.01..
 */
public class Top extends JFrame { // implements KeyListener

    // UI elements
    private JTextArea hmi_mileage_text_area;
    private JSlider test_slider;
    private JPanel rootPanel;
    private JPanel ui_panel;
    private JPanel mileAgePanel;
    private JPanel tachometerPanel;
    private JPanel gearShiftPanel;
    private JPanel hmiPanel;
    private JLabel mapLabel;
    private JPanel mapPanel;
    private JSlider test_slider2;
    private JTextPane dTextPane;
    private JTextPane nTextPane;
    private JTextPane rTextPane;
    private JTextPane pTextPane;
    private JTextPane a1TextPane;
    private JTextPane a2TextPane;
    private JComboBox comboBox1;
    private JLabel steeringWheelLabel;
    private SteeringWheel steeringWheel;

    //Timer
    private Timer timer;

    //visualisation.Visualization
    public VisualizationRenderer vRenderer = null;

    // hmi elements
    private Hmi hmi;

    // Parking pilot
    private Timer moveTimer;
    private PPMain parkingPilot;
    private Timer parkingTimer;

    private static final int DISPLAY_MAX_KM = 220;
    private static final int DISPLAY_MAX_TACHO = 6000;
    private final DefaultValueDataset mileAgeDataset = new DefaultValueDataset();
    private final DefaultValueDataset tachoMeterDataset = new DefaultValueDataset();
    private final DefaultValueDataset mileAgeDisplayDataset = new DefaultValueDataset();
    private final DefaultValueDataset tachoMeterDisplayDataset = new DefaultValueDataset();
    private StringBuilder builder;

    //visualisation.car.car
    private AutonomousCar car;

    // parking test
    private int parkingType;
    private int stage = 0;
    private float rotate = 1;
    private int HORIZONTAL_PARKING = 0;
    private int VERTICAL_PARKING = 1;

    // mapPath
    private String mapPath;

    public Top(String mapPath) {
        this.mapPath = mapPath;
        init();
    }


    private void init() {
        parkingTimer = new Timer(42, parkingTimerListener);
        parkingPilot = new PPMain();
        moveTimer = new Timer(42, moveListener);
        hmi = Hmi.newInstance();
        hmi.setHmiListener(mileAgeListener);
        mileAgePanel.add(buildDialPlot(0, DISPLAY_MAX_KM, 20, mileAgeDataset, mileAgeDisplayDataset));
        tachometerPanel.add(buildDialPlot(0, DISPLAY_MAX_TACHO, 1000, tachoMeterDataset, tachoMeterDisplayDataset));
        setMileAgeValue(0);
        setTachometerValue(0);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(rootPanel);
        pack();

        //visualisation.car.car setup
        car = new AutonomousCar(new Vector2D(501, 90), ImageLoader.getCarImage());

        //visualisation.Visualization renderer setup
        vRenderer = new VisualizationRenderer(mapPanel, hmi, car, mapPath);

        //Timer setup
        //1 sec / 24 ~= 42 ms -> 24fps
        timer = new Timer(42, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vRenderer.render();
            }
        });

        //Steering Wheel setup
        steeringWheel = new SteeringWheel(hmi, steeringWheelLabel);
        steeringWheelLabel.setIcon(steeringWheel.GetSteeringWheel(0));

        // Test
        test_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = test_slider.getValue();
                hmi.mileage(value);
            }
        });

        test_slider2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = test_slider2.getValue();
                hmi.tachometer(value);
            }
        });

//        comboBox1.addItem("1");
//        comboBox1.addItem("2");
//        comboBox1.addItem("3");
//        comboBox1.addItem("4");
//        comboBox1.addItem("5");
//        comboBox1.addItem("6");


//        comboBox1.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                          }
//        });

        steeringWheelLabel.addKeyListener(keyListener);
        steeringWheelLabel.setFocusable(true);
        steeringWheelLabel.setFocusTraversalKeysEnabled(false);
        steeringWheelLabel.requestFocus();
        //comboBox1.setSelectedIndex(0);
        steeringWheelLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Test end

        //Start timer
        timer.start();
    }

    private void setMileAgeValue(int value) {
        mileAgeDataset.setValue(value);
        mileAgeDisplayDataset.setValue(Math.min(DISPLAY_MAX_KM, value));
    }

    private void setTachometerValue(int value) {
        tachoMeterDataset.setValue(value);
        tachoMeterDisplayDataset.setValue(Math.min(DISPLAY_MAX_TACHO, value));
    }

    private void setGearShiftStage(int stage) {
        pTextPane.setText(setColor("P", false));
        dTextPane.setText(setColor("D", false));
        nTextPane.setText(setColor("N", false));
        rTextPane.setText(setColor("R", false));
        a1TextPane.setText(setColor("1", false));
        a2TextPane.setText(setColor("2", false));

        if (stage == Hmi.GEAR_SHIFT_P)
            pTextPane.setText(setColor("P", true));
        else if (stage == Hmi.GEAR_SHIFT_1)
            a1TextPane.setText(setColor("1", true));
        else if (stage == Hmi.GEAR_SHIFT_2)
            a2TextPane.setText(setColor("2", true));
        else if (stage == Hmi.GEAR_SHIFT_D)
            dTextPane.setText(setColor("D", true));
        else if (stage == Hmi.GEAR_SHIFT_N)
            nTextPane.setText(setColor("N", true));
        else if (stage == Hmi.GEAR_SHIFT_R)
            rTextPane.setText(setColor("R", true));
    }

    private String setColor(String text, boolean activated) {
        builder = new StringBuilder();
        builder.append("<html><body>");
        if (activated)
            builder.append("<span style=\"color:red\"><b>").append(text).append("</b></span>");
        else
            builder.append("<span style=\"color:black\"><b>").append(text).append("</b></span>");
        builder.append("</body></html>");
        return builder.toString();
    }

    private ChartPanel buildDialPlot(int minimumValue, int maximumValue,
                                     int majorTickGap, DefaultValueDataset dataset, DefaultValueDataset displayDataset) {

        DialPlot plot = new DialPlot();
        plot.setDataset(0, dataset);
        plot.setDataset(1, displayDataset);

        plot.setDialFrame(new StandardDialFrame());

        // value indicator uses the real data set
        plot.addLayer(new DialValueIndicator(0));

        // needle uses constrained data set
        plot.addLayer(new DialPointer.Pointer(1));

        StandardDialScale scale = new StandardDialScale(minimumValue, maximumValue,
                -120, -300, majorTickGap, majorTickGap - 1);
        scale.setTickRadius(0.88);
        scale.setTickLabelOffset(0.20);
        plot.addScale(0, scale);

        return new ChartPanel(new JFreeChart(plot));
    }


    // Listener --------------------------------------------------------------------------------------------------------
    private Hmi.OnHmiListener mileAgeListener = new Hmi.OnHmiListener() {
        public void mileAgeChanged(float mile) {
            setMileAgeValue((int) mile);
        }

        public void tachometerChanged(float tachometer) {
            setTachometerValue((int) tachometer);
        }

        public void gearshiftChanged(int gearshift) {
            setGearShiftStage(gearshift);
        }
    };

    private KeyListener keyListener = new KeyListener() {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            stage = 0;
            rotate = 1;

            if (e.getKeyChar() == 'p')
                simulateMoving(HORIZONTAL_PARKING);
            else if (e.getKeyChar() == 'o')
                simulateMoving(VERTICAL_PARKING);
            else if (e.getKeyChar() == 'r')
                car.setPosition(new Vector2D(501, 90));

        }

        public void keyReleased(KeyEvent e) {
            steeringWheel.control(e);
        }
    };

    private void simulateMoving(int type) {
        parkingType = type;
        moveTimer.start();
    }

    private void simulateParking(int parkingType) {
        parkingPilot.parkingPilotActivate(car.getPosition(), car.getImage().getHeight(), car.getImage().getWidth(), parkingListener, parkingType);
    }

    private void horizontalParking() {
        car.move(-1);
        mapPanel.repaint();

        if (car.getPosition().get_coordinateY() < -156) {
            moveTimer.stop();
            simulateParking(ParkingCalculator.MODIFY_HORIZONTAL);
        }
    }

    private void verticalParking() {
        if (stage == 0) {
            car.move(-1);

            if (car.getPosition().get_coordinateY() < 45)
                stage++;
        } else if (stage == 1) {
            car.rotation(rotate);
            car.setPosition(new Vector2D(car.getPosition().get_coordinateX() + 0.2f, car.getPosition().get_coordinateY() - 0.15f));
            rotate++;

            if (rotate > 89)
                stage++;
        } else if (stage == 2) {
            car.setPosition(new Vector2D(car.getPosition().get_coordinateX() + 1, car.getPosition().get_coordinateY()));

            if (car.getPosition().get_coordinateX() > 632) {
                moveTimer.stop();
                simulateParking(ParkingCalculator.MODIFY_VERTICAL_LEFT);
            }
        }

    }

    // Listener --------------------------------------------------------------------------------------------------------
    private ActionListener moveListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (parkingType == HORIZONTAL_PARKING)
                horizontalParking();
            else if (parkingType == VERTICAL_PARKING)
                verticalParking();
        }
    };

    private ActionListener parkingTimerListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            mapPanel.repaint();
            parkingTimer.stop();

            parkingPilot.doParking();
        }
    };

    private ParkingCalculator.OnParkingListener parkingListener = new ParkingCalculator.OnParkingListener() {
        public void changePosition(float front, float side, float rotate) {
            car.setPosition(new Vector2D(car.getPosition().get_coordinateX() + side, car.getPosition().get_coordinateY() + front));
            car.rotation(rotate);
            parkingTimer.start();
        }

        public void changePosition(float front, float side) {
            car.setPosition(new Vector2D(car.getPosition().get_coordinateX() + side, car.getPosition().get_coordinateY() + front));
            parkingTimer.start();
        }
    };
}
