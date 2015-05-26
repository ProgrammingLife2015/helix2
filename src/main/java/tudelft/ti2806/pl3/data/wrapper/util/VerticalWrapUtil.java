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
	static List<Wrapper> combineNodes(List<Wrapper> nodes) {
		List<Wrapper> nonWrappedNodes = new ArrayList<Wrapper>(nodes);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<Wrapper> list : findCombineableNodes(nodes)) {
			CombineWrapper newNode = new VerticalWrapper(list);
			combinedNodes.add(newNode);
			nonWrappedNodes.removeAll(list);
		}
		if (combinedNodes.size() == 0) {
			return null;
		}
		return WrapUtil.wrapAndReconnect(nonWrappedNodes, combinedNodes);
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
					.get(new Pair<HashableCollection<Wrapper>, HashableCollection<Wrapper>>(
							new HashableCollection<Wrapper>(node
									.getIncoming()),
							new HashableCollection<Wrapper>(node
									.getOutgoing())));
			if (list == null) {
				list = new ArrayList<Wrapper>();
				map.put(new Pair<HashableCollection<Wrapper>, HashableCollection<Wrapper>>(
						new HashableCollection<Wrapper>(node.getIncoming()),
						new HashableCollection<Wrapper>(node.getOutgoing())),
						list);
			}
			list.add(node);
		}
		List<List<Wrapper>> combineAbleNodes = new ArrayList<List<Wrapper>>();
		for (List<Wrapper> list : map.values()) {
			if (list.size() > 1) {
				combineAbleNodes.add(list);
			}
		}
		return combineAbleNodes;
	}
}
