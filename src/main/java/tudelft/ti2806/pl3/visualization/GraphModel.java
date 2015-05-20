package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.util.wrap.WrapUtil;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphModel reads GraphData and makes a new graph of it.
 */
public class GraphModel {
	protected AbstractGraphData originalGraphData;
	private WrappedGraphData originalWrappedGraphData;

	public GraphModel(AbstractGraphData graphData) {
		this.originalGraphData = graphData;
	}
	
	/**
	 * Filters a copy of the {@link GraphDataRepository} and combines all nodes
	 * which can be combined without losing data and removes all dead edges. The
	 * result is saved as {@code originalWrappedGraphData}.
	 * 
	 * @param filters
	 *            the filters to be applied.
	 */
	public void produceGraph(List<Filter<DataNode>> filters) {
		List<DataNode> resultNodes = originalGraphData.getNodeListClone();
		filter(resultNodes, filters);
		List<Edge> resultEdges = originalGraphData.getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		originalWrappedGraphData = new WrappedGraphData(originalGraphData, resultNodes, resultEdges);
		originalWrappedGraphData = WrapUtil.collapseGraph(originalWrappedGraphData, 10);
	}
	
	/**
	 * Removes all edges of which one or both of their nodes is not on the
	 * originalWrappedGraphData.
	 * 
	 * @param edgeList
	 *            the list of edges in the originalWrappedGraphData
	 * @param nodeList
	 *            the list of nodes in the originalWrappedGraphData
	 */
	static void removeAllDeadEdges(List<Edge> edgeList,
			List<DataNode> nodeList) {
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
			List<DataNode> nodeList) {
		List<Edge> removeList = new ArrayList<>();
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
	protected void filter(List<DataNode> list,
			List<Filter<DataNode>> filters) {
		for (Filter<DataNode> filter : filters) {
			filter.filter(list);
		}
	}
	
}
