package tudelft.ti2806.pl3.visualization.position.wrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class NodePositionWrapper {
	
	protected double yy;
	protected List<NodePositionWrapper> incoming = new ArrayList<NodePositionWrapper>();
	protected List<NodePositionWrapper> outgoing = new ArrayList<NodePositionWrapper>();
	protected int previousNodesCount = -1;
	
	public abstract long getXStart();
	
	public abstract long getXEnd();
	
	public abstract long getWidth();
	
	public int getPreviousNodesCount() {
		return previousNodesCount;
	}
	
	public double getY() {
		return yy;
	}
	
	/**
	 * Calculate the number of nodes on the longest path to this node.
	 * 
	 * @return the number of nodes on the longest path to this node
	 */
	public int calculatePreviousNodesCount() {
		if (this.getPreviousNodesCount() != -1) {
			return this.getPreviousNodesCount();
		}
		int max = 0;
		for (NodePositionWrapper incomingNode : this.getIncoming()) {
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
		for (NodePositionWrapper incomingNode : this.getOutgoing()) {
			min = Math.min(min, incomingNode.getXStart());
		}
		return min - this.getXEnd();
	}
	
	public List<NodePositionWrapper> getIncoming() {
		return incoming;
	}
	
	public List<NodePositionWrapper> getOutgoing() {
		return outgoing;
	}

	public abstract String getIdString();
}
