package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.util.DefaultShortcutManager;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
 *
 */
public class GraphView implements Observer, ViewInterface {
	/**
	 * The space left between nodes for drawing the edges between the nodes.<br>
	 * Set the zoom to have changes take effect.
	 */
//	private int nodeJumpSize = 20;
	/**
	 * The zoomLevel used to draw the graph.<br>
	 * A zoom level of 1.0 shows the graph 1:1, so that every base pair should
	 * be readable, each with {@link #basePairDisplayWidth} pixels to draw its
	 * value as text. A zoom level of 2.0 shows the graph with each base pair
	 * using the half this size.
	 */
//	private double zoomLevel = 1.0;
	/**
	 * The center position of the view.<br>
	 * The position on the x axis.
	 */
//	private long zoomCenter;
	
	/**
	 * The css style sheet used drawing the graph.<br>
	 * Generate a new view to have the changes take effect.
	 */

	private WrappedGraphData graphData;
	private Graph graph = new SingleGraph("");
	private Viewer viewer;
	private View panel;
	
	private List<GraphNode> graphNodeList;

	private ZoomedGraphModel zoomedGraphModel;

	public GraphView(ZoomedGraphModel zoomedGraphModel) {
		this.zoomedGraphModel = zoomedGraphModel;
	}

	public void init() {
		generateViewer();
	}
	
	/**
	 * Generates a {@link Viewer} for the graph with the given {@code zoomLevel}
	 * . A new Viewer should be constructed every time the graphData or
	 * zoomLevel updates.
	 */
	private void generateViewer() {
		generateGraph();
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
		panel = viewer.addDefaultView(false);
//		panel.getCamera().setAutoFitView(false);

		panel.setShortcutManager(new DefaultShortcutManager());
	}
	
	/**
	 * Sets the graph its drawing properties.
	 */
	private void setGraphPropertys() {
		String url = "resources/stylesheet.css";
		try {
			List<String> lines = Files.readAllLines(Paths.get(url));

			String stylesheet = "";
			for (String line : lines) {
				stylesheet += line + " ";
			}

			graph.addAttribute("ui.stylesheet", stylesheet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}
	
	/**
	 * Generates a Graph from the current graphData.
	 * 
	 * @return a graph with all nodes from the given graphData
	 */
	public Graph generateGraph() {
		graph.clear();
		setGraphPropertys();
		graphNodeList = new ArrayList<>(graphData.getPositionedNodes().size());
		graphData.getPositionedNodes().forEach(node -> graphNodeList.add(new GraphNode(graph, node)));
		System.out.println(graphData.getPositionedNodes());

		for (NodeWrapper node : graphData.getPositionedNodes()) {
			for (NodeWrapper to : node.getOutgoing()) {
				addNormalEdge(graph, node, to);
			}
		}
		return graph;
	}
	
	/**
	 * Adds an edge between two nodes.
	 * 
	 * @param graph
	 *            the graph to add the edge to
	 * @param from
	 *            the node where the edge begins
	 * @param to
	 *            the node where the edge ends
	 */
	private static void addNormalEdge(Graph graph, NodeWrapper from, NodeWrapper to) {
		org.graphstream.graph.Edge gEdge = graph.addEdge(
				from.getIdString() + "-" + to.getIdString(), from.getIdString(), to.getIdString());
		gEdge.addAttribute("ui.class", "normalEdge");
	}
	
	/**
	 * Changes the zoom level and apply it.
	 * 
	 * @param zoomLevel
	 *            the new zoom level
	 */
	public void zoom(double zoomLevel) {
//		this.zoomLevel = zoomLevel;
//		panel.getCamera().setViewPercent(
//				zoomLevel / (getGraphWidth() / panel.getWidth()));

		//TODO: this has to be implemented in a new way.
	}
	
//	private double getGraphWidth() {
//		return graphData.getSize() + graphData.getLongestNodePath()
//				* nodeJumpSize * zoomLevel;
//	}
	
	/**
	 * Moves the view to the given position on the x axis.
	 * 
	 * @param newCenter
	 *            the new center of view
	 */
	public void moveView(long newCenter) {
//		this.zoomCenter = newCenter;
//		viewer.getDefaultView().getCamera().setViewCenter(zoomCenter, 0, 0);
	}

	@Override
	public Component getPanel() {
		return panel;
	}

	public Viewer getViewer() {
		return viewer;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o == zoomedGraphModel) {
			graphData = zoomedGraphModel.getWrappedGraphData();
			// TODO: draw graph with the newly retrieved graphData
		}
	}
}
