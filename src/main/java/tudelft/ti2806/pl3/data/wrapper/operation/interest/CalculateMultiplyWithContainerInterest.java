package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

public class CalculateMultiplyWithContainerInterest extends WrapperOperation {
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		compute(wrapper, container);
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		compute(wrapper, container);
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		compute(wrapper, container);
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, Wrapper container) {
		compute(wrapper, container);
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		compute(wrapper, container);
	}
	
	/**
	 * Multiplies the interest of a wrapper with its parents interest.
	 * 
	 * @param wrapper
	 *            the wrapper
	 * @param container
	 *            the parent
	 */
	@SuppressWarnings("PMD.UnusedPrivateMethod")
	private void compute(Wrapper wrapper, Wrapper container) {
		if (wrapper.getInterest() == 0) {
			wrapper.addInterest(1);
		}
		if (container != null) {
			wrapper.multiplyInterest(container.getInterest());
		}
	}
}
