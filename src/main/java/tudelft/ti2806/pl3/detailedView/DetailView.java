package tudelft.ti2806.pl3.detailedView;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This view is added when more details about a node are needed.
 * @author mathieu
 */
public class DetailView extends JPanel {

	/**
	 * Constructs a DetailView and set a border and the preferred layout.
	 */
	public DetailView() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
		for (Genome genome : node.getGenome()) {
			JLabel label = new JLabel(genome.toString());
			add(label);
		}

		Dimension size = getPreferredSize();
		setBounds(x, y, size.width, size.height);
	}
}
