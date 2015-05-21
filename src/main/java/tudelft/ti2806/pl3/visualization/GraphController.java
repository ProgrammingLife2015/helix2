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
		graphView.init();

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
	 * @param zoomCenter
	 *            the new center of zoom
	 */
	public void moveView(long zoomCenter) {
		graphView.setZoomCenter(zoomCenter);
	}

	public void zoomLevelUp() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel()+1);
		zoomedGraphModel.produceDataNodeWrapperList();
	}
	public void zoomLevelDown() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel()-1);
		zoomedGraphModel.produceDataNodeWrapperList();
	}

	public Component getPanel() {
		return graphView.getPanel();
	}

	public double getCurrentZoomLevel() {
		return zoomedGraphModel.getZoomLevel();
	}

	public long getCurrentZoomCenter() {
		return graphView.getZoomCenter();
	}
}
