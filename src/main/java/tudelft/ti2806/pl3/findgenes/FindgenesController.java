package tudelft.ti2806.pl3.findgenes;

import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.data.gene.Gene;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;
import tudelft.ti2806.pl3.visualization.GraphController;

import javax.swing.JOptionPane;

/**
 * This controller controls the view that lets you select a gene from a list, and will navigate you to it in the graph.
 * Created by Boris Mattijssen on 30-05-15.
 */
public class FindgenesController {

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
	public FindgenesController(ControllerContainer cc, GraphDataRepository graphData) {
		this.cc = cc;
		this.graphData = graphData;
	}


	/**
	 * Opens the dialog which lets the user select a gene.<br>
	 * After a gene is selected, it will do a call to the {@link GraphController} to navigate to the start of that
	 * gene.
	 */
	public void openDialog() {
		FindgenesView findgenesView = new FindgenesView(
				graphData.getGenes().toArray(new Gene[graphData.getGenes().size()]));
		JOptionPane.showMessageDialog(null, findgenesView, "Select a gene:", JOptionPane.QUESTION_MESSAGE);

		if (previousSelected != findgenesView.getSelectedItem()) {
			boolean tryAgain = false;
			previousSelected = findgenesView.getSelectedItem();
			try {
				Gene selected = (Gene) findgenesView.getSelectedItem();
				DataNode node = graphData.getGeneToStartNodeMap().get(selected);
				if (node == null) {
					JOptionPane.showMessageDialog(null,
							"Couldn't find the selected gene. Try again");
					tryAgain = true;
				} else {
					cc.getGraphController().centerOnNode(node);
				}
			} catch (ClassCastException e) {
				JOptionPane.showMessageDialog(null, "Please select an existing gene. Try again");
				tryAgain = true;
			} catch (NodeNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Couldn't find the node on the graph. Try again");
				tryAgain = true;
			}
			if (tryAgain == true) {
				openDialog();
			}
		}
	}
}
