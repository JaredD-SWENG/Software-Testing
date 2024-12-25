/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUITesting.src.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author wxw18
 */
public class GUITest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Toolkit tk = Toolkit.getDefaultToolkit();
        AWTEventListener listener = new AWTEventListener() {
            ArrayList<AWTEvent> al = new ArrayList<>();

            @Override
            public void eventDispatched(AWTEvent event) {
                if (al.size() < 3) {
                    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
                        al.add(event);
                    }
                    if (al.size() == 3) {
                        JTextField jtf1 = (JTextField) al.get(0).getSource();
                        JTextField jtf2 = (JTextField) al.get(1).getSource();
                        JTextField jtf3 = (JTextField) al.get(2).getSource();
                        jtf1.setText("100");
                        jtf2.setText("200");
                        jtf3.setText("300");
                    }
                }
            }
        };

        tk.addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);
        gui.NewJFrame njf = new gui.NewJFrame();

        njf.setSize(400, 250);
        njf.setVisible(true);
    }
}
