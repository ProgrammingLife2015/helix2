package tudelft.ti2806.pl3.visualization.node;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodePosition implements Node {
	private Node node;
	private long startX;
	private int yy;
	private List<NodePosition> incoming = new ArrayList<NodePosition>();
	private List<NodePosition> outgoing = new ArrayList<NodePosition>();
	private int previousNodesCount;
	
	public static List<NodePosition> newNodePositionList(List<Node> nodes,
			List<Edge> edges) {
		// GraphModel.removeAllDeadEdges(edges, nodes);
		Map<Integer, NodePosition> map = new HashMap<Integer, NodePosition>();
		for (Node node : nodes) {
			map.put(node.getId(), new NodePosition(node));
		}
		for (Edge edge : edges) {
			NodePosition from = map.get(edge.getFromId());
			NodePosition to = map.get(edge.getToId());
			// if (from == null || to == null) {
			// System.out.println(edge.toString());
			// } else {
			from.outgoing.add(to);
			to.incoming.add(from);
			// }
		}
		List<NodePosition> list = new ArrayList<NodePosition>(map.values());
		for (NodePosition node : list) {
			node.calculateStartX();
			node.calculatePreviousNodesCount();
		}
		return list;
	}
	
	protected NodePosition(Node node) {
		this.node = node;
	}
	
	public Node getNode() {
		return node;
	}
	
	List<NodePosition> getIncoming() {
		return incoming;
	}
	
	List<NodePosition> getOutgoing() {
		return outgoing;
	}
	
	public long getXStart() {
		return startX;
	}
	
	public long getXEnd() {
		return startX + getWidth();
	}
	
	public int getPreviousNodesCount() {
		return previousNodesCount;
	}
	
	public int getY() {
		return yy;
	}
	
	/**
	 * Recursive method for calculating the axis start.
	 * 
	 * @return the calculated startX value.
	 */
	protected long calculateStartX() {
		if (this.getXStart() != -1) {
			return this.getXStart();
		}
		long max = 0;
		for (NodePosition incomingNode : this.getIncoming()) {
			max = Math.max(max,
					incomingNode.calculateStartX() + incomingNode.getWidth());
		}
		this.startX = max;
		return max;
	}
	
	/**
	 * Calculate the number of nodes on the longest path to this node.
	 * 
	 * @return the number of nodes on the longest path to this node
	 */
	protected int calculatePreviousNodesCount() {
		if (this.getPreviousNodesCount() != -1) {
			return this.getPreviousNodesCount();
		}
		int max = 0;
		for (NodePosition incomingNode : this.getIncoming()) {
			max = Math.max(max, incomingNode.calculatePreviousNodesCount() + 1);
		}
		this.previousNodesCount = max;
		return this.previousNodesCount;
	}
	
	/**
	 * Calculates the whitespace available on the right side of this node.
	 * 
	 * @return the number of base pairs that fit in the whitespace on the right
	 *         side of the node.
	 */
	public long calculateWhitespaceOnRightSide() {
		long min = Long.MAX_VALUE;
		for (NodePosition incomingNode : this.getOutgoing()) {
			min = Math.min(min, incomingNode.getXStart());
		}
		return min - this.getXEnd();
	}
	
	public long getWidth() {
		return node.getWidth();
	}
	
	@Override
	public int getId() {
		return node.getId();
	}
	
	@Override
	public Genome[] getSource() {
		return node.getSource();
	}
	
	@Override
	public int getRefStartPoint() {
		return node.getRefStartPoint();
	}
	
	@Override
	public int getRefEndPoint() {
		return node.getRefEndPoint();
	}
}
