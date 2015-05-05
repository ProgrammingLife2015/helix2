package tudelft.ti2806.pl3.data.graph.positioning;

import tudelft.ti2806.pl3.data.graph.SingleNode;

public class NodeTarget implements Target {
	private SingleNode node;
	
	public NodeTarget(SingleNode node) {
		this.node = node;
	}
	
	@Override
	public long getEnd() {
		return node.getXEnd() + node.getPreviousNodesCount();
	}
	
	@Override
	public long getStart() {
		return node.getXStart() + node.getPreviousNodesCount();
	}
	
	@Override
	public Line getLine(int[] order) {
		return new Line(this.getStart(), this.getEnd(), 0f, order[node.getId()]);
	}
	
}
