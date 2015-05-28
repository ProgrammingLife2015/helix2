package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;
import tudelft.ti2806.pl3.util.DoneDeque;

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
	protected float x;
	
	protected List<Wrapper> incoming = new ArrayList<>();
	protected List<Wrapper> outgoing = new ArrayList<>();
	
	private int previousNodesCount = -1;
	private int interest = 0;
	
	/**
	 * @return the maximum number of base pairs that can be passed when
	 *         following the graph from the start of this node to its end.
	 */
	public abstract long getBasePairCount();
	
	/**
	 * @return the distance between the start x and end x of this node.
	 */
	public abstract int getWidth();
	
	public int getPreviousNodesCount() {
		return previousNodesCount;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public abstract void calculateX();
	
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
	
	/**
	 * Computes the longest path for each {@link Wrapper} in the list and
	 * returns the length of the longest path found.
	 * 
	 * @param nodeWrappers
	 *            the list of the {@link Wrapper}s to compute the longest path
	 *            of
	 * @return the length of the longest path found
	 */
	public static int computeLongestPaths(List<Wrapper> nodeWrappers) {
		DoneDeque<Wrapper> leftToRight = new DoneDeque<>(nodeWrappers.size());
		// Find all first wrappers
		for (Wrapper wrapper : nodeWrappers) {
			if (wrapper.getIncoming().size() == 0) {
				leftToRight.add(wrapper);
			}
		}
		int max = 0;
		DoneDeque<Wrapper> rightToLeft = new DoneDeque<>(nodeWrappers.size());
		// Calculate all previous node counts from left to right
		for (int i = nodeWrappers.size(); i > 0; i--) {
			Wrapper wrapper = leftToRight.poll();
			// Find all last wrappers
			if (wrapper.getOutgoing().size() == 0) {
				rightToLeft.addAll(wrapper.getIncoming());
			} else {
				for (Wrapper out : wrapper.getOutgoing()) {
					if (leftToRight.doneAll(out.getIncoming())) {
						leftToRight.add(out);
					}
				}
			}
			max = Math.max(wrapper.calculatePreviousNodesCount(), max);
		}
		// Improve all previous node counts from right to left
		while (!rightToLeft.isEmpty()) {
			Wrapper wrapper = rightToLeft.poll();
			for (Wrapper in : wrapper.getIncoming()) {
				if (rightToLeft.doneAll(in.getOutgoing())) {
					rightToLeft.add(in);
				}
			}
			wrapper.shiftPreviousNodeCount();
		}
		return max;
	}
	
	/**
	 * Shifts the previous node count as far to the right as possible.
	 */
	private void shiftPreviousNodeCount() {
		if (this.incoming.size() == 0) {
			return;
		}
		int min = Integer.MAX_VALUE;
		for (Wrapper wrapper : this.outgoing) {
			min = Math.min(wrapper.getPreviousNodesCount(), min);
		}
		this.previousNodesCount = min - 1;
	}
	
	/**
	 * Calculate the number of nodes on the longest path to this node.
	 */
	private int calculatePreviousNodesCount() {
		int max = 0;
		for (Wrapper incomingNode : this.incoming) {
			max = Math.max(max, incomingNode.previousNodesCount + 1);
		}
		this.previousNodesCount = max;
		return max;
	}
}
