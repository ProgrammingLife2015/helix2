package tudelft.ti2806.pl3.util.wrap;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.exeption.DuplicateGenomeNameException;
import tudelft.ti2806.pl3.util.HashableCollection;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An utility class to find and combine nodes which can be combined into
 * {@link SpaceWrapper}.
 * 
 * @author Sam Smulders
 */
public final class SpaceWrapUtil {
	private SpaceWrapUtil() {
	}
	
	@SuppressWarnings("CPD-START")
	/**
	 * Constructs a {@link WrappedGraphData} instance which contains the spatial
	 * collapsed graph of the given graph.
	 * 
	 * <p>
	 * See {@link SpaceWrapper} about the definition of spatial collapsing.
	 * 
	 * @param original
	 *            the original graph
	 * @return the collapsed version of the given graph <br>
	 *         {@code null} if nothing could be collapsed
	 */
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		List<NodeWrapper> newLayer = combineNodes(original.getPositionedNodes());
		if (newLayer == null) {
			return null;
		}
		return new WrappedGraphData(original, newLayer);
	}
	
	/**
	 * Combines all nodes into {@link SpaceWrapper}s where possible on the
	 * graph. Prioritising the smaller groups over larger groups.
	 * 
	 * <p>
	 * The result is stored into a new list which is a layer over the given list
	 * of nodes. The original list is untouched.
	 * 
	 * @param nodes
	 *            the nodes of the graph to combine.
	 * @return a new layer of nodes <br>
	 *         {@code null} if nothing could be collapsed
	 */
	static List<NodeWrapper> combineNodes(List<NodeWrapper> nodes) {
		List<NodeWrapper> nonWrappedNodes = new ArrayList<NodeWrapper>(nodes);
		List<CombineWrapper> combinedNodes = new ArrayList<CombineWrapper>();
		for (List<NodeWrapper> list : findCombineableNodes(nodes)) {
			CombineWrapper newNode = new SpaceWrapper(list);
			combinedNodes.add(newNode);
			nonWrappedNodes.removeAll(list);
		}
		if (combinedNodes.size() == 0) {
			return null;
		}
		return WrapUtil.wrapAndReconnect(nonWrappedNodes, combinedNodes);
	}
	
	@SuppressWarnings("CPD-END")
	/**
	 * Finds the groups of spatial combine able nodes, prioritising the smaller
	 * groups. If a bigger group contains a smaller group, it will be let out.
	 * 
	 * @param nodes
	 *            the nodes to search through
	 * @return a list of spatial combine able nodes.
	 */
	static List<List<NodeWrapper>> findCombineableNodes(List<NodeWrapper> nodes) {
		return filterCandidates(computeAllCandidates(nodes));
	}
	
	/**
	 * Filters the given candidates to see if they really form a closed group of
	 * nodes. Which means that all nodes between a candidate pair must start and
	 * end at the candidate pair at some point and the found group contains no
	 * subset of another found combine able group.
	 * 
	 * @param candidateList
	 *            the list of candidates to filter
	 * @return a list of all combine able groups found
	 */
	static List<List<NodeWrapper>> filterCandidates(
			List<Pair<Integer, Pair<NodeWrapper, NodeWrapper>>> candidateList) {
		/*
		 * The found group of nodes may not contain a smaller group of nodes, or
		 * else we would duplicate them.
		 */
		Set<NodeWrapper> blackList = new HashSet<NodeWrapper>();
		List<List<NodeWrapper>> foundSets = new ArrayList<List<NodeWrapper>>();
		for (Pair<Integer, Pair<NodeWrapper, NodeWrapper>> candidate : candidateList) {
			NodeWrapper startNode = candidate.getSecond().getFirst();
			NodeWrapper endNode = candidate.getSecond().getSecond();
			if (blackList.contains(startNode) || blackList.contains(endNode)) {
				continue;
			}
			
			Set<NodeWrapper> rightGroup = new HashSet<NodeWrapper>();
			Set<NodeWrapper> leftGroup = new HashSet<NodeWrapper>();
			/*
			 * Each path from the startNode to the right must lead to the
			 * endNode.
			 */
			if (!searchRight(startNode, rightGroup, candidate.getFirst(),
					endNode, blackList)
					/*
					 * Each path from the endNode to the left must lead to the
					 * startNode.
					 */
					|| !searchLeft(endNode, leftGroup, candidate.getFirst(),
							startNode, blackList)) {
				continue;
			}
			/*
			 * To fulfil the preconditions of CombineWrapper we sort the found
			 * list on previousNodeCount.
			 */
			rightGroup.add(startNode);
			rightGroup.add(endNode);
			List<NodeWrapper> foundList = new ArrayList<NodeWrapper>(rightGroup);
			Collections.sort(foundList);
			blackList.addAll(foundList);
			foundSets.add(foundList);
		}
		return foundSets;
	}
	
	/**
	 * Computes all possible candidates for spatial node combination.
	 * 
	 * @param nodes
	 *            the nodes to search trough
	 * @return a sorted list of all possible combine candidates, sorted on
	 *         distance between the candidate nodes
	 */
	static List<Pair<Integer, Pair<NodeWrapper, NodeWrapper>>> computeAllCandidates(
			List<NodeWrapper> nodes) {
		List<Pair<Integer, Pair<NodeWrapper, NodeWrapper>>> candidateList = new ArrayList<Pair<Integer, Pair<NodeWrapper, NodeWrapper>>>();
		/*
		 * If a node doesn't contain the same genomes, it is impossible for them
		 * to be a candidate, because every node in the group between a
		 * candidate pair should start and end at some point on the candidate
		 * pair its nodes. A missing genome means there is an other path in or
		 * out the group. Thats why we only use nodes with the same set of
		 * genomes to create candidates.
		 */
		for (Pair<Set<Genome>, List<NodeWrapper>> bucket : getNodesByGenome(nodes)) {
			// There should be at least two nodes with the same genome list.
			if (bucket.getSecond().size() <= 1) {
				continue;
			}
			List<NodeWrapper> nodeList = new ArrayList<NodeWrapper>(
					bucket.getSecond());
			for (int i = bucket.getSecond().size() - 1; i > 0; i--) {
				nodeList.remove(nodeList.size() - 1);
				candidateList.add(newCandidatePair(bucket.getSecond().get(i),
						nodeList.get(nodeList.size() - 1)));
			}
		}
		sortOnLenght(candidateList);
		return candidateList;
	}
	
	/**
	 * Put all nodes with the same genome group in the same bucket.
	 * 
	 * @param nodes
	 *            the nodes to map
	 * @return a collection of buckets
	 */
	static Collection<Pair<Set<Genome>, List<NodeWrapper>>> getNodesByGenome(
			List<NodeWrapper> nodes) {
		Map<HashableCollection<Genome>, Pair<Set<Genome>, List<NodeWrapper>>> searchMap = new HashMap<HashableCollection<Genome>, Pair<Set<Genome>, List<NodeWrapper>>>();
		for (NodeWrapper node : nodes) {
			Set<Genome> genome = node.getGenome();
			/*
			 * There should be at least two genomes on a node to be a end or
			 * start node.
			 */
			if (genome.size() <= 1) {
				continue;
			}
			Pair<Set<Genome>, List<NodeWrapper>> pair = searchMap
					.get(new HashableCollection<Genome>(genome));
			if (pair == null) {
				pair = new Pair<Set<Genome>, List<NodeWrapper>>(genome,
						new ArrayList<NodeWrapper>());
				searchMap.put(new HashableCollection<Genome>(genome), pair);
			}
			pair.getSecond().add(node);
		}
		return searchMap.values();
	}
	
	/**
	 * Recursive method which checks if<br>
	 * - the path to the left ends with the end node<br>
	 * - the current node isn't on the blacklist<br>
	 * and it keeps track of a set with all visited nodes and only visits nodes
	 * which have not been visited yet.
	 * 
	 * @param to
	 *            the node to work from
	 * @param track
	 *            a set of all visited nodes
	 * @param steps
	 *            the number of steps left
	 * @param startNode
	 *            the end node to find
	 * @param blackList
	 *            the blacklist of nodes which should not be on any path
	 * @return {@code true} if each path from the endNode to the left leads to
	 *         the startNode.<br>
	 *         {@code false} otherwise
	 */
	static boolean searchLeft(NodeWrapper to, Set<NodeWrapper> track,
			Integer steps, NodeWrapper startNode, Set<NodeWrapper> blackList) {
		if (to == startNode) {
			return true;
		}
		if (steps == 0 || to.getIncoming().size() == 0
				|| blackList.contains(to)) {
			return false;
		}
		for (NodeWrapper from : to.getIncoming()) {
			if (!track.contains(from)) {
				track.add(from);
				if (!searchLeft(from, track, steps - 1, startNode, blackList)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Recursive method which checks if<br>
	 * <ul>
	 * <li>the path to the right ends with the end node<br>
	 * <li>the current node isn't on the blacklist
	 * </ul>
	 * This method also keeps track of a set with all visited nodes and only
	 * visits nodes which have not been visited yet.
	 * 
	 * @param to
	 *            the node to work from
	 * @param track
	 *            a set of all visited nodes
	 * @param steps
	 *            the number of steps left
	 * @param endNode
	 *            the end node to find
	 * @param blackList
	 *            the blacklist of nodes which should not be on any path
	 * @return {@code true} if each path from the startNode to the right leads
	 *         to the endNode.<br>
	 *         {@code false} otherwise
	 */
	static boolean searchRight(NodeWrapper from, Set<NodeWrapper> track,
			Integer steps, NodeWrapper endNode, Set<NodeWrapper> blackList) {
		if (from == endNode) {
			return true;
		}
		if (steps == 0 || blackList.contains(from)
				|| from.getOutgoing().size() == 0) {
			return false;
		}
		for (NodeWrapper to : from.getOutgoing()) {
			if (!track.contains(to)) {
				track.add(to);
				if (!searchRight(to, track, steps - 1, endNode, blackList)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Creates a new candidate pair from the given two nodes.<br>
	 * The first value in the pair is the maximum distance between the nodes in
	 * edges.<br>
	 * The second value in the pair is an other pair with the node most to the
	 * left as the first value and the node most to the right as the second
	 * value.
	 * 
	 * @param node1
	 *            a candidate node
	 * @param node2
	 *            a candidate node
	 * @return an ordered candidate pair
	 * @throws DuplicateGenomeNameException
	 *             if the distance between two nodes with the same genome is
	 *             zero.
	 */
	static Pair<Integer, Pair<NodeWrapper, NodeWrapper>> newCandidatePair(
			NodeWrapper node1, NodeWrapper node2) {
		int distance = node1.getPreviousNodesCount()
				- node2.getPreviousNodesCount();
		if (distance > 0) {
			return new Pair<Integer, Pair<NodeWrapper, NodeWrapper>>(distance,
					new Pair<NodeWrapper, NodeWrapper>(node2, node1));
		} else if (distance < 0) {
			return new Pair<Integer, Pair<NodeWrapper, NodeWrapper>>(-distance,
					new Pair<NodeWrapper, NodeWrapper>(node1, node2));
		}
		// TODO: Handle the exceptions correctly
		try {
			throw new DuplicateGenomeNameException(
					"The graph consists of two genome with the same name.",
					"Two posible routes are found with the same genome name, "
							+ "or the order of the nodes was not correctly calculated.");
		} catch (DuplicateGenomeNameException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Sorts a candidate list on the distance between the nodes, which is given
	 * by the first value of each pair.
	 * 
	 * @param candidateList
	 *            the list to sort
	 */
	static void sortOnLenght(
			List<Pair<Integer, Pair<NodeWrapper, NodeWrapper>>> candidateList) {
		Collections.sort(candidateList, new LenghtPairSort());
	}
	
	/**
	 * A comparator implementation to sort a candidate list on the distance
	 * between the nodes, which is given by the first value of each pair.
	 */
	static class LenghtPairSort implements
			Comparator<Pair<Integer, Pair<NodeWrapper, NodeWrapper>>> {
		@Override
		public int compare(Pair<Integer, Pair<NodeWrapper, NodeWrapper>> o1,
				Pair<Integer, Pair<NodeWrapper, NodeWrapper>> o2) {
			return o1.getFirst() - o2.getFirst();
		}
	}
}
