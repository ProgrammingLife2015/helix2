package tudelft.ti2806.pl3.zoomBar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.visualization.GraphView;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * The view for the zoom bar. The zoom bar is used to navigate through and zoom
 * in on the graph. Created by Boris Mattijssen on 06-05-15.
 */
@SuppressWarnings("serial")
public class ZoomBarView extends JPanel implements View, ComponentListener, Observer {
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
		graphController.getFilteredObservable().addObserver(this);
	}

	/**
	 * Draw the box indicating where you are on the graph.
	 *
	 * @param g
	 * 		graphics
	 */
	protected void paintComponent(Graphics g) {
		drawHighlightBox(g);
		drawInterest(g);
	}

	private void drawInterest(Graphics g) {
		int height = getPreferredSize().height;
		float max = graphController.getMaxInterest();
		int i = 0;
		for (float v : graphController.getInterest()) {
			int lineHeight = (int) ((v/max) * (float) height);
			int alpha = (int) ((v/max)*255);
			g.setColor(new Color(255, 0, 0, alpha));
			g.drawLine(i, (height - lineHeight) / 2, i, (height - lineHeight) / 2 + lineHeight);
			i++;
		}
	}

	private void drawHighlightBox(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		int height = getPreferredSize().height;
		g.drawRect(x, 1, width, height-1);
		g2.setStroke(oldStroke);
	}

	/**
	 * When the graph was moved, the position of the square is recalculated.
	 */
	public void moved() {
		GraphView graphView = graphController.getGraphView();
		float zoomCenter = graphController.getCurrentZoomCenter() +
				graphView.getOffsetToCenter() * (float) graphView.getViewPercent();
		float fraction = zoomCenter / (float) graphView.getGraphDimension();
		x = (int) (fraction * ScreenSize.getInstance().getWidth());
		width = (int) (graphView.getViewPercent() * ScreenSize.getInstance().getWidth());
		repaint();
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public ZoomBarController getController() {
		return zoomBarController;
	}

	@Override
	public void update(Observable o, Object arg) {
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
