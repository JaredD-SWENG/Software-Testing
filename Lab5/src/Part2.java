import gui.MyGUI;

/**
 * Part 2 of Lab5
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public class Part2 {
    public static final long DELAY_SECONDS = 1; // To display execution

    public static void main(String[] args) {
        MyGUI gui = new MyGUI(); // View MyGUI
        gui.setSize(500,400);
        gui.setLocationRelativeTo(null); // Center of screen
        gui.setVisible(true);
        new GUITestingTool(gui); // View GUI Testing Tool
    }
}