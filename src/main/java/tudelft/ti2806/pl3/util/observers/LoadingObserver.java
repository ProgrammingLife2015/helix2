package tudelft.ti2806.pl3.util.observers;

import tudelft.ti2806.pl3.util.observable.LoadingObservable;

/**
 * Interface for the Loading Observer
 * Created by Kasper on 27-5-2015.
 */
public interface LoadingObserver {

	void update(LoadingObservable loadingObservable, Object arguments);
}
