package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * ScrollListener handles the events when the user used the scrollwheel on his mouse.
 * Created by Kasper on 30-5-2015.
 */
public class ScrollListener implements MouseWheelListener {

    private Application application;

    /**
     * Constructor a Listener.
     *
     * @param app
     *         application to control
     */
    public ScrollListener(Application app) {
        application = app;

    }

    /**
     * Is called when the user scrolls his mousewheel.
     * It first calculates where the user his mouse is in Graph Units.
     * Then it increments or decrements the zoomlevel with the mouse position as zoomcenter.
     *
     * @param e
     *         {@link MouseWheelEvent} from the user
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int mouseScroll = e.getWheelRotation();

        if (mouseScroll < 0) {
            application.getGraphController().zoomLevelUp();
        } else {
            application.getGraphController().zoomLevelDown();
        }
    }

}
