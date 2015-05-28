package tudelft.ti2806.pl3.sidebar.phylotree;

import newick.NewickParser;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Phylo controller controls the phylogentic tree view.
 * Created by Kasper on 20-5-2015.
 */
public class PhyloController implements Controller, ActionListener {

	private final GraphController graphController;
	private PhyloView view;

	public PhyloController(GraphController graphController, NewickParser.TreeNode tree) {
		this.graphController = graphController;
		view = new PhyloView(tree,this);
	}


	@Override
	public Component getPanel() {
		return view;
	}

	/**
	 * When the button is clicked on the view.
	 *
	 * @param event
	 * 		the click event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		graphController.addFilter("genome", new GenomeFilter(view.getSelected()));
		view.resetSelected();
	}
}
