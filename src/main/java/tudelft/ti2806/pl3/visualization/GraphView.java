package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.graph.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 * Generate a new view to have the changes take effect.
	 */
	private int basePairDisplayWidth;
	/**
	 * The space left between nodes for drawing the edges between the nodes.<br>
	 * Generate a new view to have the changes take effect.
	 */
	private int nodeSpacing;
	/**
	 * A zoom level of 1.0 shows the graph 1:1, so that every base pair should
	 * be readable. A zoom level of 2.0 shows the graph with each pase pair
	 * using the twice the space as with a zoom level op 1.0.
	 */
	private double zoomLevel;
	
	private Viewer viewer;
	
	/**
	 * The css style sheet used drawing the graph.<br>
	 * Generate a new view to have the changes take effect.
	 */
	private String styleSheet = DEFAULT_STYLESHEET;
	
	/**
	 * Generates a {@link Viewer} for the graph with the given {@code zoomLevel}
	 * . A new Viewer should be constructed every time the graphData or
	 * zoomLevel updates.
	 * 
	 * @param graphData
	 *            the graph data to construct the graph from
	 * @param zoomLevel
	 *            the new {@link zoomLevel}
	 */
	public void generateViewer(GraphData graphData, double zoomLevel) {
		this.zoomLevel = zoomLevel;
		Graph graph = generateGraph(graphData);
		setGraphPropertys(graph);
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
	}
	
	private void setGraphPropertys(Graph graph) {
		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}
	
	/**
	 * Centers the view on the {@code zoomCenter} and sets the views borders so
	 * that the view fits with the current {@link #zoomLevel}.
	 * 
	 * @param zoomCenter
	 *            the center of the screen on the x axis
	 * @param windowWidth
	 *            the width of the window to draw on
	 */
	public void changeView(long zoomCenter, long windowWidth) {
		double halfViewWidth = (windowWidth / zoomLevel) / 2;
		viewer.getDefaultView()
				.getCamera()
				.setBounds(zoomCenter - halfViewWidth, 1.0, 0,
						zoomCenter + halfViewWidth, -1.0, 0);
	}
	
	/**
	 * Generates a Graph from the given graphData, where each Node is set to a
	 * calculated position.
	 * 
	 * <p>
	 * This position is based on the level of zoom
	 * 
	 * @param graphData
	 *            the graph data to construct the graph from
	 * @return a graph with all nodes from the given graphData, each with a
	 *         calculated position
	 */
	public Graph generateGraph(GraphData graphData) {
		Graph graph = new SingleGraph("");
		double genomeHeight = 2.0 / graphData.getGenomes().size();
		for (Node node : graphData.getNodes()) {
			int nodeSpace = node.getPreviousNodesCount() * nodeSpacing;
			double y = calculateYPosition(node) * genomeHeight - 1.0;
			
			String nodeName = node.getId() + "";
			addNode(graph, node, "[" + nodeName, node.getXStart()
					* basePairDisplayWidth * zoomLevel + nodeSpace, y);
			addNode(graph, node, nodeName + "]", node.getXEnd()
					* basePairDisplayWidth * zoomLevel + nodeSpace, y);
			addNodeEdge(graph, nodeName, node);
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
		org.graphstream.graph.Edge gedge = graph.addEdge(edge.toString(), edge
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
	private static void addNodeEdge(Graph graph, String nodeName, Node node) {
		org.graphstream.graph.Edge edge = graph.addEdge("[" + nodeName + "]",
				"[" + nodeName, nodeName + "]");
		edge.addAttribute("ui.class", "nodeEdge");
		edge.addAttribute("node", node);
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
	 * @param x
	 *            the position of the node on the x axis
	 * @param y
	 *            the position of the node on the y axis
	 */
	private static void addNode(Graph graph, Node node, String nodeName,
			double x, double y) {
		org.graphstream.graph.Node graphNode = graph.addNode(nodeName);
		graphNode.setAttribute("xy", x, y);
		graphNode.addAttribute("node", node);
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
	
	public int getBasePairDisplayWidth() {
		return basePairDisplayWidth;
	}
	
	public void setBasePairDisplayWidth(int basePairDisplayWidth) {
		this.basePairDisplayWidth = basePairDisplayWidth;
	}
	
	public int getNodeSpacing() {
		return nodeSpacing;
	}
	
	public void setNodeSpacing(int nodeSpacing) {
		this.nodeSpacing = nodeSpacing;
	}
	
	public double getZoomLevel() {
		return zoomLevel;
	}
	
	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	
	public Viewer getViewer() {
		return viewer;
	}
	
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	
	public String getStyleSheet() {
		return styleSheet;
	}
	
	public void setStyleSheet(String styleSheet) {
		this.styleSheet = styleSheet;
	}
}
