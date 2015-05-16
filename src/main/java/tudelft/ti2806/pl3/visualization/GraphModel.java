package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.util.wrap.HorizontalWrapUtil;
import tudelft.ti2806.pl3.util.wrap.VerticalWrapUtil;
import tudelft.ti2806.pl3.util.wrap.WrapUtil;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphModel reads GraphData and makes a new graph of it.
 */
public class GraphModel implements GraphModelInterface {
	protected AbstractGraphData originalGraph;
	private WrappedGraphData graph;
	
	// protected AbstractGraphData graph;
	
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
	public void produceGraph(List<Filter<DataNodeInterface>> filters) {
		List<DataNodeInterface> resultNodes = originalGraph.getNodeListClone();
		filter(resultNodes, filters);
		List<Edge> resultEdges = originalGraph.getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		graph = new WrappedGraphData(originalGraph, resultNodes, resultEdges);
		graph = WrapUtil.collapseGraph(graph, 10);
	}
	
	@Override
	public WrappedGraphData getGraphData() {
		return graph;
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
	static void removeAllDeadEdges(List<Edge> edgeList,
			List<DataNodeInterface> nodeList) {
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
	static List<Edge> getAllDeadEdges(List<Edge> edgeList,
			List<DataNodeInterface> nodeList) {
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
	protected void filter(List<DataNodeInterface> list,
			List<Filter<DataNodeInterface>> filters) {
		for (Filter<DataNodeInterface> filter : filters) {
			filter.filter(list);
		}
	}
	
}
