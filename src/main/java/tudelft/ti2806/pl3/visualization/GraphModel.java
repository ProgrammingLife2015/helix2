package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.CNode;
import tudelft.ti2806.pl3.data.Edge;
import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.GraphData;
import tudelft.ti2806.pl3.data.Node;
import tudelft.ti2806.pl3.data.SNode;
import tudelft.ti2806.pl3.data.filter.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphModel {
	// protected List<Filter<Node>> nodeFilters = new ArrayList<Filter<Node>>();
	// protected List<Node> nodes;
	// protected List<Edge> edges;
	
	// public GraphModel(List<Edge> edgeList, List<Node> nodeList) {
	// this.edges = edgeList;
	// this.nodes = nodeList;
	// }
	
	protected GraphData originalGraph;
	protected GraphData graph;
	
	public GraphModel(GraphData graph) {
		this.originalGraph = graph;
	}
	
	/**
	 * Filters a copy of the {@link GraphData} and combines all nodes which can
	 * be combined without losing data and removes all dead edges.
	 * 
	 * The result is saved as {@code graph}.
	 * 
	 * @param filters
	 *            the filters to be applied.
	 */
	public void produceGraph(List<Filter<Node>> filters) {
		List<Node> resultNodes = getNodeListClone();
		filter(resultNodes, filters);
		List<Edge> resultEdges = getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		combineNodes(findCombineableNodes(resultNodes, resultEdges),
				resultNodes, resultEdges);
		graph = new GraphData(resultNodes, resultEdges);
	}
	
	// demo
	public static void main(String[] s) {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		Node[] nodes = new Node[] { new SNode(0, null, 0, 0, new byte[0]),
				new SNode(1, null, 0, 0, new byte[0]),
				new SNode(2, null, 0, 0, new byte[0]),
				new SNode(3, null, 0, 0, new byte[0]),
				new SNode(4, null, 0, 0, new byte[0]),
				new SNode(5, null, 0, 0, new byte[0]),
				new SNode(6, null, 0, 0, new byte[0]),
				new SNode(7, null, 0, 0, new byte[0]),
				new SNode(8, null, 0, 0, new byte[0]),
				new SNode(9, null, 0, 0, new byte[0]) };
		
		for (Node node : nodes) {
			nodeList.add(node);
		}
		Map<String, Edge> map = new HashMap<String, Edge>();
		map.put("0-1", new Edge(nodes[0], nodes[1]));
		map.put("0-2", new Edge(nodes[0], nodes[2]));
		map.put("1-3", new Edge(nodes[1], nodes[3]));
		map.put("2-3", new Edge(nodes[2], nodes[3]));
		map.put("3-4", new Edge(nodes[3], nodes[4]));
		map.put("4-5", new Edge(nodes[4], nodes[5]));
		map.put("5-6", new Edge(nodes[5], nodes[6]));
		map.put("5-7", new Edge(nodes[5], nodes[7]));
		map.put("7-8", new Edge(nodes[7], nodes[8]));
		map.put("8-9", new Edge(nodes[8], nodes[9]));
		List<Edge> edgeList = new ArrayList<Edge>();
		edgeList.addAll(map.values());
		
		GraphModel dm = new GraphModel(new GraphData(nodeList, edgeList));
		dm.produceGraph(new ArrayList<Filter<Node>>());
		DisplayView.getGraph("", dm.getGraph().getNodes(),
				dm.getGraph().getEdges()).display();
	}
	
	public GraphData getGraph() {
		return graph;
	}
	
	/**
	 * Combines all {@link Node}s in the given edges list into {@link CNode}s,
	 * reconnects the {@link CNode}s in the graph and remove all {@link Node}s
	 * which are combined.
	 * 
	 * @param edgesToCombine
	 *            the edges with nodes to combine.
	 * @param nodes
	 *            the list of nodes in the graph
	 * @param edges
	 *            the list of edges in the graph
	 */
	void combineNodes(List<Edge> edgesToCombine, List<Node> nodes,
			List<Edge> edges) {
		edges.removeAll(edgesToCombine);
		
		// Hash all edges
		Map<Integer, Edge> fromHash = new HashMap<Integer, Edge>();
		Map<Integer, Edge> toHash = new HashMap<Integer, Edge>();
		for (Edge edge : edgesToCombine) {
			fromHash.put(edge.getFrom().getNodeId(), edge);
			toHash.put(edge.getTo().getNodeId(), edge);
		}
		
		Map<Integer, CNode> nodeReference = new HashMap<Integer, CNode>();
		List<Node> combinedNodes = new ArrayList<Node>();
		
		while (edgesToCombine.size() > 0) {
			List<Edge> foundEdgeGroup = findEdgeGroups(fromHash, toHash,
					edgesToCombine);
			// init CNode
			CNode combinedNode = new CNode(foundEdgeGroup);
			combinedNodes.add(combinedNode);
			nodes.removeAll(combinedNode.getNodeList());
			nodeReference.put(foundEdgeGroup.get(0).getFrom().getNodeId(),
					combinedNode);
			nodeReference.put(foundEdgeGroup.get(foundEdgeGroup.size() - 1)
					.getTo().getNodeId(), combinedNode);
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
	 *            {@link CNode}s which contain them.
	 */
	void reconnectCombinedNodes(List<Edge> edges, List<Node> nodes,
			Map<Integer, CNode> nodeReference) {
		List<Edge> deadEdges = getAllDeadEdges(edges, nodes);
		for (Edge edge : deadEdges) {
			Node nodeTo = edge.getTo();
			Node tempNode = nodeReference.get(nodeTo.getNodeId());
			if (tempNode != null) {
				nodeTo = tempNode;
			}
			
			Node nodeFrom = edge.getFrom();
			tempNode = nodeReference.get(nodeFrom.getNodeId());
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
	List<Edge> findEdgeGroups(Map<Integer, Edge> fromHash,
			Map<Integer, Edge> toHash, List<Edge> edgesToCombine) {
		Edge startEdge = edgesToCombine.remove(0);
		List<Edge> edgeList = new ArrayList<Edge>();
		edgeList.add(startEdge);
		// Search to left
		Edge searchEdge = fromHash.get(startEdge.getTo().getNodeId());
		while (searchEdge != null) {
			edgesToCombine.remove(searchEdge);
			edgeList.add(searchEdge);
			searchEdge = fromHash.get(searchEdge.getTo().getNodeId());
		}
		// Search to right
		searchEdge = toHash.get(startEdge.getFrom().getNodeId());
		while (searchEdge != null) {
			edgesToCombine.remove(searchEdge);
			edgeList.add(0, searchEdge);
			searchEdge = toHash.get(searchEdge.getFrom().getNodeId());
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
	List<Edge> findCombineableNodes(List<Node> nodes, List<Edge> edges) {
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
	void sortEdgesOnTo(List<Edge> fromEdges) {
		Collections.sort(fromEdges, new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				int dir = (int) Math.signum(o1.getTo().getNodeId()
						- o2.getTo().getNodeId());
				if (dir == 0) {
					return (int) Math.signum(o1.getFrom().getNodeId()
							- o2.getFrom().getNodeId());
				} else {
					return dir;
				}
			}
		});
	}
	
	/**
	 * Sorts the edges on their {@code from} {@link Node} and after that on
	 * their {@code to} {@link Node}.
	 * 
	 * @param fromEdges
	 *            the edges to be sorted
	 */
	void sortEdgesOnFrom(List<Edge> edges2) {
		Collections.sort(edges2, new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				int dir = (int) Math.signum(o1.getFrom().getNodeId()
						- o2.getFrom().getNodeId());
				if (dir == 0) {
					return (int) Math.signum(o1.getTo().getNodeId()
							- o2.getTo().getNodeId());
				} else {
					return dir;
				}
			}
		});
	}
	
	/**
	 * Find all edges where their nodes have only one input.
	 * 
	 * @param edges
	 *            the list of edges
	 * @return a list of all edges of nodes with only one input
	 */
	List<Edge> findFromEdges(List<Edge> edges) {
		sortEdgesOnFrom(edges);
		List<Edge> foundEdges = new ArrayList<Edge>();
		Edge lastEdge = null;
		boolean found = true;
		for (Edge edge : edges) {
			if (lastEdge != null
					&& edge.getFrom().getNodeId() == lastEdge.getFrom()
							.getNodeId()) {
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
					&& edge.getTo().getNodeId() == lastEdge.getTo().getNodeId()) {
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
	
	void removeAllDeadEdges(List<Edge> edgeList, List<Node> nodeList) {
		edgeList.removeAll(getAllDeadEdges(edgeList, nodeList));
	}
	
	List<Edge> getAllDeadEdges(List<Edge> edgeList, List<Node> nodeList) {
		List<Edge> removeList = new ArrayList<Edge>();
		for (Edge edge : edgeList) {
			if ((!nodeList.contains(edge.getFrom()))
					|| (!nodeList.contains(edge.getTo()))) {
				removeList.add(edge);
			}
		}
		return removeList;
	}
	
	void filter(List<Node> list, List<Filter<Node>> filters) {
		for (Filter<Node> filter : filters) {
			filter.filter(list);
		}
	}
	
	List<Edge> getEdgeListClone() {
		List<Edge> clone = new ArrayList<Edge>();
		clone.addAll(originalGraph.getEdges());
		return clone;
	}
	
	List<Node> getNodeListClone() {
		List<Node> clone = new ArrayList<Node>();
		clone.addAll(originalGraph.getNodes());
		return clone;
	}
}
