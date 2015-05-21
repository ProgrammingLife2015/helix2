package tudelft.ti2806.pl3.visualization.wrapper.operation.collapse;

import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

public class CollapseOnInterest extends WrapperOperation {
	/**
	 * the minimum interest required for a combined wrapper to have its content
	 * fully displayed.
	 */
	private int minInterestValue;
	
	/**
	 * 
	 * @param minInterestValue
	 *            the minimum interest required for a combined wrapper to have
	 *            its content fully displayed.
	 */
	public CollapseOnInterest(int minInterestValue) {
		this.minInterestValue = minInterestValue;
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		if (wrapper.getInterest() < minInterestValue) {
			wrapper.setShouldUnfold(false);
		} else {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		if (wrapper.getInterest() < minInterestValue) {
			wrapper.setShouldUnfold(false);
		} else {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		if (wrapper.getInterest() < minInterestValue) {
			wrapper.setShouldUnfold(false);
		} else {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
}
