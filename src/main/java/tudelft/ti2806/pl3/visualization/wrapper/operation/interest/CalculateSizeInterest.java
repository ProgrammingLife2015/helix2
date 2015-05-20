package tudelft.ti2806.pl3.visualization.wrapper.operation.interest;

import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodePosition;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

/**
 * Adds an interest value equal to the square root of the node its size. Bigger
 * nodes are more interesting because their mutation is more likely to be more
 * significant.
 * 
 * @author Sam Smulders
 */
public class CalculateSizeInterest extends WrapperOperation {
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getWidth()));
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getWidth()));
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getWidth()));
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getWidth()));
	}
	
	@Override
	public void calculate(NodePosition wrapper, NodeWrapper container) {
		wrapper.addInterest((int) Math.sqrt(wrapper.getWidth()));
	}
	
}
