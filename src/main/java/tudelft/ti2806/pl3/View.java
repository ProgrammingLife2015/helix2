package tudelft.ti2806.pl3;

import java.awt.Component;

/**
 * Interface for all the views used in the application.
 * Created by Kasper on 27-5-2015.
 */
public interface View {

	/**
	 * Getter for the view itself.
	 *
	 * @return Component
	 */
	Component getPanel();

	/**
	 * Getter for the controller of the view.
	 *
	 * @return Controller
	 */
	Controller getController();

}
