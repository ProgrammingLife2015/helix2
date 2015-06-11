package tudelft.ti2806.pl3.detailedView;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.label.Label;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Set;
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

		add(new JLabel("Genomes:"));
		for (Genome genome : node.getGenome()) {
			JLabel label = new JLabel(genome.toString());
			add(label);
		}

		Set<Label> labels = node.getLabels();
		if (labels.size() > 0) {
			JLabel jLabel = new JLabel("Labels:");
			jLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			add(jLabel);
			for (Label label : labels) {
				add(new JLabel(label.getText()));
			}
		}

		Dimension size = getPreferredSize();
		setBounds(x, y, size.width, size.height);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		Container parent = getParent();
		if (parent != null) {
			int parentWidth = parent.getWidth();
			int parentHeight = parent.getHeight();

			width = Math.min(width, parentWidth);
			height = Math.min(height, parentHeight);

			if (x > parentWidth - width) {
				x -= width + 30;
			}
			y = Math.min(y, parentHeight - height);
		}
		super.setBounds(x, y, width, height);
	}
}
