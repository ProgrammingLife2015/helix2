package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.Collections;
import java.util.ListIterator;

/**
 * This class calculates the y position of the Nodes that are inside a Wrapper.
 * The y-scale is from 0 to 1, where 0.5 is the middle of the y pos.
 * Created by Kasper on 18-5-2015.
 */
public class NodeYPosition extends WrapperOperation {

	/**
	 * The start values of the y-space and beginpostion on the y-scale.
	 */
	private static final float Y_START = 0.0f;
	private static final float Y_SPACE = 1f;

	/**
	 * Calculate the y-position of the nodes in {@link NodeWrapper}.
	 * This constructor assumes that {@link NodeWrapper} is the highest parent.
	 *
	 * @param nodeWrapper
	 * 		the wrapper containing the nodes.
	 */
	public static void init(NodeWrapper nodeWrapper) {
		nodeWrapper.setY(Y_START);
		nodeWrapper.setySpace(Y_SPACE);
		NodeYPosition nodeYPosition = new NodeYPosition();
		nodeYPosition.calculate(nodeWrapper, null);
	}

	/**
	 * Calculate the y-position of the nodes in {@link NodeWrapper}.
	 * This constructor assumes that {@link NodeWrapper} is the highest parent.
	 *
	 * @param nodeWrapper
	 * 		the wrapper containing the nodes.
	 */
	public static void init(NodeWrapper nodeWrapper, NodeWrapper container) {
		nodeWrapper.setY(container.getY());
		nodeWrapper.setySpace(container.getySpace());
		NodeYPosition nodeYPosition = new NodeYPosition();
		nodeYPosition.calculate(nodeWrapper, container);
	}

	/**
	 * Construct is used to call the calculate function.
	 * This is private since it should not be accessed
	 * other then by the init method.
	 */
	private NodeYPosition() {
	}

	/**
	 * Sets the y-space and y-position of the nodes in {@link VerticalWrapper}.
	 * The y-space is based on the amount of genomes in the node, the y-position
	 * is in the middle of the y-space of the node.
	 *
	 * @param wrapper
	 * 		the node to perform the operation on
	 * @param container
	 * 		the wrapper containing this node<br>
	 */
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		double lastspace = 0.0;
		sortOnAmountOfGenomes(wrapper);

		for (NodeWrapper nodeWrapper : wrapper.getNodeList()) {
			// the node is in the middle of his yspace.
			double yspace = this.calculateYSpace(nodeWrapper.getGenome().size()
					, wrapper.getGenome().size(), wrapper.getySpace());
			double ypos = this.calculateYPos(yspace, lastspace);

			// set the y values
			nodeWrapper.setySpace((float) yspace);
			nodeWrapper.setY((float) ypos);

			// keep track of the yspace already used.
			lastspace += yspace;
			calculate(nodeWrapper, wrapper);
		}
	}

	/**
	 * Sets the y-position of the nodes that are inside the {@link SpaceWrapper}	 *
	 * @param wrapper
	 * 		the node to perform the operation on
	 * @param container
	 * 		the wrapper containing this node<br>
	 */
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		double lastspace = 0.0;
		sortOnAmountOfGenomes(wrapper);

		// after sorting the first and second in the list must be the
		// first and last node.

		ListIterator<NodeWrapper> nodeWrapperIt = wrapper.getNodeList().listIterator();
		// first and last node should be on the level that the parent gives them
		NodeWrapper first = nodeWrapperIt.next();
		NodeWrapper last = nodeWrapperIt.next();
		first.setY(wrapper.getY());
		first.setySpace(wrapper.getySpace());
		last.setY(wrapper.getY());
		last.setySpace(wrapper.getySpace());
		calculate(first, wrapper);
		calculate(last, wrapper);

		while (nodeWrapperIt.hasNext()) {
			NodeWrapper nodeWrapper = nodeWrapperIt.next();
			double yspace = calculateYSpace(nodeWrapper.getGenome().size(),
					wrapper.getGenome().size(), wrapper.getySpace());
			double ypos = calculateYPos(yspace, lastspace + wrapper.getY());

			nodeWrapper.setySpace((float) yspace);
			nodeWrapper.setY((float) ypos);
			lastspace += yspace;

			calculate(nodeWrapper, wrapper);
		}
	}

	/**
	 * Sets the y-position of the nodes that are inside the {@link SingleWrapper}	 *
	 * @param wrapper
	 * 		the node to perform the operation on
	 * @param container
	 * 		the wrapper containing this node<br>
	 */
	@Override
	public void calculate(SingleWrapper wrapper, NodeWrapper container) {
		wrapper.getNode().setY(wrapper.getY());
		calculate(wrapper.getNode(), wrapper);
	}

	/**
	 * Calculate the position of all the nodes in the {@link HorizontalWrapper}.
	 * The y-position of a node is the same as his parent since the nodes are
	 * horizontally wrapped.
	 *
	 * @param wrapper
	 * 		the node to perform the operation on
	 * @param container
	 * 		the wrapper containing this node<br>
	 */
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper nodeWrapper : wrapper.getNodeList()) {
			nodeWrapper.setY(wrapper.getY());
			nodeWrapper.setySpace(wrapper.getySpace());
			calculate(nodeWrapper, wrapper);
		}
	}

	/**
	 * Calculate the yspace.
	 * This is based on the fraction of the genomes in the node with the total genomes.
	 *
	 * @param amountofGenomes
	 * 		amount of genomes in the node
	 * @param totalGenomes
	 * 		total genomes of the parent
	 * @param parentSpace
	 * 		yspace of the parent
	 * @return yspace of the node
	 */
	private double calculateYSpace(double amountofGenomes, double totalGenomes, double parentSpace) {
		double space = amountofGenomes / totalGenomes * parentSpace;
		return space;
	}

	/**
	 * Calculate the ypos of the node.
	 * The node will be in the middle of his yspace.
	 *
	 * @param yspace
	 * 		yspace of the node
	 * @param leftspace
	 * 		space already used
	 * @return ypos of the node
	 */
	private double calculateYPos(double yspace, double leftspace) {
		return yspace / 2 + leftspace;
	}

	/**
	 * Sort the nodes in the combinewrapper on their amount of genomes.
	 * The first element has the highest genome amount.
	 *
	 * @param nodeWrapper
	 * 		wrapper with the nodes.
	 */
	@SuppressWarnings("unused")
	private void sortOnAmountOfGenomes(CombineWrapper nodeWrapper) {
		Collections.sort(nodeWrapper.getNodeList(),
				(o1, o2) -> o2.getGenome().size() - o1.getGenome().size());
	}
}
