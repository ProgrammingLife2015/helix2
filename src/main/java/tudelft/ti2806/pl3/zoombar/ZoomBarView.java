package tudelft.ti2806.pl3.zoombar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * The view for the zoom bar. The zoom bar is used to navigate through and zoom
 * in on the graph. Created by Boris Mattijssen on 06-05-15.
 */
@SuppressWarnings("serial")
public class ZoomBarView extends JPanel implements View, Observer {
	public static final double ZOOMBAR_FACTOR = 0.1;

	private int x = 0;
	private int width = -1;

	private float maxInterest = 0;
	private float[] interest = new float[0];
	private float zoomCenter = 0;
	private float viewPercent = 0;
	private float graphWidth = 0;
	private float offsetToCenter = 0;

	/**
	 * Construct a zoom bar view with a fixed height.
	 */
	public ZoomBarView() {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth(),
				ScreenSize.getInstance().getZoombarHeight()));
	}

	/**
	 * Paint on the graphics.
	 *
	 * @param g
	 * 		graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawIndicator(g);
		drawInterest(g);
	}

	/**
	 * Draw interest values on the zoombar.
	 *
	 * @param g
	 * 		graphics
	 */
	private void drawInterest(Graphics g) {
		int height = getPreferredSize().height;
		int i = 0;
		for (float v : interest) {
			int lineHeight = (int) ((v / maxInterest) * height);
			int alpha = (int) ((v / maxInterest) * 255);
			g.setColor(new Color(255, 0, 0, alpha));
			g.drawLine(i, (height - lineHeight) / 2, i, (height - lineHeight) / 2 + lineHeight);
			i++;
		}
	}

	/**
	 * Draw the box that indicates your position on the graph.
	 *
	 * @param g
	 * 		graphics
	 */
	private void drawIndicator(Graphics g) {
		if (width > 0) {
			Graphics2D g2 = (Graphics2D) g;
			float thickness = 2;
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(thickness));
			int height = getPreferredSize().height;
			g.drawRect(x, 1, width, height - 1);
			g2.setStroke(oldStroke);
		}
	}

	/**
	 * When the graph was moved, the position of the square is recalculated.
	 */
	public void moved() {
		float zoomCenterWithOffset = zoomCenter	- offsetToCenter * viewPercent;
		float fraction = zoomCenterWithOffset / graphWidth;
		x = (int) (fraction * ScreenSize.getInstance().getWidth());
		width = (int) (viewPercent * ScreenSize.getInstance().getWidth());
		repaint();
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public void setMaxInterest(float maxInterest) {
		this.maxInterest = maxInterest;
	}

	public void setInterest(float[] interest) {
		this.interest = interest;
	}

	public void setZoomCenter(float zoomCenter) {
		this.zoomCenter = zoomCenter;
	}

	public void setViewPercent(float viewPercent) {
		this.viewPercent = viewPercent;
	}

	public void setGraphWidth(float graphWidth) {
		this.graphWidth = graphWidth;
	}

	public void setOffsetToCenter(float offsetToCenter) {
		this.offsetToCenter = offsetToCenter;
	}
}
