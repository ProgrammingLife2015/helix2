package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.graph.PositionedGraphData;
import tudelft.ti2806.pl3.visualization.node.CombineWrapper;
import tudelft.ti2806.pl3.visualization.node.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.node.NodePositionWrapper;

import java.util.ArrayList;
import java.util.List;

public class HorizontalWrapUtil extends WrapUtil {
	private HorizontalWrapUtil() {
	}
	
	/**
	 * Constructs a {@link PositionedGraphData} instance which contains the
	 * horizontal collapsed graph of the given graph.
	 * 
	 * <p>
	 * After construction the new previous node count is updated.
	 * 
	 * @param original
	 *            the original graph
	 * @return the collapsed version of the given graph
	 */
	public static PositionedGraphData collapseGraph(PositionedGraphData original) {
		List<NodePositionWrapper> result = collapseNodeList(original
				.getPositionedNodes());
		for (NodePositionWrapper node : result) {
			node.calculatePreviousNodesCount();
		}
		return new PositionedGraphData(original, result);
	}
	
	/**
	 * Constructs a new layer of {@link NodePositionWrapper}s containing the
	 * previous layer. The new layer is collapsed where possible.
	 * 
	 * @param parentLayer
	 *            the parent layer
	 * @return the new layer over the given parent layer
	 */
	static List<NodePositionWrapper> collapseNodeList(
			List<NodePositionWrapper> parentLayer) {
		List<NodePositionWrapper> nonWrappedNodes = new ArrayList<NodePositionWrapper>(
				parentLayer);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<NodePositionWrapper> list : findCombineableNodes(parentLayer)) {
			HorizontalWrapper newNode = new HorizontalWrapper(list);
			combinedNodes.add(newNode);
			nonWrappedNodes.removeAll(list);
		}
		return wrapNodes(nonWrappedNodes, combinedNodes);
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
						&& node.getIncoming().get(0).getOutgoing().size() == 1) {
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
