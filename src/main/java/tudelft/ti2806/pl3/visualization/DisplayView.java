package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.Node;

import java.util.List;

public class DisplayView {
	public static Graph getGraph(String name, List<Node> nodes, List<Edge> edges) {
		Graph graph = new SingleGraph(name);
		for (Node node : nodes) {
			org.graphstream.graph.Node graphNode = graph.addNode(node
					.getNodeId() + "");
			graphNode.addAttribute("Node", node);
		}
		for (Edge edge : edges) {
			graph.addEdge(edge.toString(), edge.getFrom().getNodeId() + "",
					edge.getTo().getNodeId() + "");
		}
		return graph;
	}
}
