package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.Wrapper;

public class ComputeInterest {
	/**
	 * Computes the interest values on the graph.
	 * 
	 * @param wrapper
	 *            the wrapper
	 */
	public void compute(Wrapper wrapper) {
		new CalculateWrapPressureInterest().calculate(wrapper, null);
		new CalculateMultiplyWithContainerInterest().calculate(wrapper, null);
		new CalculateSizeInterest().calculate(wrapper, null);
		new CalculateNInterest().calculate(wrapper, null);
	}
}
