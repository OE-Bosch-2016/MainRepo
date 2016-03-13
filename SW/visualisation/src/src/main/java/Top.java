import HMI.Mileage;
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
    private JTextArea textAreaD;
    private JTextArea textAreaN;
    private JTextArea textAreaR;
    private JTextArea textAreaP;
    private JTextArea a1TextArea;
    private JTextArea a2TextArea;
    private JPanel gearShiftPanel;
    private JPanel hmi;
    private JLabel mapLabel;
    private JPanel mapPanel;


    // HMI elements
    private Mileage mileage;

    private static final int DISPLAY_MAX = 220;
    private final DefaultValueDataset dataset = new DefaultValueDataset();
    private final DefaultValueDataset displayDataset = new DefaultValueDataset();


    public Top() {
        init();
    }



    private void init() {
        mileage = new Mileage();
        mileage.setMileAgeListener(mileAgeListener);
        mileAgePanel.add(buildDialPlot(0, DISPLAY_MAX, 20));
        tachometerPanel.add(buildDialPlot(0, 6000, 1000));
        setValue(50);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(rootPanel);
        pack();


        mapLabel.setIcon(MapLoader.getImage(MapLoader.MAP1));
        // Test
        test_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = test_slider.getValue();
                mileage.mileage(value);
            }
        });
    }

    private void setValue(int value) {
        dataset.setValue(value);
        displayDataset.setValue(Math.min(DISPLAY_MAX, value));
    }

    private ChartPanel buildDialPlot(int minimumValue, int maximumValue,
                                     int majorTickGap) {

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
    private Mileage.OnMileAgeListener mileAgeListener = new Mileage.OnMileAgeListener() {
        public void changed(float mile) {
            hmi_mileage_text_area.setText(String.valueOf(mile) + Mileage.UNIT);
            setValue((int) mile);
        }
    };
}
