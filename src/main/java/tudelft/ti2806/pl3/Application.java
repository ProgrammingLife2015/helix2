package tudelft.ti2806.pl3;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * The main application view.
 *
 * Created by Boris Mattijssen on 07-05-15.
 */
public class Application extends JPanel {

	private JPanel right = new JPanel();

	/**
	 * Construct the main application view.
	 */
	public Application() {
		setLayout(new GridLayout());
		setPreferredSize(new Dimension(1800, 1000));
		JPanel left = new JPanel();
		right.setLayout(new BorderLayout());

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		jSplitPane.setDividerLocation(200);

		add(jSplitPane);
	}


	/**
	 * Add the graph view to the layout.
	 * @param view the graph view panel
	 */
	public void setGraphView(Component view) {
		right.add(view, BorderLayout.CENTER);
	}

	/**
	 * Add the zoom bar view to the layout.
	 * @param view the zoom bar view panel
	 */
	public void setZoomBarView(Component view) {
		right.add(view, BorderLayout.SOUTH);
	}
}
