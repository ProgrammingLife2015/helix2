package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.graph.node.DataNode;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link WrappedGraphData} is a {@link GraphData} class which also keeps
 * track of a {@link List}<{@link NodePosition}>. {@link WrappedGraphData}
 * instance never loses any nodes or edges which are given by initialisation.
 * 
 * @author Sam Smulders
 *
 */
public class WrappedGraphData {
	
	private List<NodeWrapper> nodeWrappers;
	private long size;
	private int longestNodePath;
	
	/**
	 * Initialises an instance of {@link WrappedGraphData}.
	 * 
	 * @param nodeWrappers
	 *            the nodes in the instance
	 */
	public WrappedGraphData(List<NodeWrapper> nodeWrappers) {
		this.nodeWrappers = nodeWrappers;
		for (NodeWrapper node : nodeWrappers) {
			node.resetPreviousNodesCount();
		}
		for (NodeWrapper node : nodeWrappers) {
			longestNodePath = Math.max(longestNodePath,
					node.calculatePreviousNodesCount());
		}
	}
	
	public WrappedGraphData(List<DataNode> nodes, List<Edge> edges) {
		this(NodePosition.newNodePositionList(nodes, edges));
	}
	
	public WrappedGraphData(AbstractGraphData gd) {
		this(gd.getNodes(), gd.getEdges());
	}
	
	public List<NodeWrapper> getPositionedNodes() {
		return nodeWrappers;
	}
	
	public long getSize() {
		return size;
	}
	
	public int getLongestNodePath() {
		return longestNodePath;
	}
	
}
