package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;
import tudelft.ti2806.pl3.ui.util.DialogUtil;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class GraphController implements Controller {
	private List<GraphMovedListener> graphMovedListenerList;
	private FilteredGraphModel filteredGraphModel;
	private ZoomedGraphModel zoomedGraphModel;
	private GraphView graphView;
	private GraphDataRepository graphDataRepository;
	private Map<String, Filter<DataNode>> filters = new HashMap<>();
	private static final int DEFAULT_VIEW = 1;
	private GeneData geneData;

	/**
	 * Percentage of the screen that is moved.
	 */
	private static final double MOVE_FACTOR = 10.0;

	/**
	 * Initialise an instance of GraphControler.<br>
	 * It will control the data in the graph view.
	 */
	public GraphController(GraphDataRepository graphDataRepository) {
		this.graphDataRepository = graphDataRepository;
		graphMovedListenerList = new ArrayList<>();

		initMvc();
		addListeners();
	}

	private void initMvc() {
		filteredGraphModel = new FilteredGraphModel(graphDataRepository);
		zoomedGraphModel = new ZoomedGraphModel(filteredGraphModel);
		graphView = new GraphView(zoomedGraphModel);
	}

	private void addListeners() {
		graphDataRepository.addGraphParsedObserver(filteredGraphModel);
		filteredGraphModel.addObserver(zoomedGraphModel);
		zoomedGraphModel.addObserver(graphView);
	}

	public void addLoadingObservers(ArrayList<LoadingObserver> loadingObservers) {
		filteredGraphModel.addLoadingObserversList(loadingObservers);
		zoomedGraphModel.addLoadingObserversList(loadingObservers);
	}


	/**
	 * Parse a graph file.
	 *
	 * @param nodeFile
	 * 		the file containing node information
	 * @param edgeFile
	 * 		the file containing edge information
	 * @throws FileNotFoundException
	 * 		when the file was not found
	 */
	public void parseGraph(File nodeFile, File edgeFile) throws FileNotFoundException {
		try {
			geneData = GeneData.parseGenes("geneAnnotationsRef");
			graphDataRepository.parseGraph(nodeFile, edgeFile, geneData);
			graphView.getPanel().setVisible(false);
			graphView.getPanel().setVisible(true);
		} catch (IOException e) {
			if (DialogUtil.confirm("Parse error", "A random error occurred while parsing the "
					+ "gene annotations file. "
					+ "Retrying could help. Would you like to try again now?")) {
				parseGraph(nodeFile, edgeFile);
			}
		}
	}

	/**
	 * Adds a node filter to the graph. The filters will be put in a HashMap, so
	 * adding a filter with the same name will override the older one.
	 *
	 * @param name
	 * 		the filter name
	 * @param filter
	 * 		Filter the filter itself
	 */
	public void addFilter(String name, Filter<DataNode> filter) {
		filters.put(name, filter);
		filteredGraphModel.setFilters(new ArrayList<>(filters.values()));
		filteredGraphModel.produceWrappedGraphData();
		graphMoved();
	}

	/**
	 * Moves the view to a new center position.
	 *
	 * @param zoomCenter
	 * 		the new center of zoom
	 */
	public void moveView(float zoomCenter) {
		graphView.setZoomCenter(zoomCenter);
		graphMoved();
	}

	/**
	 * Zoom the graph one level up.
	 */
	public void zoomLevelUp() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel() * 2);
		zoomedGraphModel.produceDataNodeWrapperList();
		graphMoved();
	}

	/**
	 * Zoom the graph one level down.
	 */
	public void zoomLevelDown() {
		zoomedGraphModel.setZoomLevel(zoomedGraphModel.getZoomLevel() / 2);
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

	private double getCurrentZoomLevel() {
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

	public Component getPanel() {
		return graphView.getPanel();
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

	/**
	 * When the graph was moved.
	 */
	private void graphMoved() {
		restrictViewCenter();
		graphMovedListenerList.forEach(GraphMovedListener::graphMoved);
	}

	/**
	 * Restrict the zoom center.
	 */
	private void restrictViewCenter() {
		if (graphView.getZoomCenter() < 0) {
			graphView.setZoomCenter(0);
		}
		if (graphView.getZoomCenter() > graphView.getGraphDimension()) {
			graphView.setZoomCenter((float) graphView.getGraphDimension());
		}
	}


	public float getMaxInterest() {
		return filteredGraphModel.getMaxInterest();
	}

	public float getViewPercent() {
		return (float) graphView.getViewPercent();
	}

	public float getOffsetToCenter() {
		return graphView.getOffsetToCenter();
	}

	public void removeDetailView() {
		graphView.removeDetailView();
	}

	public GeneData getGeneData() {
		return geneData;
	}
}
