package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import tudelft.ti2806.pl3.LoadingObservable;
import tudelft.ti2806.pl3.LoadingObserver;
import tudelft.ti2806.pl3.controls.MouseManager;
import tudelft.ti2806.pl3.data.gene.Gene;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.exception.NodeNotFoundException;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The GraphView is responsible for adding the nodes and edges to the graph,
 * keeping the nodes and edges on the right positions and applying the right
 * style to the graph.
 *
 * @author Sam Smulders
 */
public class GraphView implements Observer, tudelft.ti2806.pl3.View, ViewInterface, LoadingObservable {
	/**
	 * The zoomLevel used to draw the graph.<br>
	 * A zoom level of 1.0 shows the graph 1:1, so that every base pair should
	 * be readable, each with {@link #basePairDisplayWidth} pixels to draw its
	 * value as text. A zoom level of 2.0 shows the graph with each base pair
	 * using the half this size.
	 */
	private double zoomLevel = 1.0;
	/**
	 * The center position of the view.<br>
	 * The position on the x axis.
	 */
	private float zoomCenter = 1;

	/**
	 * The css style sheet used drawing the graph.<br>
	 * Generate a new view to have the changes take effect.
	 */

	private List<WrapperClone> graphData;
	private Graph graph = new SingleGraph("Graph");
	private Viewer viewer;
	private View panel;
	private ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	private MouseManager mouseManager;

	private GraphController graphController;

	private FilteredGraphModel filteredGraphModel;
	private ZoomedGraphModel zoomedGraphModel;

	/**
	 * Construct a GraphView with no LoadingObservers.
	 *
	 * @param abstractGraphData
	 * 		GraphData to display
	 */
	public GraphView(AbstractGraphData abstractGraphData) {
		this(abstractGraphData, null);
	}

	/**
	 * Construct a GraphView with LoadingObserver.
	 *
	 * @param abstractGraphData
	 * 		GraphData to display
	 * @param loadingObservers
	 * 		Observers for loading
	 */
	public GraphView(AbstractGraphData abstractGraphData, ArrayList<LoadingObserver> loadingObservers) {
		// make graph
		filteredGraphModel = new FilteredGraphModel(abstractGraphData);
		zoomedGraphModel = new ZoomedGraphModel(filteredGraphModel);
		// add the loading observers
		addLoadingObserversList(loadingObservers);
		filteredGraphModel.addLoadingObserversList(loadingObservers);
		zoomedGraphModel.addLoadingObserversList(loadingObservers);

		init();
		filteredGraphModel.addObserver(zoomedGraphModel);
		zoomedGraphModel.addObserver(this);

		this.graphController = new GraphController(this);
		graphData = new ArrayList<>();
	}

	/**
	 * Makes a call to the viewer.
	 */
	public void init() {
		notifyLoadingObservers(true);
		generateViewer();
		// TODO: don't hardcode
		setZoomCenter(600);
		notifyLoadingObservers(false);
	}

	/**
	 * Generates a {@link Viewer} for the graph with the given {@code zoomLevel}
	 * . A new Viewer should be constructed every time the graphData or
	 * zoomLevel updates.
	 */
	private void generateViewer() {
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
		panel = viewer.addDefaultView(false);
		mouseManager = new MouseManager();
		viewer.getDefaultView().setMouseManager(mouseManager);
	}

	/**
	 * Sets the graph its drawing properties.
	 */
	private void setGraphPropertys() {
		final String stylesheet = "stylesheet.css";

		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "url('" + stylesheet + "')");
	}

	/**
	 * Generates a Graph from the current graphData.
	 *
	 * @return a graph with all nodes from the given graphData
	 */
	public Graph generateGraph() {
		notifyLoadingObservers(true);
		graph.clear();
		setGraphPropertys();
		final double someSize = panel.getBounds().height
				/ ((double) panel.getBounds().width * zoomLevel / zoomedGraphModel
				.getWrappedCollapsedNode().getWidth())
				/ zoomedGraphModel.getWrappedCollapsedNode().getGenome().size();
		graphData.forEach(node -> {
				Node graphNode = graph.addNode(Integer.toString(node.getId()));
				double y = node.getY() * someSize;
				graphNode.setAttribute("xy", node.getX(), y);
				graphNode.addAttribute("ui.class", node.getOriginalNode().getClass()
							.getSimpleName());
				graphNode.addAttribute("ui.label", node.getOriginalNode().getWidth());
				graphNode.setAttribute("node", node);

			});

		for (WrapperClone node : graphData) {
			for (Wrapper to : node.getOutgoing()) {
				WrapperClone clone = (WrapperClone) to;
				if (node.getOriginalNode().getClass() != FixWrapper.class
						&& clone.getOriginalNode().getClass() != FixWrapper.class) {
					addNormalEdge(graph, node, to);
				}
			}
		}
		notifyLoadingObservers(false);
		return graph;
	}

	/**
	 * Adds an edge between two nodes.
	 *
	 * @param graph
	 * 		the graph to add the edge to
	 * @param from
	 * 		the node where the edge begins
	 * @param to
	 * 		the node where the edge ends
	 */
	@SuppressWarnings("PMD.UnusedPrivateMethod")
	private static void addNormalEdge(Graph graph, Wrapper from, Wrapper to) {
		graph.addEdge(from.getId() + "-" + to.getId(),
				Integer.toString(from.getId()), Integer.toString(to.getId()), true);
	}

	@Override
	public Component getPanel() {
		return panel;
	}

	@Override
	public GraphController getController() {
		return graphController;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == zoomedGraphModel) {
			graphData = zoomedGraphModel.getDataNodeWrapperList();
			zoomLevel = zoomedGraphModel.getZoomLevel();
			// TODO: draw graph with the newly retrieved graphData
			generateGraph();
			zoom();
		}
	}

	private void zoom() {
		viewer.getDefaultView().getCamera().setViewPercent(1 / zoomLevel);
	}

	public float getZoomCenter() {
		return zoomCenter;
	}

	/**
	 * Moves the view to the given position on the x axis.
	 *
	 * @param zoomCenter
	 * 		the new center of view
	 */
	public void setZoomCenter(float zoomCenter) {
		this.zoomCenter = zoomCenter;
		viewer.getDefaultView().getCamera().setViewCenter(zoomCenter, 0, 0);
	}

	public FilteredGraphModel getFilteredGraphModel() {
		return filteredGraphModel;
	}

	public ZoomedGraphModel getZoomedGraphModel() {
		return zoomedGraphModel;
	}

	@Override
	public void addLoadingObserver(LoadingObserver loadingObservable) {
		loadingObservers.add(loadingObservable);
	}


	@Override
	public void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			addLoadingObserver(loadingObserver);
		}
	}

	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObservable) {
		loadingObservers.remove(loadingObservable);
	}

	@Override
	public void notifyLoadingObservers(Object arguments) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			loadingObserver.update(this, arguments);
		}
	}

	/**
	 * Centers the graph on a specific node. It passes a {@link DataNode} and then looks in the list of currently
	 * drawn {@link WrapperClone}s, which one contains this {@link DataNode} and then sets the zoom center on this
	 * {@link WrapperClone}.
	 *
	 * @param node
	 * 		The {@link DataNode} to move the view to
	 * @throws NodeNotFoundException
	 * 		Thrown when the node cannot be found in all {@link WrapperClone}s
	 */
	public void centerOnNode(DataNode node) throws NodeNotFoundException {
		float x = -1;
		for (WrapperClone wrapperClone : graphData) {
			if (wrapperClone.getDataNodes().contains(node)) {
				x = wrapperClone.getX();
				break;
			}
		}
		if (x != -1) {
			setZoomCenter(x);
		} else {
			throw new NodeNotFoundException(
					"The node " + node + " you are looking for cannot be found in the current graph.");
		}
	}

	public void removeDetailView() {
		mouseManager.removeDetailView();
	}
}
