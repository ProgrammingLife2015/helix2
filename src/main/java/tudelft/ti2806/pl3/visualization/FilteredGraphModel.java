package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.util.wrap.WrapUtil;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

/**
 * Created by Boris Mattijssen on 20-05-15.
 */
public class FilteredGraphModel extends Observable {

	protected AbstractGraphData originalGraphData;
	private WrappedGraphData wrappedGraphData;
	private Collection<Filter<DataNode>> filters;

	public FilteredGraphModel(AbstractGraphData originalGraphData) {
		this.originalGraphData = originalGraphData;
	}

	public void setFilters(Collection<Filter<DataNode>> filters) {
		this.filters = filters;
	}

	public WrappedGraphData getWrappedGraphData() {
		return wrappedGraphData;
	}

	/**
	 * Filters a copy of the {@link tudelft.ti2806.pl3.data.graph.GraphDataRepository} and combines all nodes
	 * which can be combined without losing data and removes all dead edges. The
	 * result is saved as {@code originalWrappedGraphData}.
	 */
	public void produceWrappedGraphData() {
		List<DataNode> resultNodes = originalGraphData.getNodeListClone();
		filter(resultNodes, filters);
		List<Edge> resultEdges = originalGraphData.getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		wrappedGraphData = new WrappedGraphData(originalGraphData, resultNodes, resultEdges);
		wrappedGraphData = WrapUtil.collapseGraph(wrappedGraphData, 10);
		// TODO: hier nog de y-posities bepalen
		notifyObservers();
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
	static void removeAllDeadEdges(List<Edge> edgeList, List<DataNode> nodeList) {
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
	protected void filter(List<DataNode> list, Collection<Filter<DataNode>> filters) {
		for (Filter<DataNode> filter : filters) {
			filter.filter(list);
		}
	}
}
