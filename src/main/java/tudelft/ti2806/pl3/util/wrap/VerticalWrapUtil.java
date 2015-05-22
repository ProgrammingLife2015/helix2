package tudelft.ti2806.pl3.util.wrap;

import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.util.HashableCollection;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

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
		List<NodeWrapper> newLayer = combineNodes(original.getPositionedNodes());
		if (newLayer == null) {
			return null;
		}
		return new WrappedGraphData(original, newLayer);
	}
	
	/**
<<<<<<< HEAD
	 * Combines nodes vertically. Combines all {@link DataNode}s in the
	 * given list of node into {@link VerticalWrapper}s, reconnects the
	 * {@link VerticalWrapper}s in the graph and remove all
	 * {@link DataNode}s which are combined from the graph.
=======
	 * Combines nodes vertically. Combines all {@link DataNode}s in the given
	 * list of node into {@link VerticalWrapper}s, reconnects the
	 * {@link VerticalWrapper}s in the graph and remove all {@link DataNode}s
	 * which are combined from the graph.
>>>>>>> nodeyposition
	 * 
	 * @param nodes
	 *            the nodes to combine
	 * @return the collapsed version of the given graph<br>
	 *         {@code null} if nothing could be collapsed
	 */
	static List<NodeWrapper> combineNodes(List<NodeWrapper> nodes) {
		List<NodeWrapper> nonWrappedNodes = new ArrayList<NodeWrapper>(nodes);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<NodeWrapper> list : findCombineableNodes(nodes)) {
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
	static List<List<NodeWrapper>> findCombineableNodes(List<NodeWrapper> nodes) {
		Map<Pair<HashableCollection<NodeWrapper>, HashableCollection<NodeWrapper>>,
				List<NodeWrapper>> map = new HashMap<>();
		for (NodeWrapper node : nodes) {
			List<NodeWrapper> list = map
					.get(new Pair<HashableCollection<NodeWrapper>, HashableCollection<NodeWrapper>>(
							new HashableCollection<NodeWrapper>(node
									.getIncoming()),
							new HashableCollection<NodeWrapper>(node
									.getOutgoing())));
			if (list == null) {
				list = new ArrayList<NodeWrapper>();
				map.put(new Pair<HashableCollection<NodeWrapper>, HashableCollection<NodeWrapper>>(
						new HashableCollection<NodeWrapper>(node.getIncoming()),
						new HashableCollection<NodeWrapper>(node.getOutgoing())),
						list);
			}
			list.add(node);
		}
		List<List<NodeWrapper>> combineAbleNodes = new ArrayList<List<NodeWrapper>>();
		for (List<NodeWrapper> list : map.values()) {
			if (list.size() > 1) {
				combineAbleNodes.add(list);
			}
		}
		return combineAbleNodes;
	}
}
