package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;

import java.util.Collection;
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

	private final int longestNodePath;
	private Collection<Wrapper> nodeWrappers;

	/**
	 * Initialises an instance of {@link WrappedGraphData}.
	 *
	 * @param nodeWrappers
	 *            the nodes in the instance
	 */
	public WrappedGraphData(Collection<Wrapper> nodeWrappers) {
		this.nodeWrappers = nodeWrappers;
		this.longestNodePath = Wrapper.computeLongestPaths(this.nodeWrappers);
	}

	public WrappedGraphData(List<DataNode> nodes, List<Edge> edges) {
		this(DataNodeWrapper.newNodePositionList(nodes, edges));
	}

	public WrappedGraphData(AbstractGraphData gd) {
		this(gd.getNodes(), gd.getEdges());
	}

	public Collection<Wrapper> getPositionedNodes() {
		return nodeWrappers;
	}

	public int getLongestNodePath() {
		return longestNodePath;
	}

}
