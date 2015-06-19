package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.util.HashableCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An utility class to find and combine nodes which can be combined into
 * {@link HorizontalWrapper}.
 * 
 * @author Sam Smulders
 */
public final class HorizontalWrapUtil {
	private HorizontalWrapUtil() {
	}
	
	/**
	 * Constructs a {@link WrappedGraphData} instance which contains the
	 * horizontal collapsed graph of the given graph.
	 * 
	 * @param original
	 *            the original graph
	 * @return the collapsed version of the given graph <br>
	 *         {@code null} if nothing could be collapsed
	 */
	@SuppressWarnings("CPD-START")
	public static WrappedGraphData collapseGraph(WrappedGraphData original,
			boolean canUnwrap) {
		List<Wrapper> newLayer = combineNodes(original.getPositionedNodes(),
				canUnwrap);
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
	private static List<Wrapper> combineNodes(List<Wrapper> nodes,
			boolean canUnwrap) {
		Map<Integer, Wrapper> nonWrappedNodes = new HashMap<>(nodes.size());
		List<Integer> nonWrappedNodesOrder = new ArrayList<>(nodes.size());
		for (Wrapper node : nodes) {
			int id = node.getId();
			nonWrappedNodes.put(id, node);
			nonWrappedNodesOrder.add(id);
		}
		List<CombineWrapper> combinedNodes = new ArrayList<>();
		for (List<Wrapper> list : findCombineableNodes(nodes)) {
			HorizontalWrapper newNode = new HorizontalWrapper(list, canUnwrap);
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
	 * Finds all groups of nodes which can be wrapped horizontal.
	 * 
	 * @param nodes
	 *            the nodes on the graph
	 * @return a list of horizontal wrap-able nodes.
	 */
	static List<List<Wrapper>> findCombineableNodes(List<Wrapper> nodes) {
		List<List<Wrapper>> foundCombineableNodes = new ArrayList<>();
		Map<Integer, Wrapper> iterateList = new HashMap<>(nodes.size());
		List<Integer> iterateListOrder = new ArrayList<>(nodes.size());
		for (Wrapper node : nodes) {
			int id = node.getId();
			iterateList.put(id, node);
			iterateListOrder.add(id);
		}
		List<Wrapper> removeFromIterateList = new ArrayList<>();
		/*
		 * Here we iterate over each element in iterateList and over each
		 * element only once, because we keep track of a list of all elements we
		 * iterate over.
		 */
		while (iterateList.size() > 0) {
			for (int startNodeId : iterateListOrder) {
				Wrapper startNode = iterateList.get(startNodeId);
				if (startNode == null) {
					continue;
				}
				
				List<Wrapper> foundGroup = new ArrayList<>();
				foundGroup.add(startNode);
				// Add all nodes to the right which can be combined.
				Wrapper node = startNode;
				while (node.getOutgoing().size() == 1
						&& node.getOutgoing().get(0).getIncoming().size() == 1) {
					node = node.getOutgoing().get(0);
					foundGroup.add(node);
				}
				// Add all nodes to the left which can be combined.
				node = startNode;
				while (node.getIncoming().size() == 1
						&& node.getIncoming().get(0).getOutgoing().size() == 1
						&& new HashableCollection<>(node.getGenome())
								.equals(new HashableCollection<>(node
										.getIncoming().get(0).getGenome()))) {
					node = node.getIncoming().get(0);
					foundGroup.add(0, node);
				}
				removeFromIterateList.addAll(foundGroup);
				if (foundGroup.size() > 1) {
					foundCombineableNodes.add(foundGroup);
					break;
				}
			}
			for (Wrapper wrapper : removeFromIterateList) {
				iterateList.remove(wrapper.getId());
			}
			removeFromIterateList.clear();
		}
		return foundCombineableNodes;
	}
}
