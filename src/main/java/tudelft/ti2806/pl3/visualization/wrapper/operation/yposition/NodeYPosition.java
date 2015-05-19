package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

/**
 * This class calculates the y position of the Nodes that are inside a Wrapper.
 * The y-scale is from 0 to 1, where 0.5 is the middle of the y pos.
 * Created by Kasper on 18-5-2015.
 */
public class NodeYPosition extends WrapperOperation {

	/**
	 * The start values of the y-space and beginpostion on the y-scale.
	 */
	private static final float Y_START = 0.5f;
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
		double totalGenomes = wrapper.getGenome().size();
		double lastspace = 0.0;
		for (NodeWrapper nodeWrapper : wrapper.getNodeList()) {
			// the node is in the middle of his yspace.
			double yspace = (nodeWrapper.getGenome().size() / totalGenomes)
					* wrapper.getySpace();
			double ypos = yspace / 2 + lastspace;

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
//		double totalGenomes = wrapper.getGenome().size();
//		double lastpace = 0.0;
//		for (NodeWrapper nodeWrapper : wrapper.getNodeList()) {
//			double yspace = nodeWrapper.getGenome().size() / totalGenomes
//					* wrapper.getySpace();
//			double ypos = yspace / 2 + lastpace;
//
//			nodeWrapper.setySpace((float) yspace);
//			nodeWrapper.setY((float) ypos);
//
//			lastpace += yspace;
//			calculate(nodeWrapper, wrapper);
//		}
	}

	/**
	 * Sets the y-position of the nodes that are inside the {@link SingleWrapper}
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
}
