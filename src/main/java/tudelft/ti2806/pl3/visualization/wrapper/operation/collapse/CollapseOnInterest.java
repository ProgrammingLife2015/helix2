package tudelft.ti2806.pl3.visualization.wrapper.operation.collapse;

import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

public class CollapseOnInterest extends WrapperOperation {
	private int interestValue;
	
	public CollapseOnInterest(int interestValue) {
		this.interestValue = interestValue-2;
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		if (wrapper.getInterest() >= interestValue) {
			wrapper.setCollapse(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		if (wrapper.getInterest() >= interestValue) {
			wrapper.setCollapse(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		if (wrapper.getInterest() >= interestValue) {
			wrapper.setCollapse(true);
			super.calculate(wrapper, container);
		}
	}
}
