package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.wrapper.util.interest.ComputeInterest;

import java.util.List;

/**
 * The {@link WrappedGraphData} is a {@link GraphData} class which also keeps track of a {@link List}<
 * {@link DataNodeWrapper}>. {@link WrappedGraphData} instance never loses any nodes or edges which are given by
 * initialisation.
 * 
 * @author Sam Smulders
 *
 */
public class WrappedGraphData {
	
	private final List<Wrapper> nodeWrappers;
	private final int longestNodePath;
	private int genomeSize;
	
	/**
	 * Initialises an instance of {@link WrappedGraphData}.
	 * 
	 * @param nodeWrappers
	 *            the nodes in the instance
	 * @param genomeSize
	 *            genome count withing the graph data
	 */
	public WrappedGraphData(List<Wrapper> nodeWrappers, int genomeSize) {
		this.nodeWrappers = nodeWrappers;
		this.longestNodePath = Wrapper.computeLongestPaths(this.nodeWrappers);
		this.genomeSize = genomeSize;
		init();
	}
	
	private void init() {
		for (Wrapper wrapper : nodeWrappers) {
			wrapper.calculateX();
		}
		ComputeInterest.compute(nodeWrappers, genomeSize);
	}
	
	public WrappedGraphData(List<DataNode> nodes, List<Edge> edges, int genomeSize) {
		this(DataNodeWrapper.newNodePositionList(nodes, edges), genomeSize);
	}
	
	public WrappedGraphData(AbstractGraphData gd) {
		this(gd.getNodes(), gd.getEdges(), gd.getGenomes().size());
	}
	
	public List<Wrapper> getPositionedNodes() {
		return nodeWrappers;
	}
	
	public int getLongestNodePath() {
		return longestNodePath;
	}
	
	public int getGenomeSize() {
		return genomeSize;
	}
	
}
