package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;

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
	private AbstractGraphData origin;
	private int longestNodePath;
	
	/**
	 * Initialises an instance of {@link WrappedGraphData}.
	 * 
	 * @param origin
	 *            the original
	 * @param nodes
	 *            the nodes of on the graph
	 * @param edges
	 *            the edges of on the graph
	 */
	public WrappedGraphData(AbstractGraphData origin,
			List<DataNodeInterface> nodes, List<Edge> edges) {
		this.origin = origin;
		List<NodePosition> nodePosition = NodePosition.newNodePositionList(
				nodes, edges);
		long max = 0;
		nodeWrappers = new ArrayList<>(nodePosition);
		for (NodePosition node : nodePosition) {
			max = Math.max(node.getXEnd(), max);
		}
		this.size = max;
	}
	
	public WrappedGraphData(WrappedGraphData origin,
			List<NodeWrapper> nodeWrappers) {
		this.origin = origin.origin;
		this.nodeWrappers = nodeWrappers;
		for (NodeWrapper node : nodeWrappers) {
			node.resetPreviousNodesCount();
		}
		for (NodeWrapper node : nodeWrappers) {
			longestNodePath = Math.max(longestNodePath,
					node.calculatePreviousNodesCount());
		}
	}
	
	public WrappedGraphData(AbstractGraphData gd) {
		this(gd, gd.getNodes(), gd.getEdges());
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
	
	public AbstractGraphData getOrigin() {
		return origin;
	}
	
}
