package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.interest.CalculateSizeInterest;
import tudelft.ti2806.pl3.data.wrapper.operation.yposition.PositionNodeYOnGenomeSpace;
import tudelft.ti2806.pl3.data.wrapper.util.WrapUtil;
import tudelft.ti2806.pl3.util.HashableCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

//import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.CalculateAddMaxOfWrapped;
//import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.CalculateWrapPressureInterest;

/**
 * This model filters the original graph data, based on the filter selections.
 *
 * <p>Every time a new filter is added:
 * <li>It first makes a clone of the original graph data.
 * <li>Then it passes this clone to the filters and retrieves the filtered data.
 * <li>Then it wrappes the graph to one node.
 * <li>Then it calculates the y positions of the nodes.
 * <li>Then it calculates the interest of the nodes.
 * <li>Then it notifies the {@link tudelft.ti2806.pl3.visualization.ZoomedGraphModel}, which
 * will produce the data for the view.
 * Created by Boris Mattijssen on 20-05-15.
 */
public class FilteredGraphModel extends Observable {

//	private final int pressureMultiplier = 10;

	protected AbstractGraphData originalGraphData;
	private Wrapper collapsedNode;
	private Collection<Filter<DataNode>> filters;
	private PositionNodeYOnGenomeSpace positionNodeYOnGenomeSpace;
//	private CalculateWrapPressureInterest pressureInterest;
//	private CalculateAddMaxOfWrapped addMaxOfWrapped;
	private CalculateSizeInterest sizeInterest;
	//private CalculateGroupInterest groupInterest;

	/**
	 * Construct the model containing the filtered data.<br>
	 * The model gets the original graph data and filters this data.
	 * Then it informs its listeners, to give them the filtered data.
	 *
	 * @param originalGraphData
	 *          The original graph data
	 */
	public FilteredGraphModel(AbstractGraphData originalGraphData) {
		this.originalGraphData = originalGraphData;
		filters = new ArrayList<>();
		positionNodeYOnGenomeSpace = new PositionNodeYOnGenomeSpace();
//		pressureInterest = new CalculateWrapPressureInterest(pressureMultiplier);
//		addMaxOfWrapped = new CalculateAddMaxOfWrapped();
		sizeInterest = new CalculateSizeInterest();
	}

	public void setFilters(Collection<Filter<DataNode>> filters) {
		this.filters = filters;
	}

	public Wrapper getCollapsedNode() {
		return collapsedNode;
	}

	/**
	 * Filters a copy of the {@link tudelft.ti2806.pl3.data.graph.GraphDataRepository} and combines all nodes
	 * which can be combined without losing data and removes all dead edges. The
	 * result is saved as {@code originalWrappedGraphData}.
	 */
	public void produceWrappedGraphData() {
		List<DataNode> resultNodes = originalGraphData.getNodeListClone();
		filter(resultNodes);
		List<Edge> resultEdges = originalGraphData.getEdgeListClone();
		removeAllDeadEdges(resultEdges, resultNodes);
		WrappedGraphData wrappedGraphData = new WrappedGraphData(resultNodes, resultEdges);
		removeAllEmptyEdges(wrappedGraphData);
		collapsedNode = WrapUtil.collapseGraph(wrappedGraphData).getPositionedNodes().get(0);
		// calculate y-pos
		positionNodeYOnGenomeSpace.calculate(collapsedNode, null);
		// calculate interest
//		pressureInterest.calculate(collapsedNode, null);
//		addMaxOfWrapped.calculate(collapsedNode, null);
		sizeInterest.calculate(collapsedNode, null);
		setChanged();
		notifyObservers();
	}

	public static void removeAllEmptyEdges(WrappedGraphData wrappedGraphData) {
		for (Wrapper wrapper : wrappedGraphData.getPositionedNodes()) {
			boolean allEqual = true;
			if (wrapper.getOutgoing().size() <= 1) {
				allEqual = false;
			}

			Map<Integer, Wrapper> nodeCountMapping = new HashMap<>(wrapper.getOutgoing().size());
			for (Wrapper outgoing : wrapper.getOutgoing()) {
				nodeCountMapping.put(outgoing.getPreviousNodesCount(), outgoing);
				if (!new HashableCollection<>(wrapper.getGenome())
						.equals(new HashableCollection<>(outgoing.getGenome()))) {
					allEqual = false;
					break;
				}
			}
			if(allEqual == true) {
				List<Integer> list = new ArrayList<>(nodeCountMapping.keySet());
				Collections.sort(list);
				List<Wrapper> removeList = new ArrayList<>();
				for (Wrapper outgoing : wrapper.getOutgoing()) {
					if(outgoing != nodeCountMapping.get(list.get(0))) {
						removeList.add(outgoing);
						outgoing.getIncoming().remove(wrapper);
					}
				}
				wrapper.getOutgoing().removeAll(removeList);
			}
		}
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
	 */
	protected void filter(List<DataNode> list) {
		for (Filter<DataNode> filter : filters) {
			filter.filter(list);
		}
	}
}
