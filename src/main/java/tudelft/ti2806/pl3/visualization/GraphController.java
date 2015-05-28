package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.HashMap;
import java.util.Map;

public class GraphController implements Controller {
	private FilteredGraphModel filteredGraphModel;
	private ZoomedGraphModel zoomedGraphModel;
	private GraphView graphView;
	private Map<String, Filter<DataNode>> filters = new HashMap<>();
	private final static int DEFAULT_VIEW = 1;
	
	/**
	 * Initialise an instance of GraphControler.<br>
	 * It will control the data in the graphview
	 * 
	 * @param graphView
	 *            the view in which the graph is displayed
	 */
	public GraphController(GraphView graphView) {
		this.graphView = graphView;
		filteredGraphModel = graphView.getFilteredGraphModel();
		zoomedGraphModel = graphView.getZoomedGraphModel();
	}
	
	/**
	 * Adds a node filter to the graph. The filters will be put in a HashMap, so
	 * adding a filter with the same name will override the older one.
	 * 
	 * @param name
	 *            the filter name
	 * @param filter
	 *            Filter the filter itself
	 */
	public void addFilter(String name, Filter<DataNode> filter) {
		filters.put(name, filter);
		filteredGraphModel.setFilters(filters.values());
		filteredGraphModel.produceWrappedGraphData();
	}
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param zoomCenter
	 *            the new center of zoom
	 */
	public void moveView(long zoomCenter) {
		graphView.setZoomCenter(zoomCenter);
	}
	
	/**
	 * Zoom the graph one level up.
	 */
	public void zoomLevelUp() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel() + 1);
		zoomedGraphModel.produceDataNodeWrapperList();
	}
	
	/**
	 * Zoom the graph one level down.
	 */
	public void zoomLevelDown() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel() - 1);
		zoomedGraphModel.produceDataNodeWrapperList();
	}

	/**
	 * Reset the zoom level of graph
	 */
	public void resetZoom() {
		zoomedGraphModel.setZoomLevel(DEFAULT_VIEW);
		zoomedGraphModel.produceDataNodeWrapperList();
	}

	public double getCurrentZoomLevel() {
		return zoomedGraphModel.getZoomLevel();
	}
	
	public long getCurrentZoomCenter() {
		return graphView.getZoomCenter();
	}
}
