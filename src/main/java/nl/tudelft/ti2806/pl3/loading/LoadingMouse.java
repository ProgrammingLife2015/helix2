package nl.tudelft.ti2806.pl3.loading;

import nl.tudelft.ti2806.pl3.Application;
import nl.tudelft.ti2806.pl3.LoadingObservable;
import nl.tudelft.ti2806.pl3.LoadingObserver;

import java.awt.Cursor;

/**
 * Loading mouse class is a Observer of the LoadingObserverable. It displays a
 * loading cursor when the Obserable is loading.
 * <p/>
 * <p/>
 * Created by Kasper on 28-5-2015.
 */
public class LoadingMouse implements LoadingObserver {
    private Application application;

    /**
     * Constructs a new LoadingMouse for the {@link Application}.
     *
     * @param application
     *         that needs the mouse.
     */
    public LoadingMouse(Application application) {
        this.application = application;
    }

    /**
     * Is called when a {@link LoadingObservable} notifies his
     * {@link LoadingObserver}. If the arguments are true, the application must
     * be loading, so a Wait cursor is displayed, otherwise the normal mouse is
     * displayed.
     *
     * @param loadingObservable
     *         class which called the function
     * @param arguments
     *         arguments from the loadingObservable
     */
    @Override
    public void update(LoadingObservable loadingObservable, Object arguments) {
        if (arguments.equals(true)) {
            application.setCursor(Cursor
                    .getPredefinedCursor(Cursor.WAIT_CURSOR));
        } else if (arguments.equals(false)) {
            application.setCursor(Cursor.getDefaultCursor());
        }
    }
}
