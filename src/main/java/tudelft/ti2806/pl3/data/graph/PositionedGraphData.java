package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.Node;
import tudelft.ti2806.pl3.visualization.node.NodePosition;

import java.util.List;

/**
 * The {@link PositionedGraphData} is a {@link GraphData} class which also keeps
 * track of a {@link List}<{@link NodePosition}>.
 * 
 * @author Sam Smulders
 *
 */
public class PositionedGraphData extends GraphData {
	
	private List<NodePosition> nodePositions;
	private long size;
	
	/**
	 * Initialises an instance of {@link PositionedGraphData}.
	 * 
	 * @param origin
	 *            the original
	 * @param nodes
	 *            the nodes of on the graph
	 * @param edges
	 *            the edges of on the graph
	 * @param genomes
	 *            the genomes on the graph
	 */
	public PositionedGraphData(AbstractGraphData origin, List<Node> nodes,
			List<Edge> edges, List<Genome> genomes) {
		super(origin, nodes, edges, genomes);
		this.nodePositions = NodePosition.newNodePositionList(nodes, edges);
		calculateMaxSize();
	}
	
	private void calculateMaxSize() {
		long max = 0;
		for (NodePosition node : this.nodePositions) {
			max = Math.max(node.getXEnd(), max);
		}
		this.size = max;
	}
	
	public PositionedGraphData(AbstractGraphData graphData) {
		this(graphData, graphData.getNodeListClone(), graphData
				.getEdgeListClone(), graphData.getGenomeClone());
	}
	
	public List<NodePosition> getPositionedNodes() {
		return nodePositions;
	}
	
	public long getSize() {
		return size;
	}
	
}
