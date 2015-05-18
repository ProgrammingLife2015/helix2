package tudelft.ti2806.pl3.sidebar.genomeFilter;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Controller that controls the genome filter.
 * Created by Boris Mattijssen on 12-05-15.
 */
public class GenomeFilterController implements Controller, ActionListener {

	private final GraphController graphController;
	GenomeFilterView view;

	/**
	 * Construct the controller.
	 * @param graphController
	 *          the @GraphController
	 * @param gd
	 *          the data repository containing all genomes
	 */
	public GenomeFilterController(GraphController graphController, GraphDataRepository gd) {
		this.graphController = graphController;
		view = new GenomeFilterView(this, gd.getGenomes());
	}

	/**
	 * Get the controller's view.
	 * @return
	 *          the view
	 */
	@Override
	public Component getPanel() {
		return view;
	}


	/**
	 * When the button is clicked on the view.
	 * @param event
	 *          the click event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		graphController.addFilter("genome", new GenomeFilter(view.getSelected()));
	}
}
