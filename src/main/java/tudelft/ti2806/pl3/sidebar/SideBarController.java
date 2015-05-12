package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.sidebar.genomeFilter.GenomeFilterController;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.Component;
import java.util.ArrayList;

/**
 * Controller that controls the sidebar. The user can select options in the
 * sidebar. Created by Kasper on 7-5-15.
 */
public class SideBarController implements Controller {
	
	public GraphController graphController;
	private SideBarView sideBarView;
	
	public SideBarController(GraphController graphController) {
		this.graphController = graphController;
		GenomeFilterController genomeFilterController = new GenomeFilterController();
		ArrayList<Component> viewList = new ArrayList<>();
		viewList.add(genomeFilterController.getPanel());
		sideBarView = new SideBarView(viewList);
	}
	
	@Override
	public Component getPanel() {
		return sideBarView;
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
