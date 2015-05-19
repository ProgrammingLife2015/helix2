package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

public abstract class NodeWrapper implements Comparable<NodeWrapper> {
	
	protected float yy;
	protected List<NodeWrapper> incoming = new ArrayList<NodeWrapper>();
	protected List<NodeWrapper> outgoing = new ArrayList<NodeWrapper>();
	protected int previousNodesCount = -1;
	protected float ySpace;

	public float getySpace() {
		return ySpace;
	}

	public void setySpace(float ySpace) {
		this.ySpace = ySpace;
	}

	public abstract long getXStart();
	
	public abstract long getXEnd();
	
	public abstract long getWidth();
	
	public int getPreviousNodesCount() {
		return previousNodesCount;
	}
	
	public float getY() {
		return yy;
	}
	
	public void setY(float yy) {
		this.yy = yy;
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
		for (NodeWrapper incomingNode : this.getIncoming()) {
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
		for (NodeWrapper incomingNode : this.getOutgoing()) {
			min = Math.min(min, incomingNode.getXStart());
		}
		return min - this.getXEnd();
	}
	
	public List<NodeWrapper> getIncoming() {
		return incoming;
	}
	
	public List<NodeWrapper> getOutgoing() {
		return outgoing;
	}
	
	public abstract String getIdString();
	
	public abstract List<Genome> getGenome();
	
	public abstract void calculate(WrapperOperation wrapperSequencer, NodeWrapper container);
	
	/**
	 * NodeWrapper is only comparable when the {@link #previousNodesCount} is
	 * calculated.
	 */
	@Override
	public int compareTo(NodeWrapper other) {
		return this.previousNodesCount - other.previousNodesCount;
	}

	public long compareXStart(NodeWrapper other) {
		return this.getXStart() - other.getXStart();
	}
}
