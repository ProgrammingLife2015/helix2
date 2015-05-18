package tudelft.ti2806.pl3.visualization.wrapper.operation.order;

import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

/**
 * A {@link WrapperSequencer} is used to calculate the order of each
 * {@link NodeWrapper} in a {@link NodeWrapper}.
 * 
 * @author Sam_
 *
 */
public abstract class WrapperSequencer extends WrapperOperation {
	/**
	 * Calculate the order for {@link HorizontalWrapper}.
	 * 
	 * <p>
	 * Change the order of the nodes of the {@link HorizontalWrapper} its list.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for each
	 * {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, container);
		}
	}
	
	/**
	 * Calculate the order for {@link VerticalWrapper}.
	 * 
	 * <p>
	 * Change the order of the nodes of the {@link VericalWrapper} its list.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for each
	 * {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, container);
		}
	}
	
	/**
	 * Calculate the order for {@link SpaceWrapper}.
	 * 
	 * <p>
	 * Change the order of incoming or outgoing connections for each node within
	 * the {@link SpaceWrapper} its list, to change the order within the
	 * wrapper. Don't change the order of the nodes within
	 * {@link CombineWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for each
	 * {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, container);
		}
	}
}
