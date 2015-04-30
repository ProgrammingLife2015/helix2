package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;

import tudelft.ti2806.pl3.data.Edge;
import tudelft.ti2806.pl3.data.Node;
import tudelft.ti2806.pl3.data.filter.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DisplayModel {
	protected List<Filter<Node>> nodeFilters = new ArrayList<Filter<Node>>();
	protected List<Node> nodes;
	protected List<Edge> edges;
	
	public Graph produceGraph() {
		List<Node> resultNodes = cloneNodeList(nodes);
		filter(resultNodes);
		List<Edge> resultEdges = cloneEdgeList(edges);
		removeAllDeadEdges(resultEdges, resultNodes);
		findCombineableNodes(nodes, edges);
		// calculateNodePositions(); // view
		return null;
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
	public static List<Edge> findCombineableNodes(List<Node> nodes,
			List<Edge> edges) {
		// find all edges from nodes with only one input
		List<Edge> fromEdgesList = findFromEdges(edges);
		sortEdgesOnTo(fromEdgesList);
		Iterator<Edge> fromEdges = fromEdgesList.iterator();
		Iterator<Edge> toEdges = findToEdges(edges).iterator();
		
		List<Edge> foundEdges = new ArrayList<Edge>();
		if (!fromEdges.hasNext() || !toEdges.hasNext()) {
			return foundEdges;
		}
		
		Edge fromEdge = fromEdges.next();
		Edge toEdge = toEdges.next();
		while (true) {
			System.out.println(fromEdge.toString() + " - " + toEdge.toString());
			if (toEdge.getFrom() == fromEdge.getTo()) {
				foundEdges.add(toEdge);
				if (!fromEdges.hasNext() || !toEdges.hasNext()) {
					break;
				}
				fromEdge = fromEdges.next();
				toEdge = toEdges.next();
			} else {
				int dir = getDir(fromEdge, toEdge);
				if (dir == -1) {
					if (!fromEdges.hasNext()) {
						break;
					}
					fromEdge = fromEdges.next();
				} else {
					if (!toEdges.hasNext()) {
						break;
					}
					toEdge = toEdges.next();
				}
			}
		}
		return foundEdges;
	}
	
	private static void sortEdgesOnTo(List<Edge> _fromEdges) {
		Collections.sort(_fromEdges, new Comparator<Edge>() {
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
	
	private static void sortEdgesOnFrom(List<Edge> edges2) {
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
	
	private static int getDir(Edge fromEdge, Edge toEdge) {
		return (int) Math.signum(fromEdge.getTo().getNodeId()
				- toEdge.getFrom().getNodeId());
	}
	
	/**
	 * Find all edges where their nodes have only one input.
	 * 
	 * @param edges
	 * @return a list of all edges of nodes with only one input
	 */
	private static List<Edge> findFromEdges(List<Edge> edges) {
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
		return foundEdges;
	}
	
	/**
	 * Find all edges where their nodes have only one output.
	 * 
	 * @param edges
	 * @return a list of all edges of nodes with only one output
	 */
	private static List<Edge> findToEdges(List<Edge> edges) {
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
		return foundEdges;
	}
	
	private void removeAllDeadEdges(List<Edge> edgeList, List<Node> nodeList) {
		List<Edge> removeList = new ArrayList<Edge>();
		for (Edge edge : edgeList) {
			if ((!nodeList.contains(edge.getFrom()))
					|| (!nodeList.contains(edge.getTo()))) {
				removeList.add(edge);
			}
		}
		edgeList.removeAll(removeList);
	}
	
	private List<Node> cloneNodeList(List<Node> list) {
		List<Node> clone = new ArrayList<Node>();
		clone.addAll(list);
		return clone;
	}
	
	private List<Edge> cloneEdgeList(List<Edge> list) {
		List<Edge> clone = new ArrayList<Edge>();
		clone.addAll(list);
		return clone;
	}
	
	private void filter(List<Node> list) {
		for (Filter<Node> filter : nodeFilters) {
			filter.filter(list);
		}
	}
}
