package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This filter will filter all nodes depending on a list of genomes.
 *
 * @author Boris Mattijssen
 */
public class GenomeFilter implements Filter<DataNode> {
    public static final String NAME = "genome";
    protected final List<String> genomes;

    public GenomeFilter(List<String> genomes) {
        this.genomes = genomes;
    }

    /**
     * Filter that removes all nodes that are not in the genome list.
     * It also removes the genomes from the node that should be filtered out.
     *
     * @param nodes
     *         the list of nodes
     */
    @Override
    public void filter(List<DataNode> nodes) {
        List<DataNode> remove = new ArrayList<>();
        for (DataNode dataNode : nodes) {
            Set<Genome> currentGenomeList = new HashSet<>();
            currentGenomeList.addAll(
                    dataNode.getSource().stream()
                            .filter(genome -> genomes.contains(genome.getIdentifier()))
                            .collect(Collectors.toList())
            );
            dataNode.setCurrentGenomeList(currentGenomeList);
            if (currentGenomeList.size() == 0) {
                remove.add(dataNode);
            }
        }
        nodes.removeAll(remove);
    }
}