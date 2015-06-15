package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class GraphController implements Controller {
	private List<GraphMovedListener> graphMovedListenerList;
	private FilteredGraphModel filteredGraphModel;
	private ZoomedGraphModel zoomedGraphModel;
	private final GraphView graphView;
	private Map<String, Filter<DataNode>> filters = new HashMap<>();
	private static final int DEFAULT_VIEW = 1;
	
	/**
	 * Percentage of the screen that is moved.
	 */
	private static final double MOVE_FACTOR = 10.0;

	/**
	 * Initialise an instance of GraphControler.<br>
	 * It will control the data in the graphview
	 * 
	 * @param graphView
	 *            the view in which the graph is displayed
	 */
	public GraphController(GraphView graphView) {
		this.graphView = graphView;
		graphMovedListenerList = new ArrayList<>();
		filteredGraphModel = graphView.getFilteredGraphModel();
		zoomedGraphModel = graphView.getZoomedGraphModel();
	}

	public void init() {
		filteredGraphModel.produceWrappedGraphData();
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
		graphMoved();
	}
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param zoomCenter
	 *            the new center of zoom
	 */
	public void moveView(float zoomCenter) {
		graphView.setZoomCenter(zoomCenter);
		graphMoved();
	}
	
	/**
	 * Zoom the graph one level up.
	 */
	public void zoomLevelUp() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel() + 1);
		zoomedGraphModel.produceDataNodeWrapperList();
		graphMoved();
	}
	
	/**
	 * Zoom the graph one level down.
	 */
	public void zoomLevelDown() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel() - 1);
		zoomedGraphModel.produceDataNodeWrapperList();
		graphMoved();
	}

	/**
	 * Reset the zoom level of graph.
	 */
	public void resetZoom() {
		zoomedGraphModel.setZoomLevel(DEFAULT_VIEW);
		zoomedGraphModel.produceDataNodeWrapperList();
		graphView.setZoomCenter(graphView.getOffsetToCenter());
		graphMoved();
	}

	/**
	 * Move the view of the graph to the left with percentage of MOVE_FACTOR.
	 */
	public void moveLeft() {
		float oldViewCenter = getCurrentZoomCenter();
		double move = (ScreenSize.getInstance().getWidth() / MOVE_FACTOR)
				/ getCurrentZoomLevel();
		float newViewCenter = (float) (oldViewCenter - move);
		moveView(newViewCenter);
		graphMoved();
	}

	/**
	 * Move the view of the graph to the right with percentage of MOVE_FACTOR.
	 */
	public void moveRight() {
		float oldViewCenter = getCurrentZoomCenter();
		double move = (ScreenSize.getInstance().getWidth() / MOVE_FACTOR)
				/ getCurrentZoomLevel();
		float newViewCenter = (float) (oldViewCenter + move);
		moveView(newViewCenter);
		graphMoved();
	}

	public void centerOnNode(DataNode node) throws NodeNotFoundException {
		graphView.centerOnNode(node);
		graphMoved();
	}

	public double getCurrentZoomLevel() {
		return zoomedGraphModel.getZoomLevel();
	}
	
	public float getCurrentZoomCenter() {
		return graphView.getZoomCenter();
	}

	public double getGraphDimension() {
		return graphView.getGraphDimension();
	}

	public GraphView getGraphView() {
		return graphView;
	}

	public float[] getInterest() {
		return filteredGraphModel.getInterest();
	}

	public Observable getFilteredObservable() {
		return filteredGraphModel;
	}

	public void addGraphMovedListener(GraphMovedListener graphMovedListener) {
		graphMovedListenerList.add(graphMovedListener);
	}

	public void removeGraphMovedListener(GraphMovedListener graphMovedListener) {
		graphMovedListenerList.remove(graphMovedListener);
	}

	public void graphMoved() { //TODO: Needs JavaDoc
		if (graphView.getZoomCenter() < 0) {
			graphView.setZoomCenter(0);
		}
		if (graphView.getZoomCenter() > graphView.getGraphDimension()) {
			graphView.setZoomCenter((float) graphView.getGraphDimension());
		}
		graphMovedListenerList.forEach(GraphMovedListener::graphMoved);
	}

	public float getMaxInterest() {
		return filteredGraphModel.getMaxInterest();
	}
}
