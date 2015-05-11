package tudelft.ti2806.pl3.visualization;

import java.awt.Component;

public interface GraphControllerInterface {
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param zoomCenter
	 *            the new center of zoom
	 */
	void moveView(long zoomCenter);
	
	/**
	 * Changes the zoom, and if necessary, filters are applied.
	 * 
	 * @param newZoomLevel
	 *            the new level of zoom to apply
	 */
	void changeZoom(double newZoomLevel);
	
	Component getPanel();
}
