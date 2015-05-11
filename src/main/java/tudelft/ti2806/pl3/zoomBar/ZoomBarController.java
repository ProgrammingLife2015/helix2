package tudelft.ti2806.pl3.zoomBar;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.Component;

/**
 * Controller that controls the zoom bar at the bottom of the screen.
 * The zoom bar is used to navigate through and zoom in on the graph.
 * Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarController implements Controller {

	public GraphController graphController;
	private ZoomBarView zoomBarView;

	/**
	 * Construct a new controller for the zoom bar.
	 * @param graphController instance of the graph controller to update its view
	 */
	@SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
	public ZoomBarController(GraphController graphController) {
		this.graphController = graphController;
		zoomBarView = new ZoomBarView();
	}

	/**
	 * Get the zoom bar view.
	 * @return the view
	 */
	@Override
	public Component getPanel() {
		return zoomBarView;
	}
}
