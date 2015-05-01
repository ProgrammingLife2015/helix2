package tudelft.ti2806.pl3.data;

import java.util.List;

public class GraphData {
	private List<Node> nodes;
	private List<Edge> edges;
	
	public GraphData(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
}
