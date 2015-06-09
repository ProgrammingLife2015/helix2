package tudelft.ti2806.pl3.detailedView;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.awt.TextArea;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Created by Mathieu Post on 8-6-15.
 */
public class DetailView extends JPanel {
	private WrapperClone node;

	public DetailView() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		setBounds(0, 0, 100, 100);
	}

	public void setNode(WrapperClone node) {
		this.node = node;
		removeAll();
		for (Genome genome : node.getGenome()) {
			add(new TextArea(genome.toString()));
		}
	}
}
