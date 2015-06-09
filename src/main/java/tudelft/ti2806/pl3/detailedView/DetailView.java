package tudelft.ti2806.pl3.detailedView;

import java.awt.TextArea;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Created by Mathieu Post on 8-6-15.
 */
public class DetailView extends JPanel {


	public DetailView() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		add(new TextArea("hoi"));
		setBounds(0, 0, 100, 100);
	}


}
