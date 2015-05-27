package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.Controller;

/**
 * Controller that controls the sidebar. The user can select options in the
 * sidebar. Created by Kasper on 7-5-15.
 */
public class SideBarController implements Controller {

	private SideBarView sideBarView;

	/**
	 * Construct the side bar controller and add all filters to its view.
	 *
	 * @param sideBarView
	 * 		the view that is controlled
	 *
	 */
	public SideBarController(SideBarView sideBarView) {
		this.sideBarView = sideBarView;
	}

	/**
	 * Show the sidebar if hidden, hide if shown.
	 */
	public void toggleSideBar() {
		if (sideBarView.isVisible()) {
			sideBarView.setVisible(false);
		} else {
			sideBarView.setVisible(true);
		}
	}
}
