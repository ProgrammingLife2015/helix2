package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Node;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class GraphController {
	private GraphModel model;
	private GraphView view;
	private double currentZoomLevel;
	private List<Filter<Node>> filters = new ArrayList<Filter<Node>>();
	
	/**
	 * Initialise an instance of GraphControler.<br>
	 * Also initialises the {@link GraphModel} and {@link GraphView}.
	 * 
	 * @param graphData
	 *            the graphData to display.
	 */
	public GraphController(AbstractGraphData graphData) {
		model = new GraphModel(graphData);
		model.produceGraph(new ArrayList<Filter<Node>>());
		view = new GraphView(model.getGraphData());
	}
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param zoomCenter
	 *            the new center of zoom
	 */
	public void moveView(long zoomCenter) {
		view.moveView(zoomCenter);
	}
	
	/**
	 * Changes the zoom, and if necessary, filters are applied.
	 * 
	 * @param newZoomLevel
	 *            the new level of zoom to apply
	 */
	public void changeZoom(double newZoomLevel) {
		if (Math.round(newZoomLevel) != Math.round(currentZoomLevel)
				&& filters.size() != 0) {
			model.produceGraph(filters);
		}
		currentZoomLevel = newZoomLevel;
		view.zoom(newZoomLevel);
	}
	
	public Component getPanel() {
		return view.getPanel();
	}
}
