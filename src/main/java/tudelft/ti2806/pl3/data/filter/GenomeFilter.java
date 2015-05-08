package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Node;

import java.util.List;

public class GenomeFilter extends Filter<Node> {
	protected final String genome;
	
	public GenomeFilter(String genome) {
		this.genome = genome;
	}
	
	@Override
	public void calculateFilter(List<Node> list) {
		for (Node node : list) {
			for (Genome source : node.getSource()) {
				if (source.equals(genome)) {
					filter.add(node);
				}
			}
		}
	}
}
