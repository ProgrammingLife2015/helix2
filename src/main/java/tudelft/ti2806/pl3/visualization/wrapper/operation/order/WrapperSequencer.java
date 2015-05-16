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
public interface WrapperSequencer extends WrapperOperation {
	/**
	 * Calculate the order for {@link HorizontalWrapper}.
	 * 
	 * <p>
	 * Change the order of the nodes of the {@link HorizontalWrapper} its list.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	default void calculate(HorizontalWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
	
	/**
	 * Calculate the order for {@link VerticalWrapper}.
	 * 
	 * <p>
	 * Change the order of the nodes of the {@link VericalWrapper} its list.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	default void calculate(VerticalWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
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
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	@Override
	default void calculate(SpaceWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
}
