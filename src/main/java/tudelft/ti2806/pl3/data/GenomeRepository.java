package tudelft.ti2806.pl3.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GenomeRepository {
	private List<Node> genome;
	
	public void loadGraph() throws FileNotFoundException {
		genome = new ArrayList<Node>();
		genome.addAll(GraphParser.parseGraph(new File(
				"data/10_strains_graph/simple_graph.node.graph"), new File(
				"data/10_strains_graph/simple_graph.edge.graph")));
	}
}
