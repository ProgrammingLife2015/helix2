package tudelft.ti2806.pl3.sidebar;

import newick.NewickParser;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.sidebar.genomeFilter.GenomeFilterController;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.Component;
import java.util.ArrayList;

/**
 * Controller that controls the sidebar. The user can select options in the
 * sidebar. Created by Kasper on 7-5-15.
 */
public class SideBarController implements Controller {

	private SideBarView sideBarView;
	private GenomeFilterController genomeFilterController;
	private PhyloController phyloController;

	/**
	 * Construct the side bar controller and add all filters to its view.
	 * @param graphController
	 *          the graph controller
	 * @param gd
	 *          the graph data
	 */
	public SideBarController(GraphController graphController,
	                         GraphDataRepository gd, NewickParser.TreeNode tree) {
		genomeFilterController = new GenomeFilterController(
				graphController, gd);
		phyloController = new PhyloController(graphController,tree);

		ArrayList<Component> viewList = new ArrayList<>();
		//viewList.add(genomeFilterController.getPanel());
		viewList.add(phyloController.getPanel());

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
