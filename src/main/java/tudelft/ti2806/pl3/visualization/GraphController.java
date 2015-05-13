package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.Node;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

public class GraphController implements GraphControllerInterface {
	private GraphModelInterface model;
	private GraphView view;
	private double currentZoomLevel;
	private long currentZoomCenter;
	private Map<String, Filter<Node>> filters = new HashMap<>();
	
	/**
	 * Initialise an instance of GraphControler.<br>
	 * Also initialises the {@link GraphModel} and {@link GraphView}.
	 * 
	 * @param view
	 *            the view to use
	 * @param model
	 *            the model to use
	 */
	public GraphController(GraphView view, GraphModelInterface model) {
		this.model = model;
		this.view = view;
		model.produceGraph(filters.values());
		view.setGraphData(model.getGraphData());
		view.init();

	}

	public void addFilter(String name, Filter<Node> filter) {
		filters.put(name, filter);
		model.produceGraph(filters.values());
		view.setGraphData(model.getGraphData());
		view.generateGraph();
		view.calculateGraphPositions();
	}
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param newZoomCenter
	 *            the new center of zoom
	 */
	public void moveView(long newZoomCenter) {
		currentZoomCenter = newZoomCenter;
		view.moveView(currentZoomCenter);
	}
	
	/**
	 * Changes the zoom, and if necessary, filters are applied.
	 * 
	 * @param newZoomLevel
	 *            the new level of zoom to apply
	 */
	public void changeZoom(double newZoomLevel) {
//		if (Math.round(newZoomLevel) != Math.round(currentZoomLevel)
//				&& filters.size() != 0) {
//			model.produceGraph(filters.values());
//		}
		currentZoomLevel = newZoomLevel;
		view.zoom(newZoomLevel);
	}

	public GraphViewInterface getView() {
		return view;
	}

	public Component getPanel() {
		return view.getPanel();
	}

	public double getCurrentZoomLevel() {
		return currentZoomLevel;
	}

	public long getCurrentZoomCenter() {
		return currentZoomCenter;
	}
}
