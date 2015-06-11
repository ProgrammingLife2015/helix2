package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LastOpenedController is a ActionListener for the recent files submenu.
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedController implements ActionListener,Controller {
	public LastOpenedController() {
		super();
	}

	/**
	 * Is called when the user clicks on a file in the submenu.
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
}
