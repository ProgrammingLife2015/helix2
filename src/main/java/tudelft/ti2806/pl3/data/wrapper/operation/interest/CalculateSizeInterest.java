package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

/**
 * Adds an interest value equal to the square root of the node its size. Bigger
 * nodes are more interesting because their mutation is more likely to be more
 * significant.
 * 
 * @author Sam Smulders
 */
public class CalculateSizeInterest extends WrapperOperation {
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getBasePairCount()));
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getBasePairCount()));
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getBasePairCount()));
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest((int) Math.sqrt(wrapper.getBasePairCount()));
	}
	
	@Override
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		wrapper.addInterest((int) Math.sqrt(wrapper.getBasePairCount()));
	}
	
}
