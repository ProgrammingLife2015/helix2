package tudelft.ti2806.pl3;

/**
 * Interface for the loading Observable.
 * Created by Kasper on 27-5-2015.
 */
public interface LoadingObservable {

	void addLoadingObserver(LoadingObserver loadingObservable);

	void deleteLoadingObserver(LoadingObserver loadingObservable);

	void notifyLoadingObservers(Object arguments);

}
