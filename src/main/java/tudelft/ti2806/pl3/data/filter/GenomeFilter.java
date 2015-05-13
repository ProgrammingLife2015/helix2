package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenomeFilter implements Filter<Node> {
	protected final List<String> genomes;
	
	public GenomeFilter(List<String> genomes) {
		this.genomes = genomes;
	}

	@Override
	public void filter(List<Node> nodes) {
		List<Node> remove = new ArrayList<>();
		for (Node node : nodes) {
			for(String genome : genomes) {
				if(!Arrays.asList(node.getSource())
						.stream()
						.map(Genome::getIdentifier)
						.collect(Collectors.toList())
							.contains(genome)) {
					remove.add(node);
					break;
				}
			}
		}
		nodes.removeAll(remove);
	}
}
