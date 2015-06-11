package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

/**
 * Created by Boris Mattijssen on 10-06-15.
 */
public class CanUnwrapOperation extends WrapperOperation {

	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.setCanUnwrap(true);
	}

}
