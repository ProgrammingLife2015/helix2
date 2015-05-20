package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class NodeWrapper implements Comparable<NodeWrapper> {
	
	protected float y;
	protected List<NodeWrapper> incoming = new ArrayList<NodeWrapper>();
	protected List<NodeWrapper> outgoing = new ArrayList<NodeWrapper>();
	protected int previousNodesCount = -1;
	
	public abstract long getXStart();
	
	public abstract long getXEnd();
	
	public abstract long getWidth();
	
	public int getPreviousNodesCount() {
		return previousNodesCount;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
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

	public void setIncoming(List<NodeWrapper> incoming) {
		this.incoming = incoming;
	}

	public void setOutgoing(List<NodeWrapper> outgoing) {
		this.outgoing = outgoing;
	}

	public abstract String getIdString();
	
	public abstract Set<Genome> getGenome();
	
	public abstract void calculate(WrapperOperation wrapperSequencer,
			NodeWrapper container);
	
	/**
	 * NodeWrapper is only comparable when the {@link #previousNodesCount} is
	 * calculated.
	 */
	@Override
	public int compareTo(NodeWrapper other) {
		return this.previousNodesCount - other.previousNodesCount;
	}
	
	public void resetPreviousNodesCount() {
		this.previousNodesCount = -1;
	}
	

	public abstract void collectDataNodes(List<DataNodeInterface> list);

	/**
	 * Get all {@link DataNodeInterface}s in this node and its children
	 * @return
	 *      list of {@link DataNodeInterface}s
	 */
	public List<DataNodeInterface> getDataNodes() {
		List<DataNodeInterface> dataNodeList = new ArrayList<>();
		collectDataNodes(dataNodeList);
		return dataNodeList;
	}
}
