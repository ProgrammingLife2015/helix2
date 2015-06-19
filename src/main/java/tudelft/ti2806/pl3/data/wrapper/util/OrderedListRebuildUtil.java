package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class OrderedListRebuildUtil {
	private OrderedListRebuildUtil() {
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
	public static void fillNonWrappedCollections(List<Wrapper> nodes, Map<Integer, Wrapper> nonWrappedNodes,
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
	public static List<Wrapper> collectNonWrappedNodes(Map<Integer, Wrapper> nonWrappedNodes,
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
