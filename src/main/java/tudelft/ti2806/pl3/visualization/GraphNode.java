package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;

public class GraphNode {
	NodeWrapper dataNode;
	Node gNode;

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
		this.dataNode = node;
		gNode = graph.addNode(node.getIdString());
		gNode.addAttribute("xy", node.getPreviousNodesCount(), node.getY());
		gNode.addAttribute("ui.class", node.getClass().getSimpleName());
	}

	public Node getNode() {
		return gNode;
	}
}
