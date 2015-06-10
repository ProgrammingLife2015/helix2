package tudelft.ti2806.pl3.zoomBar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

/**
 * The view for the zoom bar. The zoom bar is used to navigate through and zoom
 * in on the graph. Created by Boris Mattijssen on 06-05-15.
 */
@SuppressWarnings("serial")
public class ZoomBarView extends JPanel implements View, ComponentListener {
	public static final double ZOOMBAR_FACTOR = 0.1;
	private ZoomBarController zoomBarController;
	private GraphController graphController;

	private int x = 0;
	private int width = 100;
	
	/**
	 * Construct a zoom bar view with a fixed height.
	 */
	public ZoomBarView(GraphController graphController) {
		this.setLayout(new BorderLayout());
		this.graphController = graphController;
		setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth(),
				ScreenSize.getInstance().getZoombarHeight()));
		zoomBarController = new ZoomBarController(this,graphController);

	}

	protected void paintComponent(Graphics g) {
		g.drawRect(x,0,width,ScreenSize.getInstance().getZoombarHeight());
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public ZoomBarController getController() {
		return zoomBarController;
	}

	public void moved() {
		float fraction = graphController.getCurrentZoomCenter() / (float) graphController.getGraphView().getGraphDimension();
		x = (int) (fraction * ScreenSize.getInstance().getWidth());
		width = (int) (graphController.getGraphView().getViewPercent() * ScreenSize.getInstance().getWidth());
		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		moved();
	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}
}
