package tudelft.ti2806.pl3.visualization.wrapper.operation.order;

import tudelft.ti2806.pl3.math.Line;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Searches for the order with the least number of edge crossings, to increase
 * the readability of the graph.
 * 
 * <p>
 * The search is exhaustive, so the {@link ExhaustiveLeastCrossingsSequencer}
 * searches for a better order until all orders are explored or if an order with
 * zero crossings is found.
 * 
 * <p>
 * The {@link ExhaustiveLeastCrossingsSequencer} doesn't change the order of
 * other nodes then {@link SpaceWrapper}, because other nodes will never have
 * any crossings. For other {@link NodeWrapper} types the default methods of the
 * interface are used.
 * 
 * @author Sam Smulders
 */
public class ExhaustiveLeastCrossingsSequencer implements WrapperSequencer {
	private final int maxIterations;
	
	public ExhaustiveLeastCrossingsSequencer(int maxIterations) {
		this.maxIterations = maxIterations;
	}
	
	/**
	 * Calculate the order for {@link SpaceWrapper}.
	 * 
	 * <p>
	 * Change the order of incoming or outgoing connections for each node within
	 * the {@link SpaceWrapper} its list.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	public void calculate(SpaceWrapper wrapper) {
		// true is left to right
		// false is right to left
		// TODO: Incoming option is not implemented yet.
		Pair<Boolean, Long> direction = new Pair<Boolean, Long>(true,
				getOptionCountFromLeftToRight(wrapper.getNodeList()));
		// = getBestDirection(wrapper.getNodeList());
		if (direction.getSecond() < maxIterations) {
			// TODO: leave default order or use genetic algorithm?
			return;
		}
		List<List<NodeWrapper>> currentOrder;
		// TODO: Incoming option is not implemented yet.
		// if (direction.getFirst()) {
		// currentOrder = getOutgoingList(wrapper.getNodeList());
		// } else {
		currentOrder = getIncomingList(wrapper.getNodeList());
		// }
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = getOrder(currentOrder);
		int best = calculateBestConfig(wrapper, direction, order);
		applyOrderConfiguration(best, order);
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
	
	private int calculateBestConfig(SpaceWrapper wrapper,
			Pair<Boolean, Long> direction,
			List<Pair<List<NodeWrapper>, NodeWrapper[]>> order) {
		int best = Integer.MAX_VALUE;
		int bestConfig = 0;
		for (int i = 0; i < direction.getSecond(); i++) {
			applyConfiguration(i, order, wrapper);
			int found = countIntersections(wrapper);
			if (found < best) {
				// There is no better configuration to search for
				if (best == 0) {
					return i;
				}
				best = found;
				bestConfig = i;
			}
		}
		return bestConfig;
	}
	
	/**
	 * TODO
	 * 
	 * <p>
	 * Direction independent method.
	 * 
	 * @param currentOrder
	 * @return
	 */
	static List<Pair<List<NodeWrapper>, NodeWrapper[]>> getOrder(
			List<List<NodeWrapper>> currentOrder) {
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = new ArrayList<Pair<List<NodeWrapper>, NodeWrapper[]>>();
		for (List<NodeWrapper> list : currentOrder) {
			NodeWrapper[] connections = new NodeWrapper[list.size()];
			for (int i = list.size() - 1; i >= 0; i--) {
				connections[i] = list.get(i);
			}
			order.add(new Pair<List<NodeWrapper>, NodeWrapper[]>(list,
					connections));
		}
		return order;
	}
	
	int countIntersections(SpaceWrapper wrapper) {
		return countIntersections(getIncomingLines(wrapper.getNodeList()));
	}
	
	static boolean applyConfiguration(int configuration,
			List<Pair<List<NodeWrapper>, NodeWrapper[]>> order,
			SpaceWrapper wrapper) {
		applyOrderConfiguration(configuration, order);
		return applyOrder(wrapper);
	}
	
	/**
	 * TODO
	 * 
	 * @param wrapper
	 * @return
	 * @throws IndexOutOfBoundsException
	 *             if the {@link SpaceWrapper} is not constructed following its
	 *             preconditions.
	 */
	static boolean applyOrder(SpaceWrapper wrapper) {
		List<NodeWrapper> list = collapseIntoList(wrapper);
		if (list == null) {
			return false;
		}
		Set<NodeWrapper> remainingNodes = new HashSet<NodeWrapper>(
				wrapper.getNodeList());
		remainingNodes.removeAll(list);
		
		NodeWrapper firstNode = wrapper.getNodeList().get(0);
		firstNode.setY(0f);
		remainingNodes.remove(firstNode);
		
		int remainingNodesSize = remainingNodes.size();
		List<NodeWrapper> passedNodes = new ArrayList<NodeWrapper>(
				remainingNodesSize);
		for (int i = list.size() - 1; i >= 0; i--) {
			list.get(i).setY(i);
			for (NodeWrapper node : list.get(i).getOutgoing()) {
				if (remainingNodes.contains(node)) {
					node.setY(i);
					remainingNodes.remove(node);
					passedNodes.add(node);
				}
			}
		}
		/*
		 * Because all nodes in the SpaceWrapper are connected at some point in
		 * the chosen direction with one of the nodes out of the collapsed list,
		 * we can assume that we can iterate until all remaining nodes are in
		 * the list.
		 */
		for (int i = 0; i < remainingNodesSize; i++) {
			for (NodeWrapper node : passedNodes.get(i).getOutgoing()) {
				if (remainingNodes.contains(node)) {
					node.setY(passedNodes.get(i).getY());
					remainingNodes.remove(node);
					passedNodes.add(node);
				}
			}
		}
		return true;
	}
	
	static List<NodeWrapper> collapseIntoList(SpaceWrapper wrapper) {
		List<List<NodeWrapper>> listsToCombine = new ArrayList<List<NodeWrapper>>();
		for (int n = wrapper.getNodeList().size() - 2; n >= 0; n--) {
			NodeWrapper node = wrapper.getNodeList().get(n);
			if (node.getOutgoing().size() > 1) {
				listsToCombine.add(new ArrayList<NodeWrapper>(node
						.getOutgoing()));
			}
		}
		return mergeOrderedLists(listsToCombine);
	}
	
	/**
	 * Merges all given lists together without violating any of the given lists
	 * their element order.
	 * 
	 * <p>
	 * Warning, all elements and lists are removed from their lists after
	 * executing this method, or some of them when the method returns
	 * {@code null}.
	 * 
	 * <p>
	 * Method cost O(nm)
	 * <ul>
	 * <li>n being equal to the number of unique elements.
	 * <li>m being equal to the number of elements left in all lists together.
	 * </ul>
	 * 
	 * @param listsToMergePar
	 *            the list of lists to merge. Each list should at least contain
	 *            one element
	 * @return a list containing all elements of the given lists without
	 *         violating the order of any of the given lists<br>
	 *         {@code null} if the lists could not be merged without violating
	 *         one of the lists its orders
	 */
	static List<NodeWrapper> mergeOrderedLists(
			List<List<NodeWrapper>> listsToMerge) {
		List<NodeWrapper> lastElements = new ArrayList<NodeWrapper>(
				listsToMerge.size());
		for (List<NodeWrapper> list : listsToMerge) {
			lastElements.add(list.remove(list.size() - 1));
		}
		List<NodeWrapper> result = new ArrayList<NodeWrapper>();
		int lastResultSize = -1;
		while (listsToMerge.size() > 0) {
			/*
			 * If the result size doesn't grow, there is a conflict.
			 */
			if (lastResultSize == result.size()) {
				return null;
			}
			lastResultSize = result.size();
			for (int i = listsToMerge.size() - 1; i >= 0; i--) {
				if (!listContainsElement(listsToMerge, lastElements.get(i))) {
					int size = listsToMerge.get(i).size();
					if (size == 0) {
						NodeWrapper element = lastElements.remove(i);
						if (!result.contains(element)) {
							result.add(element);
						}
						listsToMerge.remove(i);
					} else {
						/*
						 * Adds the last element to the result list. This
						 * element is replaced on the lastElements list by the
						 * new last element on the list from the listsToCombine
						 * bound to this index. This last element again removed
						 * from that list.
						 */
						NodeWrapper element = lastElements.set(i, listsToMerge
								.get(i).remove(size - 1));
						if (!result.contains(element)) {
							result.add(element);
						}
					}
				}
			}
		}
		Collections.reverse(result);
		return result;
	}
	
	/**
	 * Checks if the given {@code element} is in one of the {@code lists}.
	 * 
	 * @param lists
	 *            the lists to search trough
	 * @param element
	 *            the element to search for
	 * @return {@code true} if the given {@code element} is in one of the
	 *         {@code lists}<br>
	 *         {@code false} otherwise
	 */
	static boolean listContainsElement(List<List<NodeWrapper>> lists,
			NodeWrapper element) {
		for (List<NodeWrapper> list : lists) {
			if (list.contains(element)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Constructs a map with for each node in the NodeWrapper which requires to
	 * be ordered a key. Each key lead to a {@link Pair} with as first value a
	 * set of all nodes which should come before this node and as second a set
	 * of all nodes which should come after this node.
	 * 
	 * @param wrapper
	 *            the wrapper
	 * @return a constructed map holding all conditions for each key.
	 */
	// Map<NodeWrapper, Pair<Set<NodeWrapper>, Set<NodeWrapper>>>
	// constructConditionMap(
	// SpaceWrapper wrapper) {
	// Map<NodeWrapper, Pair<Set<NodeWrapper>, Set<NodeWrapper>>> map = new
	// HashMap<NodeWrapper, Pair<Set<NodeWrapper>, Set<NodeWrapper>>>();
	// List<NodeWrapper> nodeList = wrapper.getNodeList();
	// for (int n = nodeList.size() - 2; n >= 0; n--) {
	// NodeWrapper node = nodeList.get(n);
	// if (node.getOutgoing().size() > 1) {
	// int listSize = node.getOutgoing().size() - 1;
	// for (int i = 0; i < listSize; i++) {
	// NodeWrapper out = node.getOutgoing().get(i);
	// Pair<Set<NodeWrapper>, Set<NodeWrapper>> goal = map
	// .get(out);
	// goal.getFirst()
	// .addAll(node.getOutgoing().subList(0, i - 1));
	// goal.getSecond().addAll(
	// node.getOutgoing().subList(i + 1, listSize));
	// }
	// }
	// }
	// return map;
	// }
	
	static void applyOrderConfiguration(int orderConfiguration,
			List<Pair<List<NodeWrapper>, NodeWrapper[]>> order) {
		int orderConfig = orderConfiguration;
		for (Pair<List<NodeWrapper>, NodeWrapper[]> pair : order) {
			
			// The number of possible configurations for this target
			int factorialLength = factorial(pair.getSecond().length);
			/*
			 * The local configuration for this target is equal to the
			 * orderConfiguration modulus the number of possible configurations
			 * on this position.
			 */
			applyOrderConfiguration(orderConfig % factorialLength,
					pair.getFirst(), pair.getSecond());
			orderConfig /= factorialLength;
		}
	}
	
	static void applyOrderConfiguration(int localConfiguration,
			List<NodeWrapper> currentOrder, NodeWrapper[] dictionary) {
		List<NodeWrapper> newOrder = new ArrayList<NodeWrapper>(
				dictionary.length);
		newOrder.add(dictionary[0]);
		for (int i = 1; i < dictionary.length; i++) {
			newOrder.add(localConfiguration % (i + 1), dictionary[i]);
			localConfiguration /= (i + 1);
		}
		currentOrder.clear();
		currentOrder.addAll(newOrder);
	}
	
	static List<List<NodeWrapper>> getIncomingList(List<NodeWrapper> nodes) {
		List<List<NodeWrapper>> list = new ArrayList<List<NodeWrapper>>();
		for (int i = nodes.size() - 1; i >= 0; i--) {
			list.add(nodes.get(i).getIncoming());
		}
		return list;
	}
	
	static List<List<NodeWrapper>> getOutgoingList(List<NodeWrapper> nodes) {
		List<List<NodeWrapper>> list = new ArrayList<List<NodeWrapper>>();
		for (int i = nodes.size() - 2; i >= 0; i--) {
			list.add(nodes.get(i).getOutgoing());
		}
		return list;
	}
	
	List<Line> getIncomingLines(List<NodeWrapper> nodes) {
		List<Line> lines = new ArrayList<Line>();
		for (int i = nodes.size() - 2; i >= 2; i--) {
			NodeWrapper to = nodes.get(i);
			for (NodeWrapper from : to.getIncoming()) {
				lines.add(new Line(from.getPreviousNodesCount(), from.getY(),
						to.getPreviousNodesCount(), to.getY()));
			}
		}
		return lines;
	}
	
	int countIntersections(List<Line> lines) {
		int count = 0;
		for (int x = lines.size() - 1; x >= 1; x--) {
			for (int y = x - 1; y >= 0; y--) {
				if (lines.get(x).intersect(lines.get(y))) {
					count++;
				}
			}
		}
		return count;
	}
	
	static long getOptionCountFromLeftToRight(List<NodeWrapper> nodeList) {
		long out = 1;
		for (int i = nodeList.size() - 2; i >= 0; i--) {
			out *= factorial(nodeList.get(i).getOutgoing().size());
		}
		return out;
	}
	
	static long getOptionCountFromRightToLeft(List<NodeWrapper> nodeList) {
		long in = 1;
		for (int i = nodeList.size() - 1; i >= 1; i--) {
			in *= factorial(nodeList.get(i).getIncoming().size());
		}
		return in;
	}
	
	private static int factorial(int n) {
		if (n == 0) {
			return 1;
		}
		if (n > 12) {
			return Integer.MAX_VALUE;
		}
		return n * factorial(n - 1);
	}
	
	/**
	 * Calculates the direction with the least number of options.
	 * 
	 * @return {@code true} if the best direction is from left to right<br>
	 *         {@code false} if the best direction is from right to left
	 */
	static Pair<Boolean, Long> getBestDirection(List<NodeWrapper> nodeList) {
		long leftToRight = getOptionCountFromLeftToRight(nodeList);
		long rightToLeft = getOptionCountFromRightToLeft(nodeList);
		if (leftToRight <= rightToLeft) {
			return new Pair<Boolean, Long>(true, leftToRight);
		} else {
			return new Pair<Boolean, Long>(false, rightToLeft);
		}
	}
}
