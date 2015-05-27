package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;

/**
 * Controller for menubar view
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController {

	private Application application;

	public MenuBarController(Application application) {
		this.application = application;
	}

	public void stop(){
		application.stop();
	}

}
