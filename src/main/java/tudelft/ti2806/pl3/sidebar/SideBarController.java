package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.LoadingObserver;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;

import java.awt.Component;
import java.util.ArrayList;

/**
 * Controller that controls the sidebar. The user can select options in the
 * sidebar. Created by Kasper on 7-5-15.
 */
public class SideBarController implements Controller {

	private SideBarView sideBarView;
	private PhyloController phyloController;

	/**
	 * Construct the side bar controller and add all filters to its view.
	 */
	public SideBarController(ControllerContainer cc) {
		sideBarView = new SideBarView();
		phyloController = new PhyloController(cc);
		sideBarView.add(phyloController.getPanel());
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

	public void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers) {
		phyloController.addLoadingObserversList(loadingObservers);
	}

	public Component getPanel() {
		return sideBarView.getPanel();
	}

	public PhyloController getPhyloController() {
		return phyloController;
	}
}
