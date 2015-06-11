package tudelft.ti2806.pl3.zoomBar;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.visualization.GraphMovedListener;

/**
 * Controller that controls the zoom bar at the bottom of the screen.
 * The zoom bar is used to navigate through and zoom in on the graph.
 * Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarController implements Controller, GraphMovedListener {

	private GraphController graphController;
	private ZoomBarView zoomBarView;

	/**
	 * Construct a new controller for the zoom bar.
	 *
	 * @param graphController
	 * 		instance of the graph controller to update its view
	 */
	@SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
	public ZoomBarController(ZoomBarView zoomBarView, GraphController graphController) {
		this.graphController = graphController;
		this.zoomBarView = zoomBarView;
	}

	public GraphController getGraphController() {
		return graphController;
	}

	public ZoomBarView getZoomBarView() {
		return zoomBarView;
	}

	public void graphMoved() {
		zoomBarView.moved();
	}
}
