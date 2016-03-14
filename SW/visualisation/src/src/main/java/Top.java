import HMI.Hmi;
import Map.MapLoader;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by haxxi on 2016.03.01..
 */
public class Top extends JFrame {

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
    private JTextPane DTextPane;
    private JTextPane nTextPane;
    private JTextPane rTextPane;
    private JTextPane pTextPane;
    private JTextPane a1TextPane;
    private JTextPane a2TextPane;


    // HMI elements
    private Hmi hmi;

    private static final int DISPLAY_MAX_KM = 220;
    private static final int DISPLAY_MAX_TACHO = 6000;
    private final DefaultValueDataset mileAgeDataset = new DefaultValueDataset();
    private final DefaultValueDataset tachoMeterDataset = new DefaultValueDataset();
    private final DefaultValueDataset mileAgeDisplayDataset = new DefaultValueDataset();
    private final DefaultValueDataset tachoMeterDisplayDataset = new DefaultValueDataset();


    public Top() {
        init();
    }



    private void init() {
        hmi = new Hmi();
        hmi.setHmiListener(mileAgeListener);
        mileAgePanel.add(buildDialPlot(0, DISPLAY_MAX_KM, 20, mileAgeDataset, mileAgeDisplayDataset));
        tachometerPanel.add(buildDialPlot(0, DISPLAY_MAX_TACHO, 1000, tachoMeterDataset, tachoMeterDisplayDataset));
        setMileAgeValue(0);
        setTachometerValue(0);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(rootPanel);
        pack();


        mapLabel.setIcon(MapLoader.getImage(MapLoader.MAP1));

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
    }

    private void setMileAgeValue(int value) {
        mileAgeDataset.setValue(value);
        mileAgeDisplayDataset.setValue(Math.min(DISPLAY_MAX_KM, value));
    }

    private void setTachometerValue(int value) {
        tachoMeterDataset.setValue(value);
        tachoMeterDisplayDataset.setValue(Math.min(DISPLAY_MAX_TACHO, value));
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
            setTachometerValue((int)tachometer);
        }
    };
}
