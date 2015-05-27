package tudelft.ti2806.pl3;

<<<<<<< HEAD
import java.util.ArrayList;

=======
>>>>>>> PMD fix + loading screen start-up
/**
 * Interface for the loading Observable.
 * Created by Kasper on 27-5-2015.
 */
public interface LoadingObservable {
<<<<<<< HEAD
	/**
	 * Add a loadingObserver to the Observable.
	 *
	 * @param loadingObserver
	 * 		observer to add
	 */
	void addLoadingObserver(LoadingObserver loadingObserver);


	/**
	 * Add multiple loadingObserver to the Observable.
	 *
	 * @param loadingObservers
	 * 		observers to add
	 */
	void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers);

	/**
	 * Delete the loadingObserver.
	 *
	 * @param loadingObserver
	 * 		observer to remove
	 */
	void deleteLoadingObserver(LoadingObserver loadingObserver);

	/**
	 * Notify all the loadingObservers with a message.
	 *
	 * @param arguments
	 * 		to give to the Observer
	 */
=======

	void addLoadingObserver(LoadingObserver loadingObservable);

	void deleteLoadingObserver(LoadingObserver loadingObservable);

>>>>>>> PMD fix + loading screen start-up
	void notifyLoadingObservers(Object arguments);

}
