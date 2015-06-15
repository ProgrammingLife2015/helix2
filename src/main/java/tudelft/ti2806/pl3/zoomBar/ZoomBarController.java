package tudelft.ti2806.pl3.zoombar;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.visualization.GraphLoadedListener;
import tudelft.ti2806.pl3.visualization.GraphMovedListener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Controller that controls the zoom bar at the bottom of the screen.
 * The zoom bar is used to navigate through and zoom in on the graph.
 * Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarController implements Controller, GraphMovedListener, GraphLoadedListener, MouseListener {

	private ZoomBarView zoomBarView;
	private ControllerContainer cc;

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
		zoomBarView.addMouseListener(this);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		double size = cc.getGraphController().getGraphView().getGraphDimension();
		float factor = (float) e.getX()
				/ (float) ScreenSize.getInstance().getWidth();
		float newPos = (float) (factor * size);
		cc.getGraphController().moveView(newPos);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public Component getPanel() {
		return zoomBarView;
	}

}
