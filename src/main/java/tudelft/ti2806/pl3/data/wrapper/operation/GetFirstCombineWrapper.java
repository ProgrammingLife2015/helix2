package tudelft.ti2806.pl3.data.wrapper.operation;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

public class GetFirstCombineWrapper extends WrapperOperation {
	
	private CombineWrapper result;
	
	public CombineWrapper compute(Wrapper wrapper) {
		calculate(wrapper, null);
		return this.result;
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		this.result = wrapper;
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		this.result = wrapper;
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		this.result = wrapper;
	}
}
