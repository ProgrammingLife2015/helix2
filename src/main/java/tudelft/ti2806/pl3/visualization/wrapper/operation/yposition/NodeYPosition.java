package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import tudelft.ti2806.pl3.visualization.wrapper.*;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

/**
 * This class calculates the y position of the Nodes that are inside a Wrapper.
 * The y-scale is from 0 to 1, where 0.5 is the middle of the y pos.
 * Created by Kasper on 18-5-2015.
 */
public class NodeYPosition extends WrapperOperation {

	private static final float Y_START = 0.5f;
	private static final float Y_SPACE = 1f;

	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		if ( container == null ) {
			wrapper.setY(Y_START);
		}else{
			wrapper.setY(container.getY());
		}
		int totalGenomes = wrapper.getGenome().size();
		int counter = 0;
		for ( NodeWrapper nodeWrapper : wrapper.getNodeList() ) {
			counter += nodeWrapper.getGenome().size();
			// y position is in the middle of the genome space
			float ypos = ((counter / totalGenomes) * Y_SPACE ) / 2;
			nodeWrapper.setY(ypos);
			calculate(nodeWrapper, wrapper);
		}
	}

	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		if ( container == null ) {
			wrapper.setY(Y_START);
		}

		// do something here
	}

	/**
	 *
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 */
	@Override
	public void calculate(SingleWrapper wrapper, NodeWrapper container) {
		if ( container == null ) {
			wrapper.setY(Y_START);
		}

		NodeWrapper nodeWrapper = wrapper.getNode();
		nodeWrapper.setY(wrapper.getY());
		calculate(nodeWrapper,wrapper);
	}

	@Override
	public void calculate(NodePosition wrapper, NodeWrapper container) {
		if (container == null) {
			wrapper.setY(Y_START);
		}
	}

	/**
	 * Calculate the position of all the nodes in the {@link HorizontalWrapper}.
	 * If {@link @NodeWrapper} is {@link null} then the nodes are not wrapped.
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 */
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		if ( container == null ) {
			// nodes are not wrapped.
			wrapper.setY(Y_START);
		}else{
			wrapper.setY(container.getY());
		}


		for (NodeWrapper nodeWrapper : wrapper.getNodeList()) {
			nodeWrapper.setY(wrapper.getY());
			calculate(nodeWrapper, wrapper);
		}
	}
}
