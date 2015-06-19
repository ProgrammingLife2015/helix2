package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.List;

/**
 * Collects interest of {@link DataNodeWrapper}'s and projects it on an array with a given size.
 *
 * @author Sam Smulders
 */
public class CollectInterest {
	private final float[] interest;
	private final int domain;
	private float maxInterest = -Float.MAX_VALUE;
	
	public CollectInterest(int domain) {
		this.interest = new float[domain];
		this.domain = domain;
	}
	
	public float[] getInterest() {
		return this.interest;
	}
	
	/**
	 * Collects interest of {@link DataNodeWrapper}'s and projects it on an array with a given size.
	 *
	 * @param wrappers
	 *            the list of wrappers
	 */
	public void calculate(List<Wrapper> wrappers) {
		double width = wrappers.stream().mapToDouble(Wrapper::getX).max().getAsDouble();
		for (Wrapper wrapper : wrappers) {
			int index = (int) Math.min(this.domain - 1, (wrapper.getX() / width) * this.domain);
			this.interest[index] += wrapper.getInterest();
//			System.out.println(wrapper.getInterest());
		}
		for (int i = 0; i < this.interest.length; i++) {
//			System.out.print(this.interest[i]+"->");
			this.interest[i] = (float) Math.sqrt(this.interest[i]);
//			System.out.println(this.interest[i]);
			this.maxInterest = Math.max(this.maxInterest, this.interest[i]);
		}
	}
	
	public float getMaxInterest() {
		return this.maxInterest;
	}
}
