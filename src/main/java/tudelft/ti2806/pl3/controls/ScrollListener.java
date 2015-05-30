package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.MouseInfo;
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

	/**
	 * Is called when the user scrolls his mousewheel.
	 * It first calculates where the user his mouse is in Graph Units.
	 * Then it increments or decrements the zoomlevel with the mouse position as zoomcenter.
	 *
	 * @param e
	 * 		{@link MouseWheelEvent} from the user
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int mouseScroll = e.getWheelRotation();

		double factor = MouseInfo.getPointerInfo().getLocation().getX() / graphController.getPanelWidth();

		double widthInGU = graphController.getGraphSizeInGraphUnits() * graphController.getViewPercent();
		double zoomPos = graphController.getCurrentGraphX() + factor * widthInGU;

		System.out.println(zoomPos);

		graphController.moveView((long) zoomPos);

		if (mouseScroll < 0) {
			graphController.zoomLevelUp();
		} else {
			graphController.zoomLevelDown();
		}

	}
}
