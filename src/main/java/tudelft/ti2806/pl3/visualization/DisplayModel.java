package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;

import tudelft.ti2806.pl3.data.CNode;
import tudelft.ti2806.pl3.data.Edge;
import tudelft.ti2806.pl3.data.Node;
import tudelft.ti2806.pl3.data.filter.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayModel {
	protected List<Filter<Node>> nodeFilters = new ArrayList<Filter<Node>>();
	protected List<Node> nodes;
	protected List<Edge> edges;
	
	public DisplayModel(List<Edge> edgeList, List<Node> nodeList) {
		this.edges = edgeList;
		this.nodes = nodeList;
	}
	
	public Graph produceGraph() {
		List<Node> resultNodes = getNodeListClone();
		filter(resultNodes);
		List<Edge> resultEdges = getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		combineNodes(findCombineableNodes(resultNodes, resultEdges),
				resultNodes, resultEdges);
		// calculateNodePositions(); // view
		return null;
	}
	
	void combineNodes(List<Edge> edgesToCombine, List<Node> nodes2,
			List<Edge> edges2) {
		edges2.removeAll(edgesToCombine);
		
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
			nodes2.removeAll(combinedNode.getNodeList());
			nodeReference.put(foundEdgeGroup.get(0).getFrom().getNodeId(),
					combinedNode);
			nodeReference.put(foundEdgeGroup.get(foundEdgeGroup.size() - 1)
					.getTo().getNodeId(), combinedNode);
		}
		// Remove and replace edges with combined nodes.
		reconnectCombinedNodes(edges2, nodes2, nodeReference);
		nodes2.addAll(combinedNodes);
	}
	
	void reconnectCombinedNodes(List<Edge> edges2, List<Node> nodes2,
			Map<Integer, CNode> nodeReference) {
		List<Edge> deadEdges = getAllDeadEdges(edges2, nodes2);
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
				edges2.add(new Edge(nodeFrom, nodeTo));
			}
		}
		edges2.removeAll(deadEdges);
	}
	
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
					&& edge.getTo().getNodeId() 
					== lastEdge.getTo().getNodeId()) {
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
	
	void filter(List<Node> list) {
		for (Filter<Node> filter : nodeFilters) {
			filter.filter(list);
		}
	}
	
	List<Edge> getEdgeListClone() {
		List<Edge> clone = new ArrayList<Edge>();
		clone.addAll(edges);
		return clone;
	}
	
	List<Node> getNodeListClone() {
		List<Node> clone = new ArrayList<Node>();
		clone.addAll(nodes);
		return clone;
	}
}
