package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This filter will filter all nodes depending on a list of genomes.
<<<<<<< HEAD
 * @author Boris Mattijssen
 */
public class GenomeFilter implements Filter<DataNode> {
	protected final List<String> genomes;
	
	public GenomeFilter(List<String> genomes) {
		this.genomes = genomes;
	}

	/**
	 * Filter that removes all nodes that are not in the genome list.
	 * It also removes the genomes from the node that should be filtered out.
	 * @param nodes
	 *          the list of nodes
	 */
	@Override
	public void filter(List<DataNode> nodes) {
		List<DataNode> remove = new ArrayList<>();
		for (DataNode dataNode : nodes) {
			for (String genome : genomes) {
				if (dataNode.getSource()
						.stream()
						.map(Genome::getIdentifier)
						.collect(Collectors.toList())
							.contains(genome)) {
					remove.add(dataNode);
					break;
				}
			}
			for (Genome genome : dataNode.getSource()) {
				if(!genomes.contains(genome.getIdentifier())) {
					dataNode.getSource().remove(genome);
				}
			}
		}
		nodes.removeAll(remove);
	}
}
