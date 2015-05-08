package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.Node;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphView {
	private static final String DEFAULT_STYLESHEET = "edge.normalEdge {shape: freeplane;"
			+ "fill-color: #00000070;}"
			+ "edge.nodeEdge {fill-color: red;"
			+ "stroke-width:3px;}"
			+ "node {stroke-mode: plain;"
			+ "size: 0;"
			+ "shape: freeplane;" + "fill-color: #00000000;}";
	/**
	 * The space reserved for drawing the base pairs characters. It should be
	 * equal to the font it's char width.<br>
	 * Set the zoom to have changes take effect.
	 */
	private int basePairDisplayWidth = 10;
	/**
	 * The space left between nodes for drawing the edges between the nodes.<br>
	 * Set the zoom to have changes take effect.
	 */
	private int nodeJumpSize = 20;
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
	private long zoomCenter;
	
	/**
	 * The css style sheet used drawing the graph.<br>
	 * Generate a new view to have the changes take effect.
	 */
	private String styleSheet = DEFAULT_STYLESHEET;
	
	private AbstractGraphData graphData;
	private Graph graph = new SingleGraph("");
	private Viewer viewer;
	private View panel;
	
	private List<org.graphstream.graph.Node> nodeStartList;
	private List<org.graphstream.graph.Node> nodeEndList;
	private List<org.graphstream.graph.Edge> nodeEdgeList;
	
	private long[] spaceStarters;
	
	/**
	 * Initialise an instance of GraphView.
	 * 
	 * <p>
	 * Automatically generates the graph and viewer from the given
	 * {@link AbstractGraphData} and calculates the positions of the nodes on
	 * the graph for the default zoom.
	 * 
	 * @param graphDataInterface
	 *            the {@link AbstractGraphData} to generate the graph from.
	 */
	public GraphView(AbstractGraphData graphDataInterface) {
		this.graphData = graphDataInterface;
		generateViewer();
		calculateGraphPositions();
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
		panel.getCamera().setAutoFitView(false);
	}
	
	/**
	 * Sets the graph its drawing properties.
	 */
	private void setGraphPropertys() {
		graph.addAttribute("ui.stylesheet", styleSheet);
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
		nodeStartList = new ArrayList<org.graphstream.graph.Node>(graphData
				.getNodes().size());
		nodeEndList = new ArrayList<org.graphstream.graph.Node>(graphData
				.getNodes().size());
		nodeEdgeList = new ArrayList<org.graphstream.graph.Edge>(graphData
				.getNodes().size());
		for (Node node : graphData.getNodes()) {
			String nodeName = node.getId() + "";
			nodeStartList.add(addNode(graph, node, "[" + nodeName));
			nodeEndList.add(addNode(graph, node, nodeName + "]"));
			nodeEdgeList.add(addNodeEdge(graph, nodeName, node));
		}
		for (Edge edge : graphData.getEdges()) {
			addNormalEdge(graph, edge);
		}
		return graph;
	}
	
	/**
	 * Adds an edge between two nodes.
	 * 
	 * @param graph
	 *            the graph to add the edge to
	 * @param edge
	 *            the edge to represent
	 */
	private static void addNormalEdge(Graph graph, Edge edge) {
		org.graphstream.graph.Edge gedge = graph.addEdge(edge.getName(), edge
				.getFrom().getId() + "]", "[" + edge.getTo().getId());
		gedge.addAttribute("ui.class", "normalEdge");
		gedge.addAttribute("edge", edge);
	}
	
	/**
	 * Adds an edge representing a node on the graph.
	 * 
	 * @param graph
	 *            the graph to add the edge to
	 * @param nodeName
	 *            the name of the node to represent
	 * @param node
	 *            the node to represent
	 */
	private static org.graphstream.graph.Edge addNodeEdge(Graph graph,
			String nodeName, Node node) {
		org.graphstream.graph.Edge edge = graph.addEdge("[" + nodeName + "]",
				"[" + nodeName, nodeName + "]");
		edge.addAttribute("ui.class", "nodeEdge");
		edge.addAttribute("node", node);
		return edge;
	}
	
	/**
	 * Adds a node to the graph.
	 * 
	 * @param graph
	 *            the graph to add the node to
	 * @param node
	 *            the node to add to the graph
	 * @param nodeName
	 *            the name of the node
	 */
	private static org.graphstream.graph.Node addNode(Graph graph, Node node,
			String nodeName) {
		org.graphstream.graph.Node graphNode = graph.addNode(nodeName);
		graphNode.addAttribute("node", node);
		return graphNode;
	}
	
	/**
	 * Calculates all positions for the current graph and zoom.
	 */
	private void calculateGraphPositions() {
		double genomeHeight = 1.0 / graphData.getGenomes().size();
		spaceStarters = new long[graphData.getOrigin().getLongestNodePath() + 1];
		Map<Node, double[]> map = new HashMap<Node, double[]>();
		for (Node node : graphData.getNodes()) {
			double nodeSpace = node.getPreviousNodesCount() * nodeJumpSize
					* zoomLevel;
			// { y, startX, endX }
			double[] position = new double[3];
			
			position[0] = (calculateYPosition(node) * genomeHeight - 0.5)
					* panel.getHeight() * zoomLevel;
			
			position[1] = node.getXStart() * basePairDisplayWidth + nodeSpace;
			
			position[2] = node.getXEnd() * basePairDisplayWidth + nodeSpace;
			map.put(node, position);
			spaceStarters[node.getPreviousNodesCount()] = Math.min(
					spaceStarters[node.getPreviousNodesCount()],
					(long) position[1]);
		}
		for (org.graphstream.graph.Node graphNode : nodeStartList) {
			double[] position = map.get(graphNode.getAttribute("node"));
			graphNode.setAttribute("xy", position[1], position[0]);
		}
		for (org.graphstream.graph.Node graphNode : nodeEndList) {
			double[] position = map.get(graphNode.getAttribute("node"));
			graphNode.setAttribute("xy", position[2], position[0]);
		}
	}
	
	/**
	 * Changes the zoom level and apply it.
	 * 
	 * @param zoomLevel
	 *            the new zoom level
	 */
	public void zoom(double zoomLevel) {
		this.zoomLevel = zoomLevel;
		calculateGraphPositions();
		panel.getCamera().setViewPercent(
				zoomLevel / (getGraphWidth() / panel.getWidth()));
	}
	
	private double getGraphWidth() {
		return graphData.getSize() * basePairDisplayWidth
				+ graphData.getLongestNodePath() * nodeJumpSize * zoomLevel;
	}
	
	/**
	 * Moves the view to the given position on the x axis.
	 * 
	 * @param newCenter
	 *            the new center of view
	 */
	public void moveView(long newCenter) {
		this.zoomCenter = newCenter;
		viewer.getDefaultView().getCamera().setViewCenter(zoomCenter, 0, 0);
	}
	
	public Component getPanel() {
		return panel;
	}
	
	/**
	 * Calculates the position of the node on the y axis.
	 * 
	 * @param node
	 *            the node to calculate the position of
	 * @return the position on the y axis
	 */
	private static double calculateYPosition(Node node) {
		List<Integer> yposition = new ArrayList<Integer>();
		for (Genome genome : node.getSource()) {
			yposition.add(genome.getYposition());
		}
		Collections.sort(yposition);
		return yposition.get(yposition.size() / 2);
	}
}
