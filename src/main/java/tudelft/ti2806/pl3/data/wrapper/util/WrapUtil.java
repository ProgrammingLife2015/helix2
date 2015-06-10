package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An utility class to collapse graphs into smaller graphs.
 * 
 * @author Sam Smulders
 */
public final class WrapUtil {
	private WrapUtil() {
	}
	
	/**
	 * Collapses a graph until it converges to a single {@link Wrapper}.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph
	 *         found.
	 */
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		WrappedGraphData graph = original;
		WrappedGraphData lastGraph = original;
		while (graph != null) {
			lastGraph = collapseGraphSimple(graph);
			graph = SpaceWrapUtil.collapseGraph(lastGraph);
		}
		if (lastGraph.getPositionedNodes().size() > 1) {
			lastGraph = applyFixNode(lastGraph);
		}
		return lastGraph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which
	 * can't be wrapped with {@link HorizontalWrapper}s or
	 * {@link VerticalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph
	 *         found, with only using vertical and horizontal options.
	 */
	public static WrappedGraphData collapseGraphSimple(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = HorizontalWrapUtil.collapseGraph(original,
				false);
		if (graph == null) {
			graph = original;
		}
		while (graph != null) {
			lastGraph = graph;
			graph = HorizontalWrapUtil.collapseGraph(graph, true);
			if (graph == null) {
				graph = lastGraph;
			} else {
				lastGraph = graph;
			}
			graph = VerticalWrapUtil.collapseGraph(graph);
		}
		return lastGraph;
	}
	
	/**
	 * Adds a node to the end and start of the graph, connecting to all genome
	 * endings on the graph, to make a last {@link SpaceWrapper} by the
	 * {@link SpaceWrapUtil} possible. And by that fixing the graph, making it
	 * possible to wrap the graph to one single node.
	 * 
	 * @param graph
	 *            the graph to fix
	 * @return a fixed graph
	 */
	public static WrappedGraphData applyFixNode(WrappedGraphData graph) {
		List<Wrapper> nodes = graph.getPositionedNodes();
		FixWrapper startFix = new FixWrapper();
		FixWrapper endFix = new FixWrapper();
		startFix.getOutgoing().add(endFix);
		endFix.getIncoming().add(startFix);
		Set<Genome> genomeSet = new HashSet<Genome>();
		for (Wrapper node : nodes) {
			Set<Genome> genome = node.getGenome();
			genomeSet.addAll(genome);
			final Set<Genome> set = new HashSet<>();
			node.getIncoming().stream().map(Wrapper::getGenome)
					.forEach(a -> set.addAll(a));
			if (set.size() != genome.size() || !set.containsAll(genome)) {
				node.getIncoming().add(startFix);
				startFix.getOutgoing().add(node);
			}
			set.clear();
			node.getOutgoing().stream().map(Wrapper::getGenome)
					.forEach(a -> set.addAll(a));
			if (set.size() != genome.size() || !set.containsAll(genome)) {
				node.getOutgoing().add(endFix);
				endFix.getIncoming().add(node);
			}
		}
		startFix.setGenome(genomeSet);
		endFix.setGenome(genomeSet);
		nodes.add(startFix);
		nodes.add(endFix);
		return WrapUtil.collapseGraph(new WrappedGraphData(nodes));
	}
	
	/**
	 * Wraps a list into a new layer and reconnects the new layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @return a list containing a new layer over the previous layer
	 */
	protected static List<Wrapper> wrapAndReconnect(
			List<Wrapper> nonCombinedNodes, List<CombineWrapper> combinedNodes) {
		Map<Wrapper, Wrapper> map = wrapList(nonCombinedNodes, combinedNodes);
		reconnectLayer(nonCombinedNodes, combinedNodes, map);
		List<Wrapper> list = new ArrayList<Wrapper>(new HashSet<Wrapper>(
				map.values()));
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Reconnects the given layer, using the connections from the previous layer
	 * and applying them to the new layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @param map
	 *            a map mapping all nodes from the previous layer to the new
	 *            layer
	 */
	static void reconnectLayer(List<Wrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes, Map<Wrapper, Wrapper> map) {
		for (Wrapper node : nonCombinedNodes) {
			Wrapper newWrapper = map.get(node);
			for (Wrapper in : node.getIncoming()) {
				if (!newWrapper.getIncoming().contains(map.get(in))) {
					newWrapper.getIncoming().add(map.get(in));
				}
			}
			for (Wrapper out : node.getOutgoing()) {
				if (!newWrapper.getOutgoing().contains(map.get(out))) {
					newWrapper.getOutgoing().add(map.get(out));
				}
			}
		}
		for (CombineWrapper comNode : combinedNodes) {
			for (Wrapper in : comNode.getFirst().getIncoming()) {
				if (!comNode.getIncoming().contains(map.get(in))) {
					comNode.getIncoming().add(map.get(in));
				}
			}
			for (Wrapper out : comNode.getLast().getOutgoing()) {
				if (!comNode.getOutgoing().contains(map.get(out))) {
					comNode.getOutgoing().add(map.get(out));
				}
			}
		}
	}
	
	/**
	 * Creates a new layer from the given nodes and creates a map mapping all
	 * nodes from the previous layer to the new layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @return a map mapping all nodes from the previous layer to the new layer
	 */
	static Map<Wrapper, Wrapper> wrapList(List<Wrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes) {
		Map<Wrapper, Wrapper> map = new HashMap<Wrapper, Wrapper>();
		for (Wrapper node : nonCombinedNodes) {
			SingleWrapper newWrapper = new SingleWrapper(node);
			map.put(node, newWrapper);
		}
		for (CombineWrapper verNode : combinedNodes) {
			for (Wrapper node : verNode.getNodeList()) {
				map.put(node, verNode);
			}
		}
		return map;
	}
}
