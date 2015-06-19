package tudelft.ti2806.pl3.findgenes;

import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.data.gene.Gene;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;
import tudelft.ti2806.pl3.ui.util.DialogUtil;
import tudelft.ti2806.pl3.visualization.GraphController;

/**
 * This controller controls the view that lets you select a gene from a list, and will navigate you to it in the graph.
 * Created by Boris Mattijssen on 30-05-15.
 */
public class FindGenesController {

	private final AbstractGraphData graphData;
	private final ControllerContainer cc;
	private Object previousSelected;

	/**
	 * Construct the controller and instantiate the view.
	 *
	 * @param cc
	 * 		Reference to all controllers
	 * @param graphData
	 * 		Contains the gene data
	 */
	public FindGenesController(ControllerContainer cc, GraphDataRepository graphData) {
		this.cc = cc;
		this.graphData = graphData;
	}


	/**
	 * Opens the dialog which lets the user select a gene.<br>
	 * After a gene is selected, it will do a call to the {@link GraphController} to navigate to the start of that
	 * gene.
	 */
	public void openDialog() {
		FindGenesView findGenesView = new FindGenesView(
				graphData.getGenes().toArray(new Gene[graphData.getGenes().size()]));
		DialogUtil.displayQuestionMessageWithView(findGenesView,"Select a gene:");

		if (previousSelected != findGenesView.getSelectedItem()) {
			boolean tryAgain = false;
			previousSelected = findGenesView.getSelectedItem();
			try {
				Gene selected = (Gene) findGenesView.getSelectedItem();
				DataNode node = graphData.getGeneToStartNodeMap().get(selected);
				if (node == null) {
					tryAgain = DialogUtil.confirm("Error!", "Couldn't find the selected gene. Please try again");
				} else {
					cc.getGraphController().centerOnNode(node);
				}
			} catch (ClassCastException e) {
				tryAgain = DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, "Please select an existing gene.");
			} catch (NodeNotFoundException e) {
				tryAgain = DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR,
						"Couldn't find the node on the graph. Please try again.");
			}
			if (tryAgain) {
				openDialog();
			}
		}
	}
}
