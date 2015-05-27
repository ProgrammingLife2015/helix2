package tudelft.ti2806.pl3.loading;

import tudelft.ti2806.pl3.LoadingObservable;
import tudelft.ti2806.pl3.LoadingObserver;

import javax.swing.JDialog;

/**
 * Created by Kasper on 27-5-2015.
 */
public class LoadingScreen extends JDialog implements LoadingObserver {


	@Override
	public void update(LoadingObservable loadingObservable, Object arguments) {
		System.out.println(arguments);
	}




}
