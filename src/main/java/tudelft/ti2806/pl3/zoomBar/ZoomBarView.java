package tudelft.ti2806.pl3.zoomBar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * The view for the zoom bar. The zoom bar is used to navigate through and zoom
 * in on the graph. Created by Boris Mattijssen on 06-05-15.
 */
@SuppressWarnings("serial")
public class ZoomBarView extends JPanel implements View{
	public static final double ZOOMBAR_FACTOR = 0.1;
	private ZoomBarController zoomBarController;
	
	/**
	 * Construct a zoom bar view with a fixed height.
	 */
	public ZoomBarView(GraphController graphController) {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth(),
				ScreenSize.getInstance().getZoombarHeight()));
		zoomBarController = new ZoomBarController(this,graphController);
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public ZoomBarController getController() {
		return zoomBarController;
	}
}
