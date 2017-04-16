package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.gene.Gene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractGraphData {
	List<DataNode> nodes;
	List<Edge> edges;
	List<Genome> genomes;
	List<Gene> genes;
	Map<Gene, DataNode> geneToStartNodeMap;
	
	public abstract List<DataNode> getNodes();
	
	public abstract List<Edge> getEdges();

	public abstract List<Gene> getGenes();

	public abstract List<Genome> getGenomes();
	
	public abstract Map<Gene, DataNode> getGeneToStartNodeMap();

	public abstract AbstractGraphData getOrigin();

	/**
	 * Creates a clone of the genomes list without cloning its elements.
	 * 
	 * @return a clone of the genomes list of this graph
	 */
	public List<Genome> getGenomeClone() {
		List<Genome> clone = new ArrayList<>(genomes.size());
		clone.addAll(genomes);
		return clone;
	}
	
	/**
	 * Creates a clone of the edge list without cloning its elements.
	 * 
	 * @return a clone of the edge list of this graph
	 */
	public List<Edge> getEdgeListClone() {
		List<Edge> clone = new ArrayList<>(edges.size());
		clone.addAll(edges);
		return clone;
	}
	
	/**
	 * Creates a clone of the node list without cloning its elements.
	 * 
	 * @return a clone of the node list
	 */
	public List<DataNode> getNodeListClone() {
		List<DataNode> clone = new ArrayList<>(nodes.size());
		clone.addAll(nodes);
		return clone;
	}
}
