import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GUITestingTool extends JFrame {
    private static boolean recording = false;
    private JButton recordButton;
    private JButton stopButton;
    private JButton playButton;
    private JPanel mainPanel;

    private final MyAWTEventListener listener = new MyAWTEventListener();

    protected GUITestingTool(JFrame requestedWindow) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK + AWTEvent.MOUSE_MOTION_EVENT_MASK);

        setContentPane(mainPanel); // Link to designed form
        setTitle("GUI Testing Tool");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null); // Center of screen
        setVisible(true);
        setLocation(getLocationOnScreen().x + 380, getLocationOnScreen().y - 157); // For fun

        recordButton.addActionListener(e -> {
            recording = true;
            listener.clearAWTEvents(); // Clear for next recording
        });
        stopButton.addActionListener(e -> recording = false);
        playButton.addActionListener(e -> {
            if (!recording) { // Only run while we aren't recording
                // Test fail-safe first
                if (listener.getAWTEventsSize() != 4) {
                    System.out.println("Please select all four components in order!");
                    return;
                }

                Thread thread = new Thread(() -> {
                    // When test cases are running, for user ease, send the JFrame to the front
                    requestedWindow.toFront();

                    try {
                        testGUIInput(); // Animate test cases based on inputs
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                thread.start();
            } else System.out.println("The recorder is still recording! Press \"Stop\" before pressing \"Play\"");
        });
    }

    private void testGUIInput() throws InterruptedException {
        ArrayList<String> fileLines = new ArrayList<>();
        try {
            // Get file
            BufferedReader reader = new BufferedReader(new FileReader("src/input.txt"));
            String line = reader.readLine();

            while (line != null) { // Read file lines
                fileLines.add(line); // Store lines
                line = reader.readLine(); // next line
            }
        } catch (IOException e) {
            System.out.println("File error!");
        }

        for (String line : fileLines) {
            String a, b, c; // Format from input.txt
            a = line.substring(0, line.indexOf(',')); // Get the first value
            b = line.substring(line.indexOf(',') + 1, line.lastIndexOf(','));
            c = line.substring(line.lastIndexOf(',') + 1);

            int MAX_SIZE = 9; // Testing for now

            // Ignore the test case where the input is larger than MAX_SIZE
            if (a.length() > MAX_SIZE || b.length() > MAX_SIZE || c.length() > MAX_SIZE) {
                System.out.println("Test Case: " + a + " + " + b + " = " + c + "\tignored. Input too large! Make it less than 10 digits");
                continue;
            }

            // Get first JTextField
            JTextField jTextField = (JTextField) listener.getAWTComponent(0);
            // Set first JTextField to first number
            jTextField.setText(a);

            // Get second JTextField
            jTextField = (JTextField) listener.getAWTComponent(1);
            // Set first JTextField to second number
            jTextField.setText(b);

            JButton jButton = (JButton) listener.getAWTComponent(3); // Get last JButton
            jButton.doClick();

            // Get third JTextField
            jTextField = (JTextField) listener.getAWTComponent(2);
            if (jTextField.getText().equals(c))
                System.out.println("Test Case: " + a + " + " + b + " = " + c + "\tpasses.");
            else System.out.println("Test Case: " + a + " + " + b + " = " + c + "\tfails.");

            Thread.sleep(Part2.DELAY_SECONDS * 1000);
        }
    }

    public static boolean isRecording() {
        return recording;
    }
}