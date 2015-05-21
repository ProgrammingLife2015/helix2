package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This filter will filter all nodes depending on a list of genomes.
 * @author Boris Mattijssen
 */
public class GenomeFilter implements Filter<DataNode> {
	protected final List<String> genomes;
	
	public GenomeFilter(List<String> genomes) {
		this.genomes = genomes;
	}

	/**
	 * Filter that removes all nodes that are not in the genome list.
	 * @param nodes
	 *          the list of nodes
	 */
	@Override
	public void filter(List<DataNode> nodes) {
		List<DataNode> remove = new ArrayList<>();
		for (DataNode DataNode : nodes) {
			for (String genome : genomes) {
				if (!Arrays.asList(DataNode.getSource())
						.stream()
						.map(Genome::getIdentifier)
						.collect(Collectors.toList())
							.contains(genome)) {
					remove.add(DataNode);
					break;
				}
			}
		}
		nodes.removeAll(remove);
	}
}
