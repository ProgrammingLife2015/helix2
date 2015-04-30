package tudelft.ti2806.pl3.data;

public class Edge {
	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
	}
	
	protected Node from;
	protected Node to;
	
	public Node getFrom() {
		return from;
	}
	
	public Node getTo() {
		return to;
	}
	
	@Override
	public String toString() {
		return "Edge [from=" + from.getNodeId() + ", to=" + to.getNodeId()
				+ "]";
	}
}
