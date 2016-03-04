import HMI.IMileage;
import HMI.Mileage;

import javax.swing.*;

/**
 * Created by haxxi on 2016.03.01..
 */
public class Top extends JFrame {

    // UI elements
    private JTextArea hmi_mileage_text_area;
    private JSlider test_slider;
    private JPanel rootPanel;

    // HMI elements
    private Mileage mileage;

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

        // Test
        test_slider.addChangeListener(e -> {
           int value = test_slider.getValue();
            mileage.mileage(value);
        });
    }

    // Listener --------------------------------------------------------------------------------------------------------
    private Mileage.OnMileAgeListener mileAgeListener = mile -> hmi_mileage_text_area.setText(String.valueOf(mile) + Mileage.UNIT);
}
