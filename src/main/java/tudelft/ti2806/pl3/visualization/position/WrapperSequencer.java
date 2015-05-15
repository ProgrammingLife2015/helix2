package tudelft.ti2806.pl3.visualization.position;

import tudelft.ti2806.pl3.visualization.position.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.VerticalWrapper;

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
	 * Change the order of the nodes of the {@link SpaceWrapper} its list.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
	default void calculate(HorizontalWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
	
	/**
	 * Calculate the order for {@link VerticalWrapper}.
	 * 
	 * <p>
	 * Change the order of the nodes of the {@link SpaceWrapper} its list.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 */
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
	default void calculate(SpaceWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
}
