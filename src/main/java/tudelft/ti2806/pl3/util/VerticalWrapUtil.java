package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.graph.PositionedGraphData;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.node.CombineWrapper;
import tudelft.ti2806.pl3.visualization.node.NodePositionWrapper;
import tudelft.ti2806.pl3.visualization.node.VerticalWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerticalWrapUtil extends WrapUtil {
	private VerticalWrapUtil() {
	}

	/**
	 * Constructs a {@link PositionedGraphData} instance which contains the
	 * vertical collapsed graph of the given graph.
	 * 
	 * <p>
	 * After construction the new previous node count is updated.
	 * 
	 * @param original
	 *            the original graph
	 * @return the collapsed version of the given graph
	 */
	public static PositionedGraphData collapseGraph(PositionedGraphData original) {
		List<NodePositionWrapper> result = combineNodes(original
				.getPositionedNodes());
		for (NodePositionWrapper node : result) {
			node.calculatePreviousNodesCount();
		}
		return new PositionedGraphData(original, result);
	}
	
	/**
	 * Combines nodes vertically. Combines all {@link DataNodeInterface}s in the
	 * given list of node into {@link VerticalWrapper}s, reconnects the
	 * {@link VerticalWrapper}s in the graph and remove all
	 * {@link DataNodeInterface}s which are combined from the graph.
	 * 
	 * @param nodes
	 *            the nodes to combine
	 */
	static List<NodePositionWrapper> combineNodes(
			List<NodePositionWrapper> nodes) {
		List<NodePositionWrapper> nonWrappedNodes = new ArrayList<NodePositionWrapper>(
				nodes);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<NodePositionWrapper> list : findCombineableNodes(nodes)) {
			VerticalWrapper newNode = new VerticalWrapper(list);
			combinedNodes.add(newNode);
			nonWrappedNodes.removeAll(list);
		}
		return wrapNodes(nonWrappedNodes, combinedNodes);
	}
	
	/**
	 * Finds all nodes in the graph which could be combined vertically.
	 * 
	 * <p>
	 * Vertically-combine-able nodes are nodes with the same incoming and
	 * outgoing connections.
	 * 
	 * @return a list of edges which could be combined
	 */
	static List<List<NodePositionWrapper>> findCombineableNodes(
			List<NodePositionWrapper> nodes) {
		Map<Pair<HashableList<NodePositionWrapper>, HashableList<NodePositionWrapper>>, List<NodePositionWrapper>> map
				= new HashMap<Pair<HashableList<NodePositionWrapper>, HashableList<NodePositionWrapper>>, List<NodePositionWrapper>>();
		for (NodePositionWrapper node : nodes) {
			List<NodePositionWrapper> list = map
					.get(new Pair<HashableList<NodePositionWrapper>, HashableList<NodePositionWrapper>>(
							new HashableList<NodePositionWrapper>(node
									.getIncoming()),
							new HashableList<NodePositionWrapper>(node
									.getOutgoing())));
			if (list == null) {
				list = new ArrayList<NodePositionWrapper>();
				map.put(new Pair<HashableList<NodePositionWrapper>, HashableList<NodePositionWrapper>>(
						new HashableList<NodePositionWrapper>(node
								.getIncoming()),
						new HashableList<NodePositionWrapper>(node
								.getOutgoing())), list);
			}
			list.add(node);
		}
		List<List<NodePositionWrapper>> combineAbleNodes = new ArrayList<List<NodePositionWrapper>>();
		for (List<NodePositionWrapper> list : map.values()) {
			if (list.size() > 1) {
				combineAbleNodes.add(list);
			}
		}
		return combineAbleNodes;
	}
}
