import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyAWTEventListener implements AWTEventListener {
    public static final ArrayList<Component> awtEvents = new ArrayList<>();

    /**
     * Invoked when an event is dispatched in the AWT.
     *
     * @param event
     */
    @Override
    public void eventDispatched(AWTEvent event) {
        // If state for the future can be broken into methods
        // Makes sure we only get new JComponents
        if(     awtEvents.size() < 4 && event.getID() == MouseEvent.MOUSE_PRESSED &&
                (event.getSource() instanceof JTextField || event.getSource() instanceof JButton) &&
                !(awtEvents.contains(((JComponent) event.getSource()).getComponentAt(((JComponent) event.getSource()).getMousePosition()))))
            awtEvents.add(((JComponent) event.getSource()).getComponentAt(((JComponent) event.getSource()).getMousePosition()));
    }
}