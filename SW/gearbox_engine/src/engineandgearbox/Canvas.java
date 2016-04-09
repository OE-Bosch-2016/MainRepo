/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engineandgearbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.lang.reflect.Array;
import javax.swing.JPanel;

/**
 *
 * @author Patrik
 */
public class Canvas extends JPanel {

    double[] aRPM,aTorque;
    int i;
    
    Canvas() {
        // set a preferred size for the custom panel.
        setPreferredSize(new Dimension(1000,500));
        aRPM = new double[10000];
        aRPM[0]=0;
        aTorque = new double[1000];
        aTorque[0]=0;
        i=1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString("BLAH", 20, 20);
        //g.drawRect(0, 0, 200, 200);
    }
    
    public void Rajzol(Graphics g, double RPM, double Torque) {
        super.paintComponent(g);
        aRPM[i]=RPM;
        aTorque[i]=Torque;
        i++;
        for (int j = 1; j < i; j++) {
            g.setColor(Color.blue);
            g.drawLine(j-1,500-((int)aRPM[j-1]/10), j, 500-((int)aRPM[j]/10));
            g.setColor(Color.red);
            g.drawLine(j-1,500-((int)aTorque[j-1]/5), j, 500-((int)aTorque[j]/5));
        }
    }
}
