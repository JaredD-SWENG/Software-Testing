import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyAWTEventListener implements AWTEventListener {
    private final ArrayList<Component> awtEvents = new ArrayList<>();

    /**
     * Invoked when an event is dispatched in the AWT.
     *
     * @param event
     */
    @Override
    public void eventDispatched(AWTEvent event) {
        // Ignore any AWTEvents from the GUI Testing Tool
        if(     event.getSource() instanceof JFrame                                                                 &&
                ((JFrame) event.getSource()).getTitle().equals("GUI Testing Tool")                                  ||
                event.getSource() instanceof Component                                                              &&
                ((JFrame)SwingUtilities.getRoot((Component) event.getSource())).getTitle().equals("GUI Testing Tool"))
            return;

        // Makes sure we only get new JComponents
        if(     GUITestingTool.isRecording() && awtEvents.size() < 4 && event.getID() == MouseEvent.MOUSE_PRESSED &&
                (event.getSource() instanceof JTextField || event.getSource() instanceof JButton) &&
                !(awtEvents.contains(((JComponent) event.getSource()).getComponentAt(((JComponent) event.getSource()).getMousePosition()))))
            awtEvents.add(((JComponent) event.getSource()).getComponentAt(((JComponent) event.getSource()).getMousePosition()));
    }

    protected Component getAWTComponent(int index) {
        return awtEvents.get(index);
    }

    protected int getAWTEventsSize() {
        return awtEvents.size();
    }

    protected void clearAWTEvents() {
        awtEvents.clear();
    }
}