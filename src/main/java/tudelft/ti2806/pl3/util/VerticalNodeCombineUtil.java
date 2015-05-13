package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.node.Node;
import tudelft.ti2806.pl3.data.graph.node.VerticalCombinedNode;
import tudelft.ti2806.pl3.visualization.node.NodePosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerticalNodeCombineUtil {
	
	/**
	 * Combines nodes vertically. Combines all {@link Node}s in the given list
	 * of node into {@link VerticalCombinedNode}s, reconnects the
	 * {@link VerticalCombinedNode}s in the graph and remove all {@link Node}s
	 * which are combined from the graph.
	 * 
	 * @param nodesToCombine
	 *            the nodes to combine
	 * @param nodes
	 *            the list of nodes in the graph
	 * @param edges
	 *            the list of edges in the graph
	 */
	public static void combineNodes(List<List<NodePosition>> nodesToCombine,
			List<Node> nodes, List<Edge> edges) {
		List<VerticalCombinedNode> combinedNodes = new ArrayList<VerticalCombinedNode>();
		for (List<NodePosition> list : nodesToCombine) {
			nodes.removeAll(nodesToCombine);
			List<Node> newlist = new ArrayList<Node>();
			for (NodePosition node : list) {
				newlist.add(node.getNode());
				nodes.remove(node.getNode());
			}
			VerticalCombinedNode newNode = new VerticalCombinedNode(newlist);
			combinedNodes.add(newNode);
		}
		reconnectCombinedNodes(edges, nodes, combinedNodes);
	}
	
	/**
	 * Reconnects all combined {@link Node}s with the graph.
	 * 
	 * @param edges
	 *            the edges in the graph
	 * @param nodes
	 *            the nodes in the graph
	 * @param combinedNodes
	 *            a list of all combined nodes
	 */
	static void reconnectCombinedNodes(List<Edge> edges, List<Node> nodes,
			List<VerticalCombinedNode> combinedNodes) {
		Map<Integer, VerticalCombinedNode> map = new HashMap<Integer, VerticalCombinedNode>();
		for (VerticalCombinedNode node : combinedNodes) {
			for (Node subNode : node.getNodes()) {
				map.put(subNode.getId(), node);
			}
		}
		List<Edge> deadEdges = DeadEdgeUtil.getAllDeadEdges(edges, nodes);
		for (Edge edge : deadEdges) {
			Node fromNode = map.get(edge.getFromId());
			if (fromNode != null) {
				fromNode = edge.getFrom();
			}
			Node toNode = map.get(edge.getToId());
			if (toNode != null) {
				toNode = edge.getTo();
			}
			if (toNode != null && fromNode != null) {
				edges.add(new Edge(toNode, fromNode));
			}
		}
		edges.removeAll(deadEdges);
		nodes.addAll(combinedNodes);
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
	public static List<List<NodePosition>> findCombineableNodes(
			List<NodePosition> nodes) {
		Map<Pair<HashableList<NodePosition>, HashableList<NodePosition>>, List<NodePosition>> map
				= new HashMap<Pair<HashableList<NodePosition>, HashableList<NodePosition>>, List<NodePosition>>();
		for (NodePosition node : nodes) {
			if (node.getIncoming().size() == 1
					&& node.getOutgoing().size() == 1) {
				List<NodePosition> list = map
						.get(new Pair<HashableList<NodePosition>, HashableList<NodePosition>>(
								new HashableList<NodePosition>(node
										.getIncoming()),
								new HashableList<NodePosition>(node
										.getOutgoing())));
				if (list == null) {
					list = new ArrayList<NodePosition>();
					map.put(new Pair<HashableList<NodePosition>, HashableList<NodePosition>>(
							new HashableList<NodePosition>(node.getIncoming()),
							new HashableList<NodePosition>(node.getOutgoing())),
							list);
				}
				list.add(node);
			}
		}
		List<List<NodePosition>> combineAbleNodes = new ArrayList<List<NodePosition>>();
		for (List<NodePosition> list : map.values()) {
			if (list.size() > 1) {
				combineAbleNodes.add(list);
			}
		}
		return combineAbleNodes;
	}
}
