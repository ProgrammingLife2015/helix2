package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * ScrollListener handles the events when the user used the scrollwheel on his mouse.
 * Created by Kasper on 30-5-2015.
 */
public class ScrollListener implements MouseWheelListener {

	private GraphController graphController;


	/**
	 * Constructor a Listener.
	 *
	 * @param app
	 * 		application to control
	 */
	public ScrollListener(Application app) {
		graphController = app.getGraphController();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int mouseScroll = e.getWheelRotation();
		if (mouseScroll < 0) {
			// user scrolls upward.
			graphController.zoomLevelUp();

		} else {
			// user scrolls downward
			graphController.zoomLevelDown();
		}

	}
}
