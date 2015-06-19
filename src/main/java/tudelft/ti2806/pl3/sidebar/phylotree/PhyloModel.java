package tudelft.ti2806.pl3.sidebar.phylotree;

import newick.NewickParser;

import java.util.Observable;

/**
 * Created by Boris Mattijssen on 14-06-15.
 */
class PhyloModel extends Observable {

	private NewickParser.TreeNode tree;

	public PhyloModel() {
		tree = new NewickParser.TreeNode();
	}

	public NewickParser.TreeNode getTree() {
		return tree;
	}

	/**
	 * Set tree and notify listeners.
	 *
	 * @param tree
	 * 		the tree
	 */
	public void setTree(NewickParser.TreeNode tree) {
		this.tree = tree;
		setChanged();
		notifyObservers();
	}
}
