package tudelft.ti2806.pl3.zoomBar;

import tudelft.ti2806.pl3.ScreenSize;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * The view for the zoom bar. The zoom bar is used to navigate through and zoom
 * in on the graph. Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarView extends JPanel {
	public static final double ZOOMBAR_FACTOR = 0.1;
	
	/**
	 * Construct a zoom bar view with a fixed height.
	 */
	public ZoomBarView() {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth(),
				ScreenSize.getInstance().getZoombarHeight()));
	}
}
