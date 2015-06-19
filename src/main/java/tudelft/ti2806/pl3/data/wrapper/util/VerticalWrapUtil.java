package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.util.HashableCollection;
import tudelft.ti2806.pl3.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An utility class to find and combine nodes which can be combined into
 * {@link VerticalWrapper}.
 *
 * @author Sam Smulders
 */
public final class VerticalWrapUtil {
	private VerticalWrapUtil() {
	}

	/**
	 * Constructs a {@link WrappedGraphData} instance which contains the
	 * vertical collapsed graph of the given graph.
	 *
	 * @param original
	 *            the original graph
	 * @return the collapsed version of the given graph <br>
	 *         {@code null} if nothing could be collapsed
	 */
	@SuppressWarnings("CPD-START")
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		List<Wrapper> newLayer = combineNodes(original.getPositionedNodes());
		if (newLayer == null) {
			return null;
		}
		return new WrappedGraphData(newLayer);
	}

	/**
	 * Combines nodes vertically. Combines all {@link DataNode}s in the given
	 * list of node into {@link VerticalWrapper}s, reconnects the
	 * {@link VerticalWrapper}s in the graph and remove all {@link DataNode}s
	 * which are combined from the graph.
	 *
	 * @param nodes
	 *            the nodes to combine
	 * @return the collapsed version of the given graph<br>
	 *         {@code null} if nothing could be collapsed
	 */
	private static List<Wrapper> combineNodes(List<Wrapper> nodes) {
		Map<Integer, Wrapper> nonWrappedNodes = new HashMap<>(nodes.size());
		List<Integer> nonWrappedNodesOrder = new ArrayList<>(nodes.size());
		for (Wrapper node : nodes) {
			int id = node.getId();
			nonWrappedNodes.put(id, node);
			nonWrappedNodesOrder.add(id);
		}
		List<CombineWrapper> combinedNodes = new ArrayList<>();
		for (List<Wrapper> list : findCombineableNodes(nodes)) {
			CombineWrapper newNode = new VerticalWrapper(list);
			combinedNodes.add(newNode);
			for (Wrapper wrapper : list) {
				nonWrappedNodes.remove(wrapper.getId());
			}
		}
		if (combinedNodes.size() == 0) {
			return null;
		}
		List<Wrapper> result = new ArrayList<>(nonWrappedNodes.values().size());
		for (int id : nonWrappedNodesOrder) {
			Wrapper node = nonWrappedNodes.get(id);
			if (node != null) {
				result.add(node);
			}
		}
		return WrapUtil.wrapAndReconnect(result, combinedNodes);
	}

	@SuppressWarnings("CPD-END")
	/**
	 * Finds all nodes in the graph which could be combined vertically.
	 *
	 * <p>
	 * Vertically-combine-able nodes are nodes with the same incoming and
	 * outgoing connections.
	 *
	 * @return a list of edges which could be combined
	 */
	static List<List<Wrapper>> findCombineableNodes(List<Wrapper> nodes) {
		Map<Pair<HashableCollection<Wrapper>, HashableCollection<Wrapper>>,
				List<Wrapper>> map = new HashMap<>();
		for (Wrapper node : nodes) {
			List<Wrapper> list = map
					.get(new Pair<>(
							new HashableCollection<>(node
									.getIncoming()),
							new HashableCollection<>(node
									.getOutgoing())));
			if (list == null) {
				list = new ArrayList<>();
				map.put(new Pair<>(
								new HashableCollection<>(node.getIncoming()),
								new HashableCollection<>(node.getOutgoing())),
						list);
			}
			list.add(node);
		}
		return map.values().stream().filter(list -> list.size() > 1)
				.collect(Collectors.toList());
	}
}
