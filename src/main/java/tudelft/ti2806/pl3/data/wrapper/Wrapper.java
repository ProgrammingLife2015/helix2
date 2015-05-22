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
	protected List<Wrapper> incoming = new ArrayList<Wrapper>();
	protected List<Wrapper> outgoing = new ArrayList<Wrapper>();
	private int previousNodesCount = -1;
	private int interest = 0;
	
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
	 */
	public int calculatePreviousNodesCount() {
		int max = 0;
		for (Wrapper incomingNode : this.getIncoming()) {
			max = Math.max(max, incomingNode.previousNodesCount + 1);
		}
		this.previousNodesCount = max;
		return max;
	}
	
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
		DoneDeque<Wrapper> deque = new DoneDeque<>(nodeWrappers.size());
		for (Wrapper wrapper : nodeWrappers) {
			if (wrapper.getIncoming().size() == 0) {
				deque.add(wrapper);
			}
		}
		int max = 0;
		for (int i = nodeWrappers.size(); i > 0; i--) {
			Wrapper wrapper = deque.poll();
			for (Wrapper out : wrapper.getOutgoing()) {
				if (deque.doneAll(out.getIncoming())) {
					deque.add(out);
				}
			}
			max = Math.max(wrapper.calculatePreviousNodesCount(), max);
		}
		return max;
	}
}
