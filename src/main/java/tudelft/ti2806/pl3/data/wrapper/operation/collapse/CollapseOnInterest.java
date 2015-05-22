package tudelft.ti2806.pl3.data.wrapper.operation.collapse;

import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

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
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		if (wrapper.getInterest() < minInterestValue) {
			wrapper.setShouldUnfold(false);
		} else {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		if (wrapper.getInterest() < minInterestValue) {
			wrapper.setShouldUnfold(false);
		} else {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		if (wrapper.getInterest() < minInterestValue) {
			wrapper.setShouldUnfold(false);
		} else {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
}
