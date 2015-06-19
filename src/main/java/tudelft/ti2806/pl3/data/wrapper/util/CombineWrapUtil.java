package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class CombineWrapUtil {
	private CombineWrapUtil(){
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
