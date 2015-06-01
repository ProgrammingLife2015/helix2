package tudelft.ti2806.pl3.findgenes;

import tudelft.ti2806.pl3.data.gene.Gene;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;
import tudelft.ti2806.pl3.visualization.GraphController;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Created by Boris Mattijssen on 30-05-15.
 */
public class FindgenesController {

	private final AbstractGraphData graphData;
	private final FindgenesView findgenesView;
	private final GraphController graphController;
	private Object previousSelected;
	private JFrame frame;

	public FindgenesController(AbstractGraphData graphData, GraphController graphController) {
		findgenesView = new FindgenesView(graphData.getGenes().toArray(new Gene[graphData.getGenes().size()]));
		this.graphController = graphController;
		this.graphData = graphData;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void openDialog() {
		JOptionPane.showMessageDialog(frame, findgenesView, "Select a gene:", JOptionPane.QUESTION_MESSAGE);

		if (previousSelected != findgenesView.getSelectedItem()) {
			boolean tryAgain = false;
			try {
				Gene selected = (Gene) findgenesView.getSelectedItem();
				DataNode node = graphData.getGeneToStartNodeMap().get(selected);
				if (node == null) {
					JOptionPane.showMessageDialog(frame, "Couldn't find the selected gene. Try again");
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
