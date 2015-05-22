package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.util.DoneDeque;

import java.util.List;

/**
 * The {@link WrappedGraphData} is a {@link GraphData} class which also keeps
 * track of a {@link List}<{@link DataNodeWrapper}>. {@link WrappedGraphData}
 * instance never loses any nodes or edges which are given by initialisation.
 * 
 * @author Sam Smulders
 *
 */
public class WrappedGraphData {
	
	private List<Wrapper> nodeWrappers;
	private final int longestNodePath;
	
	/**
	 * Initialises an instance of {@link WrappedGraphData}.
	 * 
	 * @param nodeWrappers
	 *            the nodes in the instance
	 */
	public WrappedGraphData(List<Wrapper> nodeWrappers) {
		this.nodeWrappers = nodeWrappers;
<<<<<<< HEAD
		this.longestNodePath = Wrapper.computeLongestPaths(this.nodeWrappers);
=======
		this.longestNodePath = computeLongestPath();
	}
	
	private int computeLongestPath() {
		DoneDeque<Wrapper> deque = new DoneDeque<>(nodeWrappers.size());
		for (Wrapper wrapper : this.nodeWrappers) {
			if (wrapper.getIncoming().size() == 0) {
				deque.add(wrapper);
			}
		}
		int max = 0;
		for (int i = this.nodeWrappers.size(); i > 0; i--) {
			Wrapper wrapper = deque.poll();
			for (Wrapper out : wrapper.getOutgoing()) {
				if (deque.doneAll(out.getIncoming())) {
					deque.add(out);
				}
			}
			max = Math.max(wrapper.calculatePreviousNodesCount(), max);
		}
		return max;
>>>>>>> Dead edge performence increase & calculate previous node count fix
	}
	
	public WrappedGraphData(List<DataNode> nodes, List<Edge> edges) {
		this(DataNodeWrapper.newNodePositionList(nodes, edges));
	}
	
	public WrappedGraphData(AbstractGraphData gd) {
		this(gd.getNodes(), gd.getEdges());
	}
	
	public List<Wrapper> getPositionedNodes() {
		return nodeWrappers;
	}
	
	public int getLongestNodePath() {
		return longestNodePath;
	}
	
}
