package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.CombinedNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.graph.Node;

import java.io.Serializable;
import java.util.*;

/**
 * GraphModel reads GraphData and makes a new graph of it.
 */
public class GraphModel implements GraphModelInterface {
	protected AbstractGraphData originalGraph;
	protected AbstractGraphData graph;
	
	public GraphModel(AbstractGraphData graphData) {
		this.originalGraph = graphData;
	}
	
	/**
	 * Filters a copy of the {@link GraphDataRepository} and combines all nodes
	 * which can be combined without losing data and removes all dead edges. The
	 * result is saved as {@code graph}.
	 * 
	 * @param filters
	 *            the filters to be applied.
	 */
	public void produceGraph(Collection<Filter<Node>> filters) {
		List<Node> resultNodes = originalGraph.getNodeListClone();
		filter(resultNodes, filters);
		System.out.println(resultNodes.size());
		List<Edge> resultEdges = originalGraph.getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		combineNodes(findCombineableNodes(resultNodes, resultEdges),
				resultNodes, resultEdges);
		graph = new GraphData(originalGraph, resultNodes, resultEdges,
				originalGraph.getGenomes());
	}

	public AbstractGraphData getGraphData() {
		return graph;
	}
	
	/**
	 * Combines all {@link Node}s in the given edges list into
	 * {@link CombinedNode}s, reconnects the {@link CombinedNode}s in the graph
	 * and remove all {@link Node}s which are combined.
	 * 
	 * @param edgesToCombine
	 *            the edges with nodes to combine.
	 * @param nodes
	 *            the list of nodes in the graph
	 * @param edges
	 *            the list of edges in the graph
	 */
	protected void combineNodes(List<Edge> edgesToCombine, List<Node> nodes,
			List<Edge> edges) {
		edges.removeAll(edgesToCombine);
		
		// Hash all edges
		Map<Integer, Edge> fromHash = new HashMap<Integer, Edge>();
		Map<Integer, Edge> toHash = new HashMap<Integer, Edge>();
		for (Edge edge : edgesToCombine) {
			fromHash.put(edge.getFrom().getId(), edge);
			toHash.put(edge.getTo().getId(), edge);
		}
		
		Map<Integer, CombinedNode> nodeReference = new HashMap<Integer, CombinedNode>();
		List<Node> combinedNodes = new ArrayList<Node>();
		
		while (edgesToCombine.size() > 0) {
			List<Edge> foundEdgeGroup = findEdgeGroups(fromHash, toHash,
					edgesToCombine);
			// init CNode
			CombinedNode combinedNode = new CombinedNode(foundEdgeGroup);
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
	 * Reconnects all combined {@link Node}s with the graph.
	 * 
	 * @param edges
	 *            the edges in the graph
	 * @param nodes
	 *            the nodes in the graph
	 * @param nodeReference
	 *            a map of references of the removed nodes to their new
	 *            {@link CombinedNode}s which contain them.
	 */
	protected void reconnectCombinedNodes(List<Edge> edges, List<Node> nodes,
			Map<Integer, CombinedNode> nodeReference) {
		List<Edge> deadEdges = getAllDeadEdges(edges, nodes);
		for (Edge edge : deadEdges) {
			Node nodeTo = edge.getTo();
			Node tempNode = nodeReference.get(nodeTo.getId());
			if (tempNode != null) {
				nodeTo = tempNode;
			}
			
			Node nodeFrom = edge.getFrom();
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
	 *            {@code from} {@link Node}
	 * @param toHash
	 *            a map of all edges, where each is referenced by their
	 *            {@code to} {@link Node}
	 * @param edgesToCombine
	 *            the search list with all edges who needs to be grouped.
	 * @return an {@link List}<{@link Node}> of a complete group of edges.
	 */
	protected List<Edge> findEdgeGroups(Map<Integer, Edge> fromHash,
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
	protected List<Edge> findCombineableNodes(List<Node> nodes, List<Edge> edges) {
		List<Edge> fromEdgesList = findFromEdges(edges);
		List<Edge> toEdgesList = findToEdges(edges);
		toEdgesList.retainAll(fromEdgesList);
		return toEdgesList;
	}
	
	/**
	 * Sorts the edges on their {@code to} {@link Node} and after that on their
	 * {@code from} {@link Node}.
	 * 
	 * @param fromEdges
	 *            the edges to be sorted
	 */
	protected void sortEdgesOnTo(List<Edge> fromEdges) {
		Collections.sort(fromEdges, new SortEdgesToComparator());
	}
	
	/**
	 * Sorts the edges on their {@code from} {@link Node} and after that on
	 * their {@code to} {@link Node}.
	 * 
	 * @param edges
	 *            the edges to be sorted
	 */
	protected void sortEdgesOnFrom(List<Edge> edges) {
		Collections.sort(edges, new SortEdgesFromComparator());
	}
	
	/**
	 * Find all edges where their nodes have only one input.
	 * 
	 * @param edges
	 *            the list of edges
	 * @return a list of all edges of nodes with only one input
	 */
	protected List<Edge> findFromEdges(List<Edge> edges) {
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
	List<Edge> findToEdges(List<Edge> edges) {
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
	 * Removes all edges of which one or both of their nodes is not on the
	 * graph.
	 * 
	 * @param edgeList
	 *            the list of edges in the graph
	 * @param nodeList
	 *            the list of nodes in the graph
	 */
	protected void removeAllDeadEdges(List<Edge> edgeList, List<Node> nodeList) {
		edgeList.removeAll(getAllDeadEdges(edgeList, nodeList));
	}
	
	/**
	 * Finds all the edges on the graph which have one or two nodes which are
	 * not on the graph.
	 * 
	 * @param edgeList
	 *            the list of edges in the graph
	 * @param nodeList
	 *            the list of nodes in the graph
	 * @return a list of all dead edges
	 */
	protected List<Edge> getAllDeadEdges(List<Edge> edgeList,
			List<Node> nodeList) {
		List<Edge> removeList = new ArrayList<Edge>();
		for (Edge edge : edgeList) {
			if (!nodeList.contains(edge.getFrom())
					|| !nodeList.contains(edge.getTo())) {
				removeList.add(edge);
			}
		}
		return removeList;
	}
	
	/**
	 * Apply all filters.
	 * 
	 * @param list
	 *            the list of nodes to be filtered
	 * @param filters
	 *            the list of filters to be applied
	 */
	protected void filter(List<Node> list, Collection<Filter<Node>> filters) {
		for (Filter<Node> filter : filters) {
			filter.filter(list);
		}
	}
	
	/**
	 * Comparator to sort edges on to field.
	 */
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
}
