package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An utility class to find and/or remove dead edges from a {@link GraphData}
 * object.
 * 
 * @author Sam Smulders
 */
public class EdgeUtil {
	private EdgeUtil() {
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
	 * graph.
	 * 
	 * @param edgeList
	 *            the list of edges in the graph
	 * @param nodeList
	 *            the list of nodes in the graph
	 */
	public static void removeAllDeadEdges(List<Edge> edgeList,
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
	public static List<Edge> getAllDeadEdges(List<Edge> edgeList,
			List<DataNode> nodeList) {
		List<Edge> removeList = new ArrayList<Edge>();
		for (Edge edge : edgeList) {
			if (!nodeList.contains(edge.getFrom())
					|| !nodeList.contains(edge.getTo())) {
				removeList.add(edge);
			}
		}
		return removeList;
	}
}
