package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.Node;

import java.util.List;

/**
 * GraphData parses the data as nodes,edges and genomes.
 */
public class GraphData extends AbstractGraphData {
	AbstractGraphData origin;
	
	/**
	 * Constructs an instance of {@code GraphData}.
	 * 
	 * @param origin
	 *            the original of which this GraphData is constructed from
	 * @param nodes
	 *            the nodes of the graph
	 * @param edges
	 *            the edges of the graph
	 * @param genomes
	 *            all {@link Genome} that are present in the graph
	 */
	public GraphData(AbstractGraphData origin, List<Node> nodes,
			List<Edge> edges, List<Genome> genomes) {
		this.origin = origin;
		this.nodes = nodes;
		this.edges = edges;
		this.genomes = genomes;
	}
	
	public GraphData(AbstractGraphData originalGraph) {
		this(originalGraph, originalGraph.getNodeListClone(), originalGraph
				.getEdgeListClone(), originalGraph.getGenomeClone());
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	public List<Genome> getGenomes() {
		return genomes;
	}
	
	public AbstractGraphData getOrigin() {
		return origin;
	}
	
	@Override
	public int getLongestNodePath() {
		return origin.getLongestNodePath();
	}
}
