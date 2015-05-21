package tudelft.ti2806.pl3.sidebar.phylotree;

import newick.NewickParser;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.sidebar.SideBarViewInterface;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Created by Kasper on 20-5-2015.
 */
public class PhyloView extends JPanel implements SideBarViewInterface {

	private NewickParser.TreeNode tree;
	private JTree jtree;
	private List<String> selected = new ArrayList<>();

	public PhyloView(NewickParser.TreeNode tree, PhyloController phyloController) {
		this.tree = tree;
		jtree = new JTree(parseTree());

		int width = ScreenSize.getInstance().getSidebarWidth();
		int height = ScreenSize.getInstance().getHeight() / 2;
		jtree.setSize(width, height);
		setUpLook();
		expandTree();
		setListener();

		add(new JScrollPane(jtree));
		add(jtree);
		add(createButton(phyloController));
	}

	private void setUpLook() {
		ImageIcon childIcon = new ImageIcon("pictures/bacteria_small.jpg");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(childIcon);
		jtree.setCellRenderer(renderer);
	}

	private void expandTree() {
		for (int i = 0; i < jtree.getRowCount(); i++) {
			jtree.expandRow(i);
		}
	}

	private DefaultMutableTreeNode parseTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Phylogentic tree");
		parseChilds(tree, root);

		return root;
	}

	private void parseChilds(NewickParser.TreeNode node, DefaultMutableTreeNode root) {
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

	public void setListener() {
		jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		jtree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath[] treePath = jtree.getSelectionPaths();
				for (TreePath path : treePath) {
					DefaultMutableTreeNode select =
							(DefaultMutableTreeNode) path.getLastPathComponent();
					if (select.toString().equals("Common ancestor")) {
						selected.addAll(getChildsOfAncestor(select));
					} else {
						selected.add(select.toString());
					}
				}
			}
		});
	}

	public List<String> getSelected() {
		return selected;
	}


	private List<String> getChildsOfAncestor(DefaultMutableTreeNode name) {
		List<String> selected = new ArrayList<>();
		Enumeration children = name.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode next = (DefaultMutableTreeNode) children.nextElement();
			if (next.toString().equals("Common ancestor")) {
				selected.addAll(getChildsOfAncestor(next));
			} else {
				selected.add(next.toString());
			}
		}
		return selected;
	}

	/**
	 * Create the submit button.
	 *
	 * @param phyloController
	 * 		the controller which will be the action listener
	 * @return the submit button
	 */
	private JButton createButton(PhyloController phyloController) {
		JButton button = new JButton("Update");
		button.addActionListener(phyloController);
		return button;
	}

	@Override
	public Component getPanel() {
		return this;
	}
}
