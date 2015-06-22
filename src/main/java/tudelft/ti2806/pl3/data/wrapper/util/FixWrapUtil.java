package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FixWrapUtil {
	private FixWrapUtil(){
	}
	
	/**
	 * Adds the {@link FixWrapper}s to the given node list and connects them.
	 * 
	 * @param nodes
	 *            the nodes in the remaining layer
	 * @param startFix
	 *            the {@link FixWrapper} on the left
	 * @param endFix
	 *            the {@link FixWrapper} on the right
	 *            
	 */
	public static void addFixNodesToGraph(List<Wrapper> nodes, FixWrapper startFix, FixWrapper endFix) {
		startFix.getOutgoing().add(endFix);
		endFix.getIncoming().add(startFix);
		Set<Genome> genomeSet = new HashSet<>();
		
		connectFixNodes(genomeSet, nodes, startFix, endFix);
		
		startFix.setGenome(genomeSet);
		endFix.setGenome(genomeSet);
		
		nodes.add(startFix);
		nodes.add(endFix);
	}

	/**
	 * Connects the {@link FixWrapper}s to the graph.
	 * 
	 * @param genomeSet
	 *            the set of genomes the fix wrappers should connect
	 * @param nodes
	 *            the nodes in the remaining layer
	 * @param startFix
	 *            the {@link FixWrapper} on the left
	 * @param endFix
	 *            the {@link FixWrapper} on the right
	 */
	private static void connectFixNodes(Set<Genome> genomeSet, List<Wrapper> nodes, FixWrapper startFix,
			FixWrapper endFix) {
		for (Wrapper node : nodes) {
			Set<Genome> genome = node.getGenome();
			genomeSet.addAll(genome);
			final Set<Genome> set = new HashSet<>();
			node.getIncoming().stream().map(Wrapper::getGenome).forEach(set::addAll);
			if (set.size() != genome.size() || !set.containsAll(genome)) {
				node.getIncoming().add(startFix);
				startFix.getOutgoing().add(node);
			}
			set.clear();
			node.getOutgoing().stream().map(Wrapper::getGenome).forEach(set::addAll);
			if (set.size() != genome.size() || !set.containsAll(genome)) {
				node.getOutgoing().add(endFix);
				endFix.getIncoming().add(node);
			}
		}
	}
}
