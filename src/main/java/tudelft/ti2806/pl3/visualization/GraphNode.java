package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import tudelft.ti2806.pl3.visualization.node.NodePosition;

public class GraphNode {
	public GraphNode(Graph graph, String nodeName, NodePosition node) {
		startOfNode = addNode(graph, "[" + nodeName);
		endOfNode = addNode(graph, nodeName + "]");
		nodeEdge = addNodeEdge(graph, nodeName);
		this.dataNode = node;
	}
	
	NodePosition dataNode;
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
	private static org.graphstream.graph.Edge addNodeEdge(Graph graph,
			String nodeName) {
		org.graphstream.graph.Edge edge = graph.addEdge("[" + nodeName + "]",
				"[" + nodeName, nodeName + "]");
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
	private static org.graphstream.graph.Node addNode(Graph graph,
			String nodeName) {
		org.graphstream.graph.Node graphNode = graph.addNode(nodeName);
		return graphNode;
	}
	
	public NodePosition getDataNode() {
		return dataNode;
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
	
	public void updatePosition(double jump, double y) {
		long nodeSpace = (long) (dataNode.getPreviousNodesCount() * jump);
		startOfNode.setAttribute("xy", dataNode.getXStart() + nodeSpace, y);
		endOfNode.setAttribute("xy", dataNode.getXEnd() + nodeSpace, y);
	}
	
}
