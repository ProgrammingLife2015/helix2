package tudelft.ti2806.pl3.util.wrap;

import tudelft.ti2806.pl3.visualization.position.WrappedGraphData;
import tudelft.ti2806.pl3.visualization.position.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.NodePositionWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.SingleWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * An utility class to collapse graphs into smaller graphs.
 * 
 * @author Sam Smulders
 */
public final class WrapUtil {
	private WrapUtil() {
	}
	
	/**
	 * Collapses a graph until it converges or until the {@code maxIteration} is
	 * reached.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @param maxIterations
	 *            the maximum number of iterations the algorithm will take.
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph
	 *         found.
	 */
	public static WrappedGraphData collapseGraph(WrappedGraphData original,
			int maxIterations) {
		WrappedGraphData lastGraph = null;
		WrappedGraphData graph = original;
		int iterations = 0;
		while (iterations++ < maxIterations && graph != null) {
			while (graph != null) {
				lastGraph = graph;
				graph = VerticalWrapUtil.collapseGraph(graph);
				if (graph == null) {
					graph = lastGraph;
				}
				graph = HorizontalWrapUtil.collapseGraph(graph);
			}
			graph = SpaceWrapUtil.collapseGraph(lastGraph);
		}
		if (graph == null) {
			return lastGraph;
		}
		return graph;
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
	protected static List<NodePositionWrapper> wrapAndReconnect(
			List<NodePositionWrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes) {
		Map<NodePositionWrapper, NodePositionWrapper> map = wrapList(
				nonCombinedNodes, combinedNodes);
		reconnectLayer(nonCombinedNodes, combinedNodes, map);
		return new ArrayList<NodePositionWrapper>(new HashSet<NodePositionWrapper>(map.values()));
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
	static void reconnectLayer(List<NodePositionWrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes,
			Map<NodePositionWrapper, NodePositionWrapper> map) {
		for (NodePositionWrapper node : nonCombinedNodes) {
			NodePositionWrapper newWrapper = map.get(node);
			for (NodePositionWrapper in : node.getIncoming()) {
				if (!newWrapper.getIncoming().contains(map.get(in))) {
					newWrapper.getIncoming().add(map.get(in));
				}
			}
			for (NodePositionWrapper out : node.getOutgoing()) {
				if (!newWrapper.getOutgoing().contains(map.get(out))) {
					newWrapper.getOutgoing().add(map.get(out));
				}
			}
		}
		for (CombineWrapper comNode : combinedNodes) {
			for (NodePositionWrapper in : comNode.getFirst().getIncoming()) {
				if (!comNode.getIncoming().contains(map.get(in))) {
					comNode.getIncoming().add(map.get(in));
				}
			}
			for (NodePositionWrapper out : comNode.getLast().getOutgoing()) {
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
	static Map<NodePositionWrapper, NodePositionWrapper> wrapList(
			List<NodePositionWrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes) {
		Map<NodePositionWrapper, NodePositionWrapper> map
				= new HashMap<NodePositionWrapper, NodePositionWrapper>();
		for (NodePositionWrapper node : nonCombinedNodes) {
			SingleWrapper newWrapper = new SingleWrapper(node);
			map.put(node, newWrapper);
		}
		for (CombineWrapper verNode : combinedNodes) {
			for (NodePositionWrapper node : verNode.getNodeList()) {
				map.put(node, verNode);
			}
		}
		return map;
	}
}
