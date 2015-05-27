package tudelft.ti2806.pl3.sidebar;

import newick.NewickParser;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.Component;
import java.util.ArrayList;

/**
 * Controller that controls the sidebar. The user can select options in the
 * sidebar. Created by Kasper on 7-5-15.
 */
public class SideBarController implements Controller{

	private SideBarView sideBarView;

	/**
	 * Construct the side bar controller and add all filters to its view.
	 *
	 * @param graphController
	 * 		the graph controller
	 * @param tree
	 * 		the newick tree
	 */
	public SideBarController(SideBarView sideBarView, GraphController graphController) {
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
