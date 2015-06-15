package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Controls the application window Created by Kasper on 9-5-2015.
 */
public class WindowController extends WindowAdapter {
    private Application app;

    /**
     * Constructor.
     *
     * @param app
     *         that is controlled
     */
    public WindowController(Application app) {
        this.app = app;
    }

    /**
     * Window closing is triggered when the user clicks the red exit cross.
     *
     * @param event
     *         Window close event
     */
    @Override
    public void windowClosing(WindowEvent event) {
        app.stop();
    }
}
