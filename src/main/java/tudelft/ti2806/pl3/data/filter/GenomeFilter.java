package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Node;

import java.util.List;

public class GenomeFilter extends Filter<Node> {
	protected final String genome;
	
	public GenomeFilter(String genome) {
		this.genome = genome;
	}
	
	@Override
	protected void calculateFilter(List<Node> list) {
		for (Node node : list) {
			for (String source : node.getSource()) {
				if (source.equals(genome)) {
					filter.add(node);
				}
			}
		}
	}
}
