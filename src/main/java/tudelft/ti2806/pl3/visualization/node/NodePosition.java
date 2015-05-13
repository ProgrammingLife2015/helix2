package tudelft.ti2806.pl3.visualization.node;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NodePosition is used to store the position of nodes and the edges of nodes.
 * This responsibility is separated from {@link Node} so that the same node can
 * have different positions over different views without cloning the expensive
 * Node instances which contain the original data.
 * 
 * @author Sam Smulders
 *
 */
public class NodePosition {
	private Node node;
	private long startX = -1;
	private double yy;
	private List<NodePosition> incoming = new ArrayList<NodePosition>();
	private List<NodePosition> outgoing = new ArrayList<NodePosition>();
	private int previousNodesCount = -1;
	
	/**
	 * Construct a list with connected and fully initialised
	 * {@code NodePosition}s.
	 * 
	 * @param nodeList
	 *            the {@link List}<{@link Node}> of which the new {@link List}<
	 *            {@link NodePosition}> is constructed from
	 * @param edgeList
	 *            the {@link List}<{@link Edge}> with the connections between
	 *            the newly created {@link NodePosition}s
	 * @return a {@link List}<{@link NodePosition}>, constructed from the
	 *         {@code nodeList} and {@code edgeList}
	 */
	public static List<NodePosition> newNodePositionList(List<Node> nodeList,
			List<Edge> edgeList) {
		// Construct list
		Map<Integer, NodePosition> map = new HashMap<Integer, NodePosition>();
		for (Node node : nodeList) {
			map.put(node.getId(), new NodePosition(node));
		}
		
		// Add connections from the edge list
		for (Edge edge : edgeList) {
			NodePosition from = map.get(edge.getFromId());
			NodePosition to = map.get(edge.getToId());
			from.outgoing.add(to);
			to.incoming.add(from);
		}
		
		// Calculate the x positions and the number previous node count
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
	
	public List<NodePosition> getIncoming() {
		return incoming;
	}
	
	public List<NodePosition> getOutgoing() {
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
	
	public double getY() {
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
	
	public int getId() {
		return node.getId();
	}
	
	public Genome[] getSource() {
		return node.getSource();
	}
	
	public int getRefStartPoint() {
		return node.getRefStartPoint();
	}
	
	public int getRefEndPoint() {
		return node.getRefEndPoint();
	}
}
