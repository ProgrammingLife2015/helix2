package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.node.DataNode;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

public class GraphController {
	private FilteredGraphModel filteredGraphModel;
	private ZoomedGraphModel zoomedGraphModel;
	private GraphView graphView;
	private double currentZoomLevel;
	private long currentZoomCenter;
	private Map<String, Filter<DataNode>> filters = new HashMap<>();
	/**
	 * Initialise an instance of GraphControler.<br>
	 * It will also create the view and models.
	 * @param abstractGraphData
	 *          The parsed graph data
	 */
	public GraphController(AbstractGraphData abstractGraphData) {
		filteredGraphModel = new FilteredGraphModel(abstractGraphData);
		zoomedGraphModel = new ZoomedGraphModel(filteredGraphModel);
		graphView = new GraphView(zoomedGraphModel);

		filteredGraphModel.addObserver(zoomedGraphModel);
		zoomedGraphModel.addObserver(graphView);

		filteredGraphModel.produceWrappedGraphData();
	}

	/**
	 * Adds a node filter to the graph.
	 * The filters will be put in a HashMap, so adding a filter with
	 * the same name will override the older one.
	 * @param name
	 *          the filter name
	 * @param filter Filter
	 *          the filter itself
	 */
	public void addFilter(String name, Filter<DataNode> filter) {
		filters.put(name, filter);
		filteredGraphModel.setFilters(filters.values());
		filteredGraphModel.produceWrappedGraphData();
	}
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param newZoomCenter
	 *            the new center of zoom
	 */
	public void moveView(long newZoomCenter) {
//		currentZoomCenter = newZoomCenter;
//		graphView.moveView(currentZoomCenter);
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
//			model.produceGraph(filters);
//		}
//		currentZoomLevel = newZoomLevel;
//		view.zoom(newZoomLevel);
	}

	public GraphView getView() {
		return graphView;
	}

	public Component getPanel() {
		return graphView.getPanel();
	}

	public double getCurrentZoomLevel() {
		return currentZoomLevel;
	}

	public long getCurrentZoomCenter() {
		return currentZoomCenter;
	}
}
