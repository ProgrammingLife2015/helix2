package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.PositionedGraphData;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.node.HorizontalWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorizontalNodeCombineUtil {
	/**
	 * Combines all {@link DataNodeInterface}s in the given edges list into
	 * {@link HorizontalWrapper}s, reconnects the
	 * {@link HorizontalWrapper}s in the graph and remove all {@link DataNodeInterface}s
	 * which are combined from the graph.
	 * 
	 * @param edgesToCombine
	 *            the edges with nodes to combine.
	 * @param nodes
	 *            the list of nodes in the graph
	 * @param edges
	 *            the list of edges in the graph
	 */
	public static void combineNodes(List<Edge> edgesToCombine,
			List<DataNodeInterface> nodes, List<Edge> edges) {
		edges.removeAll(edgesToCombine);
		
		// Hash all edges
		Map<Integer, Edge> fromHash = new HashMap<Integer, Edge>();
		Map<Integer, Edge> toHash = new HashMap<Integer, Edge>();
		for (Edge edge : edgesToCombine) {
			fromHash.put(edge.getFrom().getId(), edge);
			toHash.put(edge.getTo().getId(), edge);
		}
		
		Map<Integer, HorizontalWrapper> nodeReference
				= new HashMap<Integer, HorizontalWrapper>();
		List<DataNodeInterface> combinedNodes = new ArrayList<DataNodeInterface>();
		
		while (edgesToCombine.size() > 0) {
			List<Edge> foundEdgeGroup = findEdgeGroups(fromHash, toHash,
					edgesToCombine);
			// init CNode
			HorizontalWrapper combinedNode = new HorizontalWrapper(
					foundEdgeGroup);
			combinedNodes.add(combinedNode);
			nodes.removeAll(combinedNode.getNodeList());
			nodeReference.put(foundEdgeGroup.get(0).getFrom().getId(),
					combinedNode);
			nodeReference.put(foundEdgeGroup.get(foundEdgeGroup.size() - 1)
					.getTo().getId(), combinedNode);
		}
		// Remove and replace edges with combined nodes.
		reconnectCombinedNodes(edges, nodes, nodeReference);
		nodes.addAll(combinedNodes);
	}
	
	/**
	 * Reconnects all combined {@link DataNodeInterface}s with the graph.
	 * 
	 * @param edges
	 *            the edges in the graph
	 * @param nodes
	 *            the nodes in the graph
	 * @param nodeReference
	 *            a map of references of the removed nodes to their new
	 *            {@link HorizontalWrapper}s which contain them.
	 */
	public static void reconnectCombinedNodes(List<Edge> edges,
			List<DataNodeInterface> nodes, Map<Integer, HorizontalWrapper> nodeReference) {
		List<Edge> deadEdges = DeadEdgeUtil.getAllDeadEdges(edges, nodes);
		for (Edge edge : deadEdges) {
			DataNodeInterface nodeTo = edge.getTo();
			DataNodeInterface tempNode = nodeReference.get(nodeTo.getId());
			if (tempNode != null) {
				nodeTo = tempNode;
			}
			
			DataNodeInterface nodeFrom = edge.getFrom();
			tempNode = nodeReference.get(nodeFrom.getId());
			if (tempNode != null) {
				nodeFrom = tempNode;
			}
			if (nodeFrom != nodeTo) {
				edges.add(new Edge(nodeFrom, nodeTo));
			}
		}
		edges.removeAll(deadEdges);
	}
	
	/**
	 * Finds a connected group of edges and removes this group from the search
	 * list.
	 * 
	 * @param fromHash
	 *            a map of all edges, where each is referenced by their
	 *            {@code from} {@link DataNodeInterface}
	 * @param toHash
	 *            a map of all edges, where each is referenced by their
	 *            {@code to} {@link DataNodeInterface}
	 * @param edgesToCombine
	 *            the search list with all edges who needs to be grouped.
	 * @return an {@link List}<{@link DataNodeInterface}> of a complete group of edges.
	 */
	static List<Edge> findEdgeGroups(Map<Integer, Edge> fromHash,
			Map<Integer, Edge> toHash, List<Edge> edgesToCombine) {
		Edge startEdge = edgesToCombine.remove(0);
		List<Edge> edgeList = new ArrayList<Edge>();
		edgeList.add(startEdge);
		// Search to left
		Edge searchEdge = fromHash.get(startEdge.getTo().getId());
		while (searchEdge != null) {
			edgesToCombine.remove(searchEdge);
			edgeList.add(searchEdge);
			searchEdge = fromHash.get(searchEdge.getTo().getId());
		}
		// Search to right
		searchEdge = toHash.get(startEdge.getFrom().getId());
		while (searchEdge != null) {
			edgesToCombine.remove(searchEdge);
			edgeList.add(0, searchEdge);
			searchEdge = toHash.get(searchEdge.getFrom().getId());
		}
		return edgeList;
	}
	
	/**
	 * Finds all nodes in the graph which could be combined without combining
	 * the edges.
	 * 
	 * @param nodes
	 *            the nodes on the graph
	 * @param edges
	 *            the edges on the graph
	 * @return a list of edges which could be combined
	 */
	public static List<Edge> findCombineableNodes(List<DataNodeInterface> nodes,
			List<Edge> edges) {
		List<Edge> fromEdgesList = findFromEdges(edges);
		List<Edge> toEdgesList = findToEdges(edges);
		toEdgesList.retainAll(fromEdgesList);
		return toEdgesList;
	}
	
	/**
	 * Find all edges where their nodes have only one input.
	 * 
	 * @param edges
	 *            the list of edges
	 * @return a list of all edges of nodes with only one input
	 */
	static List<Edge> findFromEdges(List<Edge> edges) {
		sortEdgesOnFrom(edges);
		List<Edge> foundEdges = new ArrayList<Edge>();
		Edge lastEdge = null;
		boolean found = true;
		for (Edge edge : edges) {
			if (lastEdge != null
					&& edge.getFrom().getId() == lastEdge.getFrom().getId()) {
				found = true;
			} else {
				if (found == false) {
					foundEdges.add(lastEdge);
				}
				found = false;
				lastEdge = edge;
			}
		}
		if (found == false) {
			foundEdges.add(lastEdge);
		}
		return foundEdges;
	}
	
	/**
	 * Find all edges where their nodes have only one output.
	 * 
	 * @param edges
	 *            the list of edges
	 * @return a list of all edges of nodes with only one output
	 */
	static List<Edge> findToEdges(List<Edge> edges) {
		sortEdgesOnTo(edges);
		List<Edge> foundEdges = new ArrayList<Edge>();
		Edge lastEdge = null;
		boolean found = true;
		for (Edge edge : edges) {
			if (lastEdge != null
					&& edge.getTo().getId() == lastEdge.getTo().getId()) {
				found = true;
			} else {
				if (found == false) {
					foundEdges.add(lastEdge);
				}
				found = false;
				lastEdge = edge;
			}
		}
		
		if (found == false) {
			foundEdges.add(lastEdge);
		}
		return foundEdges;
	}
	
	/**
	 * Sorts the edges on their {@code to} {@link DataNodeInterface} and after that on their
	 * {@code from} {@link DataNodeInterface}.
	 * 
	 * @param fromEdges
	 *            the edges to be sorted
	 */
	public static void sortEdgesOnTo(List<Edge> fromEdges) {
		Collections.sort(fromEdges, new SortEdgesToComparator());
	}
	
	/**
	 * Sorts the edges on their {@code from} {@link DataNodeInterface} and after that on
	 * their {@code to} {@link DataNodeInterface}.
	 * 
	 * @param edges
	 *            the edges to be sorted
	 */
	public static void sortEdgesOnFrom(List<Edge> edges) {
		Collections.sort(edges, new SortEdgesFromComparator());
	}
	
	/**
	 * Comparator to sort edges on to field.
	 */
	@SuppressWarnings("serial")
	static class SortEdgesToComparator implements Comparator<Edge>,
			Serializable {
		@Override
		public int compare(Edge o1, Edge o2) {
			int dir = (int) Math
					.signum(o1.getTo().getId() - o2.getTo().getId());
			if (dir == 0) {
				return (int) Math.signum(o1.getFrom().getId()
						- o2.getFrom().getId());
			} else {
				return dir;
			}
		}
	}
	
	/**
	 * Comparator to sort edges on from field.
	 */
	@SuppressWarnings("serial")
	static class SortEdgesFromComparator implements Comparator<Edge>,
			Serializable {
		@Override
		public int compare(Edge o1, Edge o2) {
			int dir = (int) Math.signum(o1.getFrom().getId()
					- o2.getFrom().getId());
			if (dir == 0) {
				return (int) Math.signum(o1.getTo().getId()
						- o2.getTo().getId());
			} else {
				return dir;
			}
		}
	}
	
	public static void findAndCombineNodes(AbstractGraphData gd) {
		combineNodes(findCombineableNodes(gd.getNodes(), gd.getEdges()),
				gd.getNodes(), gd.getEdges());
	}

	public static PositionedGraphData horizontalCollapseGraph(
			PositionedGraphData positionedGraphData) {
		// TODO Auto-generated method stub
		return null;
	}
}
