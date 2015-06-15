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
        return new ArrayList<>(genomes);
    }

    /**
     * Creates a clone of the edge list without cloning its elements.
     *
     * @return a clone of the edge list of this graph
     */
    public List<Edge> getEdgeListClone() {
        return new ArrayList<>(edges);
    }

    /**
     * Creates a clone of the node list without cloning its elements.
     *
     * @return a clone of the node list
     */
    public List<DataNode> getNodeListClone() {
        return new ArrayList<>(nodes);
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
