package tudelft.ti2806.pl3.util.wrap;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.util.HashableList;
import tudelft.ti2806.pl3.visualization.position.WrappedGraphData;
import tudelft.ti2806.pl3.visualization.position.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.NodePositionWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.VerticalWrapper;

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
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		List<NodePositionWrapper> newLayer = combineNodes(original
				.getPositionedNodes());
		if (newLayer == null) {
			return null;
		}
		return new WrappedGraphData(original, newLayer);
	}

	/**
	 * Combines nodes vertically. Combines all {@link DataNodeInterface}s in the
	 * given list of node into {@link VerticalWrapper}s, reconnects the
	 * {@link VerticalWrapper}s in the graph and remove all
	 * {@link DataNodeInterface}s which are combined from the graph.
	 * 
	 * @param nodes
	 *            the nodes to combine
	 * @return the collapsed version of the given graph<br>
	 *         {@code null} if nothing could be collapsed
	 */
	static List<NodePositionWrapper> combineNodes(
			List<NodePositionWrapper> parentLayer) {
		List<NodePositionWrapper> nonWrappedNodes = new ArrayList<NodePositionWrapper>(
				parentLayer);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<NodePositionWrapper> list : findCombineableNodes(parentLayer)) {
			HorizontalWrapper newNode = new HorizontalWrapper(list);
			combinedNodes.add(newNode);
			nonWrappedNodes.removeAll(list);
		}
		if (combinedNodes.size() == 0) {
			return null;
		}
		return WrapUtil.wrapAndReconnect(nonWrappedNodes, combinedNodes);
	}
	
	/**
	 * Finds all groups of nodes which can be wrapped horizontal.
	 * 
	 * @param nodes
	 *            the nodes on the graph
	 * @return a list of horizontal wrap-able nodes.
	 */
	static List<List<NodePositionWrapper>> findCombineableNodes(
			List<NodePositionWrapper> nodes) {
		List<List<NodePositionWrapper>> foundCombineableNodes = new ArrayList<List<NodePositionWrapper>>();
		List<NodePositionWrapper> iterateList = new ArrayList<NodePositionWrapper>(
				nodes);
		List<NodePositionWrapper> removeFromIterateList = new ArrayList<NodePositionWrapper>();
		/*
		 * Here we iterate over each element in iterateList and over each
		 * element only once, because we keep track of a list of all elements we
		 * iterate over.
		 */
		while (iterateList.size() > 0) {
			for (NodePositionWrapper startNode : iterateList) {
				List<NodePositionWrapper> foundGroup = new ArrayList<NodePositionWrapper>();
				foundGroup.add(startNode);
				// Add all nodes to the right which can be combined.
				NodePositionWrapper node = startNode;
				while (node.getOutgoing().size() == 1
						&& node.getOutgoing().get(0).getIncoming().size() == 1) {
					node = node.getOutgoing().get(0);
					foundGroup.add(node);
				}
				// Add all nodes to the left which can be combined.
				node = startNode;
				while (node.getIncoming().size() == 1
						&& node.getIncoming().get(0).getOutgoing().size() == 1
						&& new HashableList<Genome>(node.getGenome())
								.equals(new HashableList<Genome>(node
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
