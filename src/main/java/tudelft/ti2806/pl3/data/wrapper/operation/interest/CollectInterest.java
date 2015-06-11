package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

/**
 * Collects interest of {@link DataNodeWrapper}'s and projects it on an array
 * with a given size.
 * 
 * @author Sam Smulders
 */
public class CollectInterest extends WrapperOperation {
	private final float[] interest;
	private final int domain;
	
	public CollectInterest(int domain) {
		this.interest = new float[domain];
		this.domain = domain;
	}
	
	public float[] getInterest() {
		return this.interest;
	}
	
	@Override
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		this.interest[(int) (wrapper.getX() / this.domain)] += wrapper
				.getInterest();
	}
}
