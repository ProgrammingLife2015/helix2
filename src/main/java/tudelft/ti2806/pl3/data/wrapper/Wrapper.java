package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Wrappers are used to wrap the {@link DataNode}s into a smaller graph.
 * 
 * @author Sam Smulders
 */
@SuppressWarnings("EQ_COMPARETO_USE_OBJECT_EQUALS")
public abstract class Wrapper implements Comparable<Wrapper> {
	
	protected float y;
	protected List<Wrapper> incoming = new ArrayList<Wrapper>();
	protected List<Wrapper> outgoing = new ArrayList<Wrapper>();
	private int previousNodesCount = -1;
	private int interest = 0;
	
//	public abstract long getXStart();
//	
//	public abstract long getXEnd();
	
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
		for (Wrapper incomingNode : this.getIncoming()) {
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
//	public long calculateWhitespaceOnRightSide() {
//		long min = Long.MAX_VALUE;
//		for (Wrapper incomingNode : this.getOutgoing()) {
//			min = Math.min(min, incomingNode.getXStart());
//		}
//		return min - this.getXEnd();
//	}
	
	public List<Wrapper> getIncoming() {
		return incoming;
	}
	
	public List<Wrapper> getOutgoing() {
		return outgoing;
	}
	
	public void setIncoming(List<Wrapper> incoming) {
		this.incoming = incoming;
	}
	
	public void setOutgoing(List<Wrapper> outgoing) {
		this.outgoing = outgoing;
	}
	
	public abstract String getIdString();
	
	public abstract Set<Genome> getGenome();
	
	public abstract void calculate(WrapperOperation wrapperSequencer,
			Wrapper container);
	
	/**
	 * NodeWrapper is only comparable when the {@link #previousNodesCount} is
	 * calculated.
	 */
	@Override
	public int compareTo(Wrapper other) {
		return this.previousNodesCount - other.previousNodesCount;
	}
	
	public void resetPreviousNodesCount() {
		this.previousNodesCount = -1;
	}
	
	public abstract void collectDataNodes(List<DataNode> list);
	
	/**
	 * Get all {@link DataNode}s in this node and its children.
	 * 
	 * @return list of {@link DataNode}s
	 */
	public List<DataNode> getDataNodes() {
		List<DataNode> dataNodeList = new ArrayList<>();
		collectDataNodes(dataNodeList);
		return dataNodeList;
	}
	
	public int getInterest() {
		return interest;
	}
	
	public void addInterest(int interest) {
		this.interest += interest;
	}
}
