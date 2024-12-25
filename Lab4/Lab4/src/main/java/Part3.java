import gui.MyGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part3 {
    public static final long DELAY_SECONDS = 1; // To display execution

    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        MyAWTEventListener listener = new MyAWTEventListener();
        tk.addAWTEventListener(listener, java.awt.AWTEvent.MOUSE_EVENT_MASK);

        MyGUI gui = new MyGUI();
        gui.setSize(500,400);
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);

        gui.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if(MyAWTEventListener.awtEvents.size() < 4)
                    System.out.println("Please select all four components in order!");
                else {
                    Thread thread = new Thread(() -> {
                        try {
                            testGUI();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    thread.start();
                }
            }
        });
    }

    public static void testGUI() throws InterruptedException {
        ArrayList<String> fileLines = new ArrayList<>();

        try {
            // Get file
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            String line = reader.readLine();

            while(line!=null) {
                fileLines.add(line); // Store lines
                line = reader.readLine(); // next line
            }
        } catch (IOException e) {
            System.out.println("File error!");
        }

        for (String line : fileLines) {
            String a, b, c;
            a = line.substring(0, line.indexOf(',')); // Get the first value
            b = line.substring(line.indexOf(',') + 1, line.lastIndexOf(','));
            c = line.substring(line.lastIndexOf(',') + 1);

            // Get first JTextField
            JTextField jTextField = (JTextField) MyAWTEventListener.awtEvents.get(0);
            // Set first JTextField to first number
            jTextField.setText(a);

            // Get second JTextField
            jTextField = (JTextField) MyAWTEventListener.awtEvents.get(1);
            // Set first JTextField to second number
            jTextField.setText(b);

            JButton jButton = (JButton) MyAWTEventListener.awtEvents.get(3); // Get last JButton
            jButton.doClick();

            // Get third JTextField
            jTextField = (JTextField) MyAWTEventListener.awtEvents.get(2);
            if (jTextField.getText().equals(c))
                System.out.println("Test Case: " + a + " + " + b + " = "+ c + "\tpasses.");
            else System.out.println("Test Case: " + a + " + " + b + " = "+ c + "\tfails.");

            Thread.sleep(DELAY_SECONDS * 1000);
        }
    }
}