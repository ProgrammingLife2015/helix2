package tudelft.ti2806.pl3.detailview;

import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * This view is added when more details about a node are needed.
 * @author mathieu
 */
public class DetailView extends JPanel {
	private static final int BORDER = 10;
	private static final int POSITION_OFFSET = 30;
	private static final String REF_GENOME = "REF";
	private static final int MAX_BASEPAIR_LENGTH = 10;

	/**
	 * Custom string comparator to prioritize the reference genome.
	 */
	private final Comparator<String> comparator = (o1, o2) -> {
		if (o1.contains(REF_GENOME)) {
			return -1;
		} else if (o2.contains(REF_GENOME)) {
			return 1;
		}
		return o1.compareTo(o2);
	};

	/**
	 * Constructs a DetailView and set a border and the preferred layout.
	 */
	public DetailView() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(0, BORDER, BORDER, BORDER));
	}

	/**
	 * Set about which node the DetailView shows some details.
	 * @param node
	 * 		the node to show details about.
	 * @param x
	 * 		x position of the view.
	 * @param y
	 * 		y position of the view.
	 */
	public void setNode(WrapperClone node, int x, int y) {
		removeAll();

		addLabels(node);
		addList(node.getGenome(), Constants.DETAILVIEW_GENOMES);
		addList(node.getLabels(), Constants.DETAILVIEW_LABELS);

		Dimension size = getPreferredSize();
		setBounds(x, y, size.width, size.height);
	}

	/**
	 * Adds labels for the node id and the base pair string if the node can not be unwrapped.
	 * @param node
	 * 		The node for which the id and base pair string should be added.
	 */
	public void addLabels(WrapperClone node) {
		if (!node.canUnwrap()) {
			JLabel label = new JLabel("Node id: " + node.getIdString());
			label.setBorder(BorderFactory.createEmptyBorder(BORDER, 0, BORDER, 0));
			add(label);
			String basePairString = node.getBasePairString();
			int length = basePairString.length();
			if (length > MAX_BASEPAIR_LENGTH) {
				basePairString = basePairString.substring(0, MAX_BASEPAIR_LENGTH / 2) + "..."
						+ basePairString.substring(length - MAX_BASEPAIR_LENGTH / 2);
			}
			add(new JLabel("Base pairs:"));
			add(new JLabel(basePairString));
		}
	}

	/**
	 * Adds a collection as a scrollable list to itself.
	 * @param collection
	 * 		the collection to be added as list.
	 * @param title
	 * 		if not null, this string will be added above the list as header.
	 */
	private void addList(Collection collection, String title) {
		if (collection.size() == 0) {
			return;
		}
		Set<String> stringSet = new HashSet<>(collection.size());
		Iterator iterator = collection.iterator();
		for (int i = 0; i < collection.size(); i++) {
			stringSet.add(iterator.next().toString());
		}
		String[] array = stringSet.toArray(new String[stringSet.size()]);
		Arrays.sort(array, comparator);
		JList<Object> list = new JList<>(array);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(list);
		if (title != null) {
			listScroller.setColumnHeaderView(new JLabel(title));
		}
		listScroller.setBorder(BorderFactory.createEmptyBorder(BORDER, 0, 0, 0));
		add(listScroller);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		Container parent = getParent();
		x += POSITION_OFFSET;
		if (parent != null) {
			int parentWidth = parent.getWidth();
			int parentHeight = parent.getHeight();

			width = Math.min(width, parentWidth);
			height = Math.min(height, parentHeight);

			if (x > parentWidth - width) {
				x -= width + 2 * POSITION_OFFSET;
			}
			y = Math.min(y, parentHeight - height);
		}
		super.setBounds(x, y, width, height);
	}
}
