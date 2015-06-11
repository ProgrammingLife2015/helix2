package tudelft.ti2806.pl3.zoomBar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.visualization.GraphView;

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
		zoomBarController = new ZoomBarController(this, graphController);

	}

	/**
	 * Draw the box indicating where you are on the graph.
	 *
	 * @param g
	 * 		graphics
	 */
	protected void paintComponent(Graphics g) {
		g.drawRect(x, 0, width, getPreferredSize().height);
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public ZoomBarController getController() {
		return zoomBarController;
	}

	/**
	 * When the graph was moved, the position of the square is recalculated.
	 */
	public void moved() {
		GraphView graphView = graphController.getGraphView();
		float zoomCenter = graphController.getCurrentZoomCenter() +
				(graphView.getOffsetToCenter() * (float) graphView.getViewPercent());
		float fraction = zoomCenter / (float) graphView.getGraphDimension();
		x = (int) (fraction * ScreenSize.getInstance().getWidth());
		width = (int) (graphView.getViewPercent() * ScreenSize.getInstance().getWidth());
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
