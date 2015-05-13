package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenomeFilter implements Filter<Node> {
	protected final List<String> genomes;
	
	public GenomeFilter(List<String> genomes) {
		this.genomes = genomes;
	}

	@Override
	public void filter(Collection<Node> nodes) {
		List<Node> newList = new ArrayList<>();
		for (Node node : nodes) {
			for(int j = 0; j< node.getSource().length; j++) {
				if(genomes.contains(node.getSource()[j].getIdentifier())) {
					newList.add(node);
					break;
				}
			}
		}
	}
}
