package tudelft.ti2806.pl3.visualization.position;

import tudelft.ti2806.pl3.visualization.position.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.SpaceWrapper;

/**
 * A {@link WrapperSequencer} is used to calculate the order of each
 * {@link NodeWrapper} in a {@link NodeWrapper}.
 * 
 * @author Sam_
 *
 */
public interface WrapperSequencer extends WrapperOperation {
	/**
	 * The first and the last element of the nodes within SpaceWrapper should be
	 * left on their original locations.
	 */
	default void calculate(SpaceWrapper wrapper) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node);
		}
	}
}
