package tudelft.ti2806.pl3.visualization.wrapper.operation.order;

import tudelft.ti2806.pl3.math.Line;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;

import java.util.ArrayList;
import java.util.List;

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
		Pair<Boolean, Long> direction = getBestDirection(wrapper.getNodeList());
		List<List<NodeWrapper>> currentOrder;
		if (direction.getFirst()) {
			currentOrder = getOutgoingList(wrapper.getNodeList());
		} else {
			currentOrder = getIncomingList(wrapper.getNodeList());
		}
		int best = Integer.MAX_VALUE;
		int score = countIntersections(getIncomingLines(wrapper.getNodeList()));
		List<NodeWrapper[]> order = new ArrayList<NodeWrapper[]>();
		for (List<NodeWrapper> list : currentOrder) {
			NodeWrapper[] connections = new NodeWrapper[list.size()];
			for (int i = list.size() - 1; i >= 0; i--) {
				connections[i] = list.get(i);
			}
			order.add(connections);
		}
		
		// TODO:
		// For every configuration
		// - Apply configuration
		// - Apply order -> y
		// - Calculate crossings
		// then choose best
		
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
	
	void applyOrder(int orderConfiguration,
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
			applyOrder(orderConfig % factorialLength, pair.getFirst(),
					pair.getSecond());
			orderConfig /= factorialLength;
		}
	}
	
	void applyOrder(int localConfiguration, List<NodeWrapper> currentOrder,
			NodeWrapper[] dictionary) {
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
	
	List<List<NodeWrapper>> getIncomingList(List<NodeWrapper> nodes) {
		List<List<NodeWrapper>> list = new ArrayList<List<NodeWrapper>>();
		for (int i = nodes.size() - 1; i >= 0; i--) {
			list.add(nodes.get(i).getIncoming());
		}
		return list;
	}
	
	List<List<NodeWrapper>> getOutgoingList(List<NodeWrapper> nodes) {
		List<List<NodeWrapper>> list = new ArrayList<List<NodeWrapper>>();
		for (int i = nodes.size() - 1; i >= 0; i--) {
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
	
	long getOptionCountFromLeftToRight(List<NodeWrapper> nodeList) {
		long out = 1;
		for (int i = nodeList.size() - 2; i >= 0; i--) {
			out *= factorial(nodeList.get(i).getOutgoing().size());
		}
		return out;
	}
	
	long getOptionCountFromRightToLeft(List<NodeWrapper> nodeList) {
		long in = 1;
		for (int i = nodeList.size() - 1; i >= 1; i--) {
			in *= factorial(nodeList.get(i).getIncoming().size());
		}
		return in;
	}
	
	private int factorial(int n) {
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
	Pair<Boolean, Long> getBestDirection(List<NodeWrapper> nodeList) {
		long leftToRight = getOptionCountFromLeftToRight(nodeList);
		long rightToLeft = getOptionCountFromRightToLeft(nodeList);
		if (leftToRight <= rightToLeft) {
			return new Pair<Boolean, Long>(true, leftToRight);
		} else {
			return new Pair<Boolean, Long>(false, rightToLeft);
		}
	}
}
