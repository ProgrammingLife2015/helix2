package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An utility class to find and/or remove dead edges from a {@link GraphData}
 * object.
 *
 * @author Sam Smulders
 * @author Boris Mattijssen
 */
public class EdgeUtil {
	private EdgeUtil() {
	}

	/**
	 * This method removes all "empty" edges from the graph. Empty edges are
	 * edges that are left over after filtering, but create a path that is
	 * actually to short. A longer path exists that should be the only path. For
	 * an example of such a dead edge, see the data/testdata/emptyEdges folder.
	 *
	 * @param wrappedGraphData
	 * 		The wrapped data containing all {@link Wrapper}s in the graph
	 */
	public static void removeAllEmptyEdges(WrappedGraphData wrappedGraphData) {
		for (Wrapper wrapper : wrappedGraphData.getPositionedNodes()) {
			if (wrapper.getOutgoing().size() <= 1) {
				continue;
			}
			Set<Genome> genomes = new HashSet<>(wrapper.getGenome());
			List<Wrapper> outgoingList = new ArrayList<>(wrapper.getOutgoing());
			Collections.sort(outgoingList);
			List<Wrapper> removeList = new ArrayList<>();
			for (Wrapper outgoing : outgoingList) {
				if (Collections.disjoint(genomes, outgoing.getGenome())) {
					removeList.add(outgoing);
				} else {
					genomes.removeAll(outgoing.getGenome());
				}
			}
			for (Wrapper remove : removeList) {
				remove.getIncoming().remove(wrapper);
			}
			wrapper.getOutgoing().removeAll(removeList);
		}
	}

	/**
	 * Removes all edges of which one or both of their nodes is not on the
	 * graph.
	 *
	 * @param edgeList
	 * 		the list of edges in the graph
	 * @param nodeList
	 * 		the list of nodes in the graph
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
	 * 		the list of edges in the graph
	 * @param nodeList
	 * 		the list of nodes in the graph
	 * @return a list of all dead edges
	 */
	public static List<Edge> getAllDeadEdges(List<Edge> edgeList,
			List<DataNode> nodeList) {
		List<Edge> removeList = new ArrayList<>();
		Map<Integer, DataNode> nodeMap = new HashMap<>(nodeList.size());
		for (DataNode node : nodeList) {
			nodeMap.put(node.getId(), node);
		}
		for (Edge edge : edgeList) {
			if (!nodeMap.containsKey(edge.getFrom().getId())
					|| !nodeMap.containsKey(edge.getTo().getId())) {
				removeList.add(edge);
			}
		}
		return removeList;
	}

	/**
	 * Sets weight based on the number of genomes that flow through the edges.
	 * <p/>
	 * It will calculate how many genomes flow to its outgoing node and it will
	 * store this weight on the node itself.
	 *
	 * @param wrapperClones
	 * 		The list of all wrappers that are currently on the screen
	 */
	public static void setEdgeWeight(List<WrapperClone> wrapperClones) {
		for (WrapperClone wrapperClone : wrapperClones) {
			if (wrapperClone.getOutgoing().size() == 0) {
				continue;
			}
			if (wrapperClone.getOutgoing().size() == 1) {
				wrapperClone.getOutgoingWeight().add(wrapperClone.getGenome().size());
			}
			Set<Genome> genomes = new HashSet<>(wrapperClone.getGenome());
			Collections.sort(wrapperClone.getOutgoing());
			for (Wrapper outgoing : wrapperClone.getOutgoing()) {
				Collection<Genome> intersection = intersection(outgoing.getGenome(), genomes);
				wrapperClone.getOutgoingWeight().add(intersection.size());
				genomes.removeAll(intersection);
			}
		}
	}

	/**
	 * Calculates the intersection between two collections.
	 *
	 * @param collection1
	 * 		collection 1
	 * @param collection2
	 * 		collection 2
	 * @param <T>
	 * 		type of both collections
	 * @return the intersection between the two collections
	 */
	public static <T> Collection<T> intersection(Collection<T> collection1, Collection<T> collection2) {
		Collection<T> list = new ArrayList<T>();

		for (T t : collection1) {
			if (collection2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}
}
