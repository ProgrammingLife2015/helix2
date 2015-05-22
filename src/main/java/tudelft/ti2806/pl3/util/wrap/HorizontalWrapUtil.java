package tudelft.ti2806.pl3.util.wrap;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.util.HashableCollection;
import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.ArrayList;
import java.util.List;

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
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		List<NodeWrapper> newLayer = combineNodes(original.getPositionedNodes());
		if (newLayer == null) {
			return null;
		}
		return new WrappedGraphData(newLayer);
	}
	
	/**
	 * Combines nodes vertically. Combines all {@link DataNode}s in the
	 * given list of node into {@link VerticalWrapper}s, reconnects the
	 * {@link VerticalWrapper}s in the graph and remove all
	 * {@link DataNode}s which are combined from the graph.
	 * 
	 * @param nodes
	 *            the nodes to combine
	 * @return the collapsed version of the given graph<br>
	 *         {@code null} if nothing could be collapsed
	 */
	static List<NodeWrapper> combineNodes(List<NodeWrapper> parentLayer) {
		List<NodeWrapper> nonWrappedNodes = new ArrayList<NodeWrapper>(
				parentLayer);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<NodeWrapper> list : findCombineableNodes(parentLayer)) {
			HorizontalWrapper newNode = new HorizontalWrapper(list);
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
	 * Finds all groups of nodes which can be wrapped horizontal.
	 * 
	 * @param nodes
	 *            the nodes on the graph
	 * @return a list of horizontal wrap-able nodes.
	 */
	static List<List<NodeWrapper>> findCombineableNodes(List<NodeWrapper> nodes) {
		List<List<NodeWrapper>> foundCombineableNodes = new ArrayList<List<NodeWrapper>>();
		List<NodeWrapper> iterateList = new ArrayList<NodeWrapper>(nodes);
		List<NodeWrapper> removeFromIterateList = new ArrayList<NodeWrapper>();
		/*
		 * Here we iterate over each element in iterateList and over each
		 * element only once, because we keep track of a list of all elements we
		 * iterate over.
		 */
		while (iterateList.size() > 0) {
			for (NodeWrapper startNode : iterateList) {
				List<NodeWrapper> foundGroup = new ArrayList<NodeWrapper>();
				foundGroup.add(startNode);
				// Add all nodes to the right which can be combined.
				NodeWrapper node = startNode;
				while (node.getOutgoing().size() == 1
						&& node.getOutgoing().get(0).getIncoming().size() == 1) {
					node = node.getOutgoing().get(0);
					foundGroup.add(node);
				}
				// Add all nodes to the left which can be combined.
				node = startNode;
				while (node.getIncoming().size() == 1
						&& node.getIncoming().get(0).getOutgoing().size() == 1
						&& new HashableCollection<Genome>(node.getGenome())
								.equals(new HashableCollection<Genome>(node
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
			iterateList.removeAll(removeFromIterateList);
			removeFromIterateList.clear();
		}
		return foundCombineableNodes;
	}
}
