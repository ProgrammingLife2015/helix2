package tudelft.ti2806.pl3.zoombar;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.visualization.GraphLoadedListener;
import tudelft.ti2806.pl3.visualization.GraphMovedListener;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Controller that controls the zoom bar at the bottom of the screen.
 * The zoom bar is used to navigate through and zoom in on the graph.
 * Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarController implements Controller, GraphMovedListener, GraphLoadedListener {

	private final ZoomBarView zoomBarView;
	private final ControllerContainer cc;

	/**
	 * Construct a new controller for the zoom bar.
	 *
	 * @param cc
	 * 		reference to all controllers
	 */
	@SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
	public ZoomBarController(ControllerContainer cc) {
		this.cc = cc;
		zoomBarView = new ZoomBarView();
		zoomBarView.addMouseListener(new ZoomBarMouseClicked());
		zoomBarView.addMouseMotionListener(new ZoomBarMouseDragged());
		cc.getGraphController().addGraphMovedListener(this);
		cc.getGraphController().getGraphView().addGraphLoadedListener(this);
		cc.getGraphController().getFilteredObservable().addObserver(zoomBarView);
	}

	/**
	 * When the graph was moved, update the view.
	 */
	@Override
	public void graphMoved() {
		updateView();
	}

	/**
	 * When the graph was loaded.
	 */
	@Override
	public void graphLoaded() {
		updateView();
	}

	private void updateView() {
		zoomBarView.setMaxInterest(cc.getGraphController().getMaxInterest());
		zoomBarView.setInterest(cc.getGraphController().getInterest());
		zoomBarView.setZoomCenter(cc.getGraphController().getCurrentZoomCenter());
		zoomBarView.setViewPercent(cc.getGraphController().getViewPercent());
		zoomBarView.setGraphWidth((float) cc.getGraphController().getGraphDimension());
		zoomBarView.setOffsetToCenter(cc.getGraphController().getOffsetToCenter());
		zoomBarView.moved();
	}

	/**
	 * Navigate to the graph on the given mouse x position.
	 *
	 * @param mouseX
	 * 		The mouse x position
	 */
	private void navigateInGraph(float mouseX) {
		double size = cc.getGraphController().getGraphView().getGraphDimension();
		float factor = (float) mouseX
				/ (float) ScreenSize.getInstance().getWidth();
		float newPos = (float) (factor * size);
		cc.getGraphController().moveView(newPos);
	}

	/**
	 * Class that handles the mouse click.
	 */
	private class ZoomBarMouseClicked extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			navigateInGraph(e.getX());
		}
	}

	/**
	 * Class that handles the mouse drag.
	 */
	private class ZoomBarMouseDragged extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			navigateInGraph(e.getX());
		}
	}

	public Component getPanel() {
		return zoomBarView;
	}

}
