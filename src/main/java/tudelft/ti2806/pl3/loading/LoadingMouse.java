package tudelft.ti2806.pl3.loading;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.LoadingObservable;
import tudelft.ti2806.pl3.LoadingObserver;

import java.awt.Cursor;

/**
 * Loading mouse class is a Observer of the LoadingObserverable.
 * It displays a loading cursor when the Obserable is loading.
 * <p>
 * Created by Kasper on 28-5-2015.
 */
public class LoadingMouse implements LoadingObserver {
	private Application application;

	public LoadingMouse(Application application) {
		this.application = application;
	}

	@Override
	public void update(LoadingObservable loadingObservable, Object arguments) {
		if (arguments.equals(true)) {
			application.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}

		if (arguments.equals(false)) {
			application.setCursor(Cursor.getDefaultCursor());
		}
	}
}
