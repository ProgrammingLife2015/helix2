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
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found.
	 */
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = HorizontalWrapUtil.collapseGraph(original, false);
		if (graph == null) {
			graph = lastGraph;
		}
		graph = collapseGraphSpacial(graph);
		if (graph == null) {
			graph = lastGraph;
		}
		if (graph.getPositionedNodes().size() > 1) {
			return applyFixNode(graph);
		}
		return graph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which can't be wrapped with
	 * {@link SpaceWrapper}s, {@link HorizontalWrapper}s or {@link VerticalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found, with only using vertical,
	 *         horizontal and spatial options.
	 */
	static WrappedGraphData collapseGraphSpacial(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = original;
		while (graph != null) {
			lastGraph = collapseGraphVertical(graph);
			graph = SpaceWrapUtil.collapseGraph(lastGraph);
		}
		return lastGraph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which can't be wrapped with
	 * {@link HorizontalWrapper}s or {@link VerticalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found, with only using vertical and
	 *         horizontal options.
	 */
	private static WrappedGraphData collapseGraphVertical(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = original;
		while (graph != null) {
			lastGraph = collapseGraphHorizontal(graph);
			graph = VerticalWrapUtil.collapseGraph(lastGraph);
		}
		return lastGraph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which can't be wrapped with
	 * {@link HorizontalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found, with only using horizontal
	 *         options.
	 */
	static WrappedGraphData collapseGraphHorizontal(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = original;
		while (graph != null) {
			lastGraph = graph;
			graph = HorizontalWrapUtil.collapseGraph(lastGraph, true);
		}
		return lastGraph;
	}
	
	/**
	 * Adds a node to the end and start of the graph, connecting to all genome endings on the graph, to make a last
	 * {@link SpaceWrapper} by the {@link SpaceWrapUtil} possible. And by that fixing the graph, making it possible 
	 * to wrap the graph to one single node.
	 * 
	 * @param graph
	 *            the graph to fix
	 * @return a fixed graph
	 */
	public static WrappedGraphData applyFixNode(WrappedGraphData graph) {
		List<Wrapper> nodes = graph.getPositionedNodes();

		FixWrapper startFix = new FixWrapper(-1);
		FixWrapper endFix = new FixWrapper(-2);
		
		addFixNodesToGraph(nodes, startFix, endFix);
		
		WrappedGraphData wrappedGraph = collapseGraphSpacial(new WrappedGraphData(nodes));
		
		startFix.setX(-1);
		endFix.setX(wrappedGraph.getPositionedNodes().get(0).getWidth() + 1);
		
		return wrappedGraph;
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
	private static void addFixNodesToGraph(List<Wrapper> nodes, FixWrapper startFix, FixWrapper endFix) {
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

	/**
	 * Wraps a list into a new layer and reconnects the new layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @return a list containing a new layer over the previous layer
	 */
	static List<Wrapper> wrapAndReconnect(List<Wrapper> nonCombinedNodes, List<CombineWrapper> combinedNodes) {
		Map<Wrapper, Wrapper> map = wrapList(nonCombinedNodes, combinedNodes);
		reconnectLayer(nonCombinedNodes, combinedNodes, map);
		List<Wrapper> list = new ArrayList<>(new HashSet<>(map.values()));
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Reconnects the given layer, using the connections from the previous layer and applying them to the new layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @param map
	 *            a map mapping all nodes from the previous layer to the new layer
	 */
	static void reconnectLayer(List<Wrapper> nonCombinedNodes, List<CombineWrapper> combinedNodes,
			Map<Wrapper, Wrapper> map) {
		reconnectNonCombinedNodes(nonCombinedNodes, map);
		reconnectCombinedNodes(combinedNodes, map);
	}

	/**
	 * Reconnects the nodes which where not combined to the given layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param map
	 *            a map mapping all nodes from the previous layer to the new layer
	 */
	private static void reconnectNonCombinedNodes(List<Wrapper> nonCombinedNodes, Map<Wrapper, Wrapper> map) {
		for (Wrapper node : nonCombinedNodes) {
			Wrapper newWrapper = map.get(node);
			node.getIncoming().stream().filter(in -> !newWrapper.getIncoming().contains(map.get(in)))
			.forEach(in -> newWrapper.getIncoming().add(map.get(in)));
			node.getOutgoing().stream().filter(out -> !newWrapper.getOutgoing().contains(map.get(out)))
			.forEach(out -> newWrapper.getOutgoing().add(map.get(out)));
		}
	}


	/**
	 * Reconnects the nodes which where combined to the given layer.
	 * 
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @param map
	 *            a map mapping all nodes from the previous layer to the new layer
	 */
	private static void reconnectCombinedNodes(List<CombineWrapper> combinedNodes, Map<Wrapper, Wrapper> map) {
		for (CombineWrapper comNode : combinedNodes) {
			comNode.getFirst().getIncoming().stream().filter(in -> !comNode.getIncoming()
					.contains(map.get(in))) .forEach(in -> comNode.getIncoming().add(map.get(in)));
			comNode.getLast().getOutgoing().stream().filter(out -> !comNode.getOutgoing()
					.contains(map.get(out))) .forEach(out -> comNode.getOutgoing()
							.add(map.get(out)));
		}
	}

	/**
	 * Creates a new layer from the given nodes and creates a map mapping all nodes from the previous layer 
	 * to the new layer.
	 * 
	 * @param nonCombinedNodes
	 *            the nodes that are not combined
	 * @param combinedNodes
	 *            the nodes that are combined, and are already of the new layer
	 * @return a map mapping all nodes from the previous layer to the new layer
	 */
	private static Map<Wrapper, Wrapper> wrapList(List<Wrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes) {
		Map<Wrapper, Wrapper> map = new HashMap<>();
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
	
	/**
	 * Fills the order list and map with the given order.
	 * 
	 * @param nodes
	 *            the nodes to fill the map with
	 * @param nonWrappedNodes
	 *            the map to fill
	 * @param nonWrappedNodesOrder
	 *            the order list to fill
	 */
	static void fillNonWrappedCollections(List<Wrapper> nodes, Map<Integer, Wrapper> nonWrappedNodes,
			List<Integer> nonWrappedNodesOrder) {
		for (Wrapper node : nodes) {
			int id = node.getId();
			nonWrappedNodes.put(id, node);
			nonWrappedNodesOrder.add(id);
		}
	}
	
	/**
	 * Collect non wrapped nodes from the maintained wrapper order and wrapper map.
	 * 
	 * @param nonWrappedNodes
	 *            the map to fill the list with
	 * @param nonWrappedNodesOrder
	 *            the order to apply
	 * @return a list with the non combined nodes in the previous order
	 */
	static List<Wrapper> collectNonWrappedNodes(Map<Integer, Wrapper> nonWrappedNodes,
			List<Integer> nonWrappedNodesOrder) {
		List<Wrapper> result = new ArrayList<>(nonWrappedNodes.values().size());
		for (int id : nonWrappedNodesOrder) {
			Wrapper node = nonWrappedNodes.get(id);
			if (node != null) {
				result.add(node);
			}
		}
		return result;
	}
}
