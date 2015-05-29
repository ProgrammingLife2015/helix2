package tudelft.ti2806.pl3.sidebar.phylotree;

import newick.NewickParser;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Phylo controller controls the phylogentic tree view.
 * Created by Kasper on 20-5-2015.
 */
public class PhyloController implements Controller,ActionListener {

	private PhyloView view;
	private GraphController graphController;

	public PhyloController(PhyloView view,GraphController graphController) {
		this.view = view;
		this.graphController = graphController;
	}

	/**
	 * Parse the tree file.
	 *
	 * @return Root node.
	 */
	public DefaultMutableTreeNode parseTree(NewickParser.TreeNode tree) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				"Phylogentic tree");
		parseChilds(tree, root);

		return root;
	}

	/**
	 * Parse the childs of a node. This method is recursive and constructs the
	 * whole tree.
	 *
	 * @param node
	 * 		tree file root
	 * @param root
	 * 		JTree root
	 */
	private void parseChilds(NewickParser.TreeNode node,
							 DefaultMutableTreeNode root) {
		for (NewickParser.TreeNode child : node.getChildren()) {
			DefaultMutableTreeNode childNode;
			if (child.getName() == null) {
				childNode = new DefaultMutableTreeNode("Common ancestor");
			} else {
				childNode = new DefaultMutableTreeNode(child.getName());
			}
			root.add(childNode);
			parseChilds(child, childNode);
		}
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

	/**
	 * Expand the tree.
	 */
	public void expandTree() {
		for (int i = 0; i < view.getjTree().getRowCount(); i++) {
			view.getjTree().expandRow(i);
		}
	}
}
