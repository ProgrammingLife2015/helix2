package tudelft.ti2806.pl3.visualization.wrapper.operation.order;

import tudelft.ti2806.pl3.math.Line;
import tudelft.ti2806.pl3.math.MathUtil;
import tudelft.ti2806.pl3.util.OrderedListUtil;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
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
public class ExhaustiveLeastCrossingsSequencer extends WrapperOperation {
	// TODO: Temp. solution. Is a problem with a node count of (1/AFJUSTMENT).
	private static final float ADJUSTMENT = 0.01f;
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
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
		long posibleConfigurations = getOptionCountFromLeftToRight(wrapper
				.getNodeList());
		if (posibleConfigurations > maxIterations) {
			// TODO: leave default order or use genetic algorithm?
			return;
		}
		List<List<NodeWrapper>> currentOrder = getOutgoingLists(wrapper
				.getNodeList());
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = getOrder(currentOrder);
		int bestConfig = calculateBestConfig(wrapper,
				(int) posibleConfigurations, order);
		applyConfigurationToOrder(bestConfig, order);
	}
	
	/**
	 * Get a list of all outgoing lists of all nodes in the given list.
	 * 
	 * @param nodes
	 *            the nodes to obtain the outgoing lists from
	 * @return a list with a the outgoing node list of every node
	 */
	List<List<NodeWrapper>> getOutgoingLists(List<NodeWrapper> nodes) {
		List<List<NodeWrapper>> list = new ArrayList<List<NodeWrapper>>();
		for (int i = nodes.size() - 2; i >= 0; i--) {
			list.add(nodes.get(i).getOutgoing());
		}
		return list;
	}
	
	/**
	 * Get a list of pairs with as first value the list of the node and as
	 * second a array copy of this list to store the original order of this
	 * list.
	 * 
	 * @param currentOrder
	 *            the list of lists of which to store the original.
	 * @return a list with as first value the list of the node and as second the
	 *         orignal order of this list
	 */
	List<Pair<List<NodeWrapper>, NodeWrapper[]>> getOrder(
			List<List<NodeWrapper>> currentOrder) {
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = new ArrayList<>();
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
	
	/**
	 * Applies the y-order to the node its position on the y-axis.
	 * 
	 * <p>
	 * This positioning is not suited for display, and is only used to calculate
	 * if the edges between the nodes cross.
	 * 
	 * @param wrapper
	 *            the wrapper of which its containing nodes require their order
	 *            applied to their y position
	 * @return {@code true} if the operation was successful<br>
	 *         {@code false} if the operation was unsuccessful due to an
	 *         conflicting order of nodes
	 * @throws IndexOutOfBoundsException
	 *             if the {@link SpaceWrapper} is not constructed following its
	 *             preconditions.<br>
	 *             TODO throws in doc if exception is not caught or expected?
	 */
	boolean applyOrderToY(SpaceWrapper wrapper) {
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
		int adjustment = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			list.get(i).setY(i);
			for (NodeWrapper node : list.get(i).getOutgoing()) {
				if (remainingNodes.contains(node)) {
					node.setY(i + ADJUSTMENT * adjustment++);
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
					node.setY(passedNodes.get(i).getY() + ADJUSTMENT
							* adjustment++);
					remainingNodes.remove(node);
					passedNodes.add(node);
				}
			}
		}
		return true;
	}
	
	/**
	 * Collapses the order of the nodes within the {@link SpaceWrapper} into a
	 * ordered list.
	 * 
	 * @param wrapper
	 *            the wrapper of which its node order should be calculated
	 * @return {@code null} if there is a conflict in node order
	 */
	List<NodeWrapper> collapseIntoList(SpaceWrapper wrapper) {
		List<List<NodeWrapper>> listsToCombine = new ArrayList<List<NodeWrapper>>();
		for (int n = wrapper.getNodeList().size() - 2; n >= 0; n--) {
			NodeWrapper node = wrapper.getNodeList().get(n);
			if (node.getOutgoing().size() > 1) {
				listsToCombine.add(new ArrayList<NodeWrapper>(node
						.getOutgoing()));
			}
		}
		return OrderedListUtil.mergeOrderedLists(listsToCombine);
	}
	
	/*
	 * Intersection method block
	 */
	/**
	 * Counts the number of intersections in the given {@link SpaceWrapper}.
	 * 
	 * @param wrapper
	 *            the {@link SpaceWrapper} with list of nodes to get the edges
	 *            from
	 * @return the number of intersections counted
	 */
	int countIntersections(SpaceWrapper wrapper) {
		return countIntersections(getLinesForSpaceWrapperIntersectionTest(wrapper));
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
	
	/**
	 * Creates a list of lines with for each edge within the
	 * {@link SpaceWrapper} a representing {@link Line}.
	 * 
	 * <p>
	 * For each edge a line is constructed by using the y value and previous
	 * node count value of each node on the edge.
	 * 
	 * <p>
	 * The edges from the first node to the rest are ignored, because they will
	 * never intersect due to the contract of the {@link SpaceWrapper}.
	 * 
	 * @param wrapper
	 *            the {@link SpaceWrapper} containing the edges which will be
	 *            used to construct the list of lines.
	 * @return a list of lines, representing the nodes within the
	 *         {@link SpaceWrapper}.
	 */
	List<Line> getLinesForSpaceWrapperIntersectionTest(SpaceWrapper wrapper) {
		List<Line> lines = new ArrayList<Line>();
		for (int i = wrapper.getNodeList().size() - 2; i >= 1; i--) {
			NodeWrapper from = wrapper.getNodeList().get(i);
			for (NodeWrapper to : from.getOutgoing()) {
				lines.add(new Line(from.getPreviousNodesCount(), to.getY(), to
						.getPreviousNodesCount(), from.getY()));
			}
		}
		return lines;
	}
	
	/*
	 * Configuration block
	 */
	/**
	 * Calculates a best configuration of node orders by trying all possible
	 * configurations until all configurations are tested, or a perfect
	 * configuration is found.
	 * 
	 * <p>
	 * A best configuration is the configuration with the least number of
	 * crossings. There could be multiple best configurations, but only the
	 * first best configuration found will be given as result.
	 * 
	 * @param wrapper
	 *            the wrapper of which to calculate its best order configuration
	 * @param iterations
	 *            the number of configurations to test, this should be equal to
	 *            the number of possible configurations
	 * @param order
	 *            the order pair list of which to compute the order with for
	 *            each configuration
	 * @return the best configuration for this wrapper
	 */
	int calculateBestConfig(SpaceWrapper wrapper, int iterations,
			List<Pair<List<NodeWrapper>, NodeWrapper[]>> order) {
		int best = Integer.MAX_VALUE;
		int bestConfig = 0;
		for (int i = 0; i < iterations; i++) {
			applyConfigurationToOrder(i, order);
			if (!applyOrderToY(wrapper)) {
				continue;
			}
			int found = countIntersections(wrapper);
			if (found < best) {
				// There is no better configuration to search for
				if (found == 0) {
					return i;
				}
				best = found;
				bestConfig = i;
			}
		}
		return bestConfig;
	}
	
	void applyConfigurationToOrder(int orderConfiguration,
			List<Pair<List<NodeWrapper>, NodeWrapper[]>> order) {
		int orderConfig = orderConfiguration;
		for (Pair<List<NodeWrapper>, NodeWrapper[]> pair : order) {
			
			// The number of possible configurations for this target
			int factorialLength = MathUtil
					.integerFactorial(pair.getSecond().length);
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
	
	void applyOrderConfiguration(int localConfiguration,
			List<NodeWrapper> currentOrder, NodeWrapper[] dictionary) {
		List<NodeWrapper> newOrder = new ArrayList<NodeWrapper>(
				dictionary.length);
		newOrder.add(dictionary[0]);
		for (int i = 1; i < dictionary.length; i++) {
			newOrder.add(localConfiguration % (i + 1), dictionary[i]);
			localConfiguration /= i + 1;
		}
		currentOrder.clear();
		currentOrder.addAll(newOrder);
	}
	
	/*
	 * Direction block
	 */
	
	long getOptionCountFromLeftToRight(List<NodeWrapper> nodeList) {
		long out = 1;
		for (int i = nodeList.size() - 2; i >= 0; i--) {
			out *= MathUtil.integerFactorial(nodeList.get(i).getOutgoing()
					.size());
		}
		return out;
	}
}
