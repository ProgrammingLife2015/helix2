package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import tudelft.ti2806.pl3.visualization.position.wrapper.NodeWrapper;

public class GraphNode {
	/**
	 * Initialises a instance of GraphNode and adds itself to the given
	 * {@link Graph}.
	 * 
	 * @param graph
	 *            the graph to draw itself on
	 * @param node
	 *            the node to represent on the graph
	 */
	public GraphNode(Graph graph, NodeWrapper node) {
		startOfNode = addNode(graph, "[" + node.getId());
		endOfNode = addNode(graph, node.getId() + "]");
		nodeEdge = addNodeEdge(graph, node.getId() + "");
		this.dataNode = node;
	}
	
	NodeWrapper dataNode;
	Node startOfNode;
	Node endOfNode;
	Edge nodeEdge;
	
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
	private static Edge addNodeEdge(Graph graph, String nodeName) {
		Edge edge = graph.addEdge("[" + nodeName + "]", "[" + nodeName,
				nodeName + "]");
		edge.addAttribute("ui.class", "nodeEdge");
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
	private static Node addNode(Graph graph, String nodeName) {
		Node graphNode = graph.addNode(nodeName);
		return graphNode;
	}
	
	public Node getStartOfNode() {
		return startOfNode;
	}
	
	public Node getEndOfNode() {
		return endOfNode;
	}
	
	public Edge getNodeEdge() {
		return nodeEdge;
	}
	
	/**
	 * Updates the position, accordingly to the jump width.
	 * 
	 * @param jump
	 *            the distance between nodes
	 * @param panelHeight
	 *            the maximum height of a position for the node to be visible
	 */
	public void updatePosition(double jump, double panelHeight) {
		long nodeSpace = (long) (dataNode.getPreviousNodesCount() * jump);
		startOfNode.setAttribute("xy", dataNode.getXStart() + nodeSpace,
				dataNode.getY());
		endOfNode.setAttribute("xy", dataNode.getXEnd() + nodeSpace,
				dataNode.getY() * panelHeight);
	}
	
}
