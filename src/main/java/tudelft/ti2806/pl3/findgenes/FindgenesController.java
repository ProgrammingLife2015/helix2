package tudelft.ti2806.pl3.findgenes;

import tudelft.ti2806.pl3.data.gene.Gene;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;
import tudelft.ti2806.pl3.visualization.GraphController;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This controller controls the view that lets you select a gene from a list, and will navigate you to it in the graph.
 * Created by Boris Mattijssen on 30-05-15.
 */
public class FindgenesController {

	private final AbstractGraphData graphData;
	private final FindgenesView findgenesView;
	private final GraphController graphController;
	private Object previousSelected;
	private JFrame frame;

	/**
	 * Construct the controller and instantiate the view.
	 *
	 * @param graphData
	 * 		Contains the gene data
	 * @param graphController
	 * 		Needed to navigate in the graph
	 */
	public FindgenesController(AbstractGraphData graphData, GraphController graphController) {
		findgenesView = new FindgenesView(graphData.getGenes().toArray(new Gene[graphData.getGenes().size()]));
		this.graphController = graphController;
		this.graphData = graphData;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Opens the dialog which lets the user select a gene.<br>
	 * After a gene is selected, it will do a call to the {@link GraphController} to navigate to the start of that
	 * gene.
	 */
	public void openDialog() {
		JOptionPane.showMessageDialog(frame, findgenesView, "Select a gene:", JOptionPane.QUESTION_MESSAGE);

		if (previousSelected != findgenesView.getSelectedItem()) {
			boolean tryAgain = false;
			try {
				Gene selected = (Gene) findgenesView.getSelectedItem();
				DataNode node = graphData.getGeneToStartNodeMap().get(selected);
				if (node == null) {
					JOptionPane.showMessageDialog(frame,
							"Couldn't find the selected gene. Try again");
					tryAgain = true;
				} else {
					graphController.centerOnNode(node);
				}
			} catch (ClassCastException e) {
				JOptionPane.showMessageDialog(frame, "Please select an existing gene. Try again");
				tryAgain = true;
			} catch (NodeNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't find the node on the graph. Try again");
				tryAgain = true;
			}
			if (tryAgain == true) {
				openDialog();
			}
		}
	}
}
