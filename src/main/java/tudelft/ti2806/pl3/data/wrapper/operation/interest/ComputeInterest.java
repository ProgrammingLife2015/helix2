package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.Wrapper;

public class ComputeInterest {
	public void compute(Wrapper wrapper) {
		new CalculateWrapPressureInterest().calculate(wrapper, null);
		new CalculateMultiplyWithContainerInterest().calculate(wrapper, null);
		new CalculateSizeInterest().calculate(wrapper, null);
	}
}
