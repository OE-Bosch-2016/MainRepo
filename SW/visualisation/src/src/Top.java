import HMI.Mileage;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;

import javax.swing.*;
import java.awt.*;

/**
 * Created by haxxi on 2016.03.01..
 */
public class Top extends JFrame {

    // UI elements
    private JTextArea hmi_mileage_text_area;
    private JSlider test_slider;
    private JPanel rootPanel;
    private JPanel ui_panel;


    // HMI elements
    private Mileage mileage;

    // Delete ??
    private static final int DISPLAY_MAX = 220;
    private final DefaultValueDataset dataset = new DefaultValueDataset();
    private final DefaultValueDataset displayDataset = new DefaultValueDataset();
    private final JFrame frame = new JFrame();


    public Top() {
        init();
    }

    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(rootPanel);
        pack();

        mileage = new Mileage();
        mileage.setMileAgeListener(mileAgeListener);


        //****
        frame.setPreferredSize(new Dimension(300, 300));
        frame.add(buildDialPlot(0, DISPLAY_MAX, 20));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setValue(50);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
        //*****

        // Test
        test_slider.addChangeListener(e -> {
           int value = test_slider.getValue();
            mileage.mileage(value);
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
    private Mileage.OnMileAgeListener mileAgeListener = mile -> {
        hmi_mileage_text_area.setText(String.valueOf(mile) + Mileage.UNIT);
        setValue((int) mile);
    };
}
