package tudelft.ti2806.pl3.sidebar.phylotree;

import newick.NewickParser;
import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;
import tudelft.ti2806.pl3.util.Resources;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * PhyloView is a view for the phylogenetic file. The user can select multiple
 * genomes or common ancestors.
 *
 * <p>
 * Created by Kasper on 20-5-2015.
 */
public class PhyloView extends JPanel implements View, Observer {

	private static final String ICON_BACTERIA = "pictures/bacteria_small.jpg";

	private JTree jTree;
	private List<String> selected = new ArrayList<>();

	private JScrollPane scroller;
	private JLabel header;
	private PhyloModel phyloModel;

	JButton button;
	private JLabel emptyLabel = new JLabel("Please load a .nwk file");

	/**
	 * Phylo view constructs a Jtree object with our .nwk tree file.
	 */
	public PhyloView(PhyloModel phyloModel) {
		this.phyloModel = phyloModel;
		jTree = new JTree(new DefaultMutableTreeNode());
		setUpUserInterface();
		setUpLook();
		setListener();
	}


	/**
	 * Setup the UI of the view.
	 */
	private void setUpUserInterface() {
		final int width = ScreenSize.getInstance().getSideBarWidth() - 10;
		final int height = ScreenSize.getInstance().getHeight() - 100;

		header = new JLabel(Constants.PHYLO_WINDOW_TITLE);
		header.setPreferredSize(new Dimension(width, 50));

		scroller = new JScrollPane(jTree,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setPreferredSize(new Dimension(width, (int) (height / 1.1)));
		scroller.setMinimumSize(new Dimension(width, height));
		scroller.setVisible(false);

		this.add(Box.createHorizontalGlue());
		this.add(header);
		add(Box.createVerticalGlue());
		this.add(scroller);
		this.add(emptyLabel);
		add(Box.createVerticalGlue());

		button = new JButton(Constants.PHYLO_BUTTON_LABEL_UPDATE);
		button.setVisible(false);
		this.add(button);
		button.setPreferredSize(new Dimension(200, 50));
		setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Set the icons of the JTree.
	 */
	private void setUpLook() {
		ImageIcon childIcon = new ImageIcon(Resources.getResource(ICON_BACTERIA));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(childIcon);
		jTree.setCellRenderer(renderer);
	}

	/**
	 * Parse the tree file.
	 *
	 * @return Root node.
	 */
	private DefaultMutableTreeNode convertTree(NewickParser.TreeNode tree) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(Constants.PHYLO_LABEL_PHYLOGENETIC_TREE);
		convertChildren(tree, root);

		return root;
	}

	/**
	 * Convert the children of a node. This method is recursive and constructs the
	 * whole tree.
	 *
	 * @param tree
	 * 		tree file root
	 * @param root
	 * 		JTree root
	 */
	private void convertChildren(NewickParser.TreeNode tree, DefaultMutableTreeNode root) {
		for (NewickParser.TreeNode child : tree.getChildren()) {
			DefaultMutableTreeNode childNode;
			if (child.getName() == null) {
				childNode = new DefaultMutableTreeNode(Constants.PHYLO_LABEL_COMMON_ANCESTOR);
			} else {
				childNode = new DefaultMutableTreeNode(child.getName());
			}
			root.add(childNode);
			convertChildren(child, childNode);
		}
	}

	/**
	 * Expand the tree.
	 */
	private void expandTree() {
		for (int i = 0; i < jTree.getRowCount(); i++) {
			jTree.expandRow(i);
		}
	}

	/**
	 * Update the sizes of the panes relative to the application size.
	 */
	public void updateSize() {
		int width = ScreenSize.getInstance().getSideBarWidth() - 10;
		int height = ScreenSize.getInstance().getHeight() - 100;

		scroller.setPreferredSize(new Dimension(width, (int) (height / 1.1)));
		header.setPreferredSize(new Dimension(width, 50));
	}


	/**
	 * Set up the listener for clicking on the phylogentic tree.
	 */
	public void setListener() {
		jTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		jTree.getSelectionModel().addTreeSelectionListener(new TreeClassListener());
	}

	@Override
	public void update(Observable o, Object arg) {
		DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		model.setRoot(convertTree(phyloModel.getTree()));
		model.reload();
		expandTree();
		emptyLabel.setVisible(false);
		scroller.setVisible(true);
		button.setVisible(true);
	}

	public void addButtonListener(ActionListener listener) {
		button.addActionListener(listener);
	}

	/**
	 * Listener that listens for the tree selection.
	 */
	private class TreeClassListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
			TreePath[] treePath = jTree.getSelectionPaths();
			if (treePath != null) {
				for (TreePath path : treePath) {
					DefaultMutableTreeNode select = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					String selectName = select.toString();
					if (selectName.equals(Constants.PHYLO_LABEL_COMMON_ANCESTOR)
							|| selectName.equals(Constants.PHYLO_LABEL_PHYLOGENETIC_TREE)) {
						selected.addAll(getChildsOfAncestor(select));
					} else {
						selected.add(select.toString());
					}
				}
			}
		}
	}

	/**
	 * Return the genomes that are selected.
	 *
	 * @return selected genomes
	 */
	public List<String> getSelected() {
		return selected;
	}

	/**
	 * Resets the selected genomes.
	 */
	public void resetSelected() {
		selected = new ArrayList<>();
	}
	
	/**
	 * Get the genomes of a Common ancestor.
	 *
	 * @param name
	 * 		of node that is selected.
	 * @return All the genomes of the common ancestor.
	 */
	private List<String> getChildsOfAncestor(DefaultMutableTreeNode name) {
		List<String> selected = new ArrayList<>();
		Enumeration children = name.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode next = (DefaultMutableTreeNode) children
					.nextElement();
			if (next.toString().equals(Constants.PHYLO_LABEL_COMMON_ANCESTOR)) {
				selected.addAll(getChildsOfAncestor(next));
			} else {
				selected.add(next.toString());
			}
		}
		return selected;
	}

	/**
	 * Return the panel that displays the JTree.
	 *
	 * @return the panel of the view
	 */
	@Override
	public Component getPanel() {
		return this;
	}
}

