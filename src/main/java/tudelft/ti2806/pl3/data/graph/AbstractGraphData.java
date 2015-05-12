package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraphData {
	List<Node> nodes;
	List<Edge> edges;
	List<Genome> genomes;
	
	public abstract List<Node> getNodes();
	
	public abstract List<Edge> getEdges();
	
	public abstract List<Genome> getGenomes();
	
	public abstract AbstractGraphData getOrigin();
	
	/**
	 * Creates a clone of the genomes list without cloning its elements.
	 * 
	 * @return a clone of the genomes list of this graph
	 */
	public List<Genome> getGenomeClone() {
		List<Genome> clone = new ArrayList<Genome>();
		clone.addAll(genomes);
		return clone;
	}
	
	/**
	 * Creates a clone of the edge list without cloning its elements.
	 * 
	 * @return a clone of the edge list of this graph
	 */
	public List<Edge> getEdgeListClone() {
		List<Edge> clone = new ArrayList<Edge>();
		clone.addAll(edges);
		return clone;
	}
	
	/**
	 * Creates a clone of the node list without cloning its elements.
	 * 
	 * @return a clone of the node list
	 */
	public List<Node> getNodeListClone() {
		List<Node> clone = new ArrayList<Node>();
		clone.addAll(nodes);
		return clone;
	}
	
	/**
	 * Get the number of nodes on the longest path on the graph, using the
	 * number of nodes as distance measure.
	 * 
	 * @return returns the number of nodes on the longest path.
	 */
	public abstract int getLongestNodePath();
	
//	/**
//	 * Get the length of the longest path on the graph, using the number of base
//	 * pairs as distance measure.
//	 * 
//	 * @return the number of base pairs on the longest path on the graph.
//	 */
//	public abstract long getSize();
}
