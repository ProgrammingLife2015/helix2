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
//		double scale;

//
//		double oldWith = graphController.getViewPercent() * graphController.getGraphSizeInGraphUnits();
//		double fromSidetoNode = getMousePosInGraphUnits() - graphController.getCurrentGraphX();
//		double fromMiddletoNode = graphController.getZoomCenterX() - getMousePosInGraphUnits();

//		GraphMetrics converter = graphController.graphView.viewer.getDefaultView().getCamera().getMetrics();

//
		double oldpixels = graphController.getZoomCenterX();
		System.out.println(oldpixels);

//		System.out.println("Zoomcenter in pixel : " + oldpixels);
//		System.out.println("Zoomcenter in GU : " + graphController.getZoomCenterX());
//		System.out.println(graphController.getCurrentZoomCenter());
//		System.out.println(graphController.getViewPercent());
//		System.out.println(1f / graphController.getCurrentZoomLevel());
		if (mouseScroll < 0) {
			graphController.zoomLevelUp();
		} else {
			graphController.zoomLevelDown();
		}
		double newpixels = graphController.getZoomCenterX();
		System.out.println(newpixels);



//		System.out.println(newpixels-oldpixels);

//		System.out.println("Zoomcenter in pixel : " + graphController.getZoomCenterX());
//		System.out.println(graphController.getCurrentZoomCenter());
//		System.out.println(graphController.getViewPercent());
//		System.out.println(1f / graphController.getCurrentZoomLevel());
//		double newpixels = graphController.graphView.viewer;
//
//		double newWidth = graphController.getViewPercent() * graphController.getGraphSizeInGraphUnits();
//		scale = newWidth / oldWith;
//
//		double newfromSidetoNode = fromSidetoNode * scale;
//		double newfromMiddletoNode = fromMiddletoNode * scale;
//
//		double delta = (newfromSidetoNode + newfromMiddletoNode) - (fromSidetoNode + fromMiddletoNode);
//		System.out.println(delta);
//		graphController.moveView((float) (graphController.getCurrentZoomCenter() + delta));
//
//		double newMousePos = getMousePosInGraphUnits();
//		double endViewX = graphController.getCurrentGraphX();
//		double diff1 = newMousePos - endViewX;


	}

	public double getMousePosInGraphUnits() {
		double factor = MouseInfo.getPointerInfo().getLocation().getX() / graphController.getPanelWidth();

		double viewWidthInGU = graphController.getGraphSizeInGraphUnits() * graphController.getViewPercent();
		double zoomPos = graphController.getCurrentGraphX() + factor * viewWidthInGU;

		return zoomPos;
	}

	public boolean mousePastViewCenter() {
		double mousePos = getMousePosInGraphUnits();
		double viewcenter = graphController.getCurrentZoomCenter();
		return mousePos > viewcenter;
	}


}
