package tudelft.ti2806.pl3.util;

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
	private float maxInterest = Float.MIN_VALUE;
	
	public CollectInterest(int domain) {
		this.interest = new float[domain];
		this.domain = domain;
	}
	
	public float[] getInterest() {
		return this.interest;
	}
	
	public void calculate(List<Wrapper> wrappers) {
		double width = wrappers.stream().mapToDouble(Wrapper::getX).max().getAsDouble();
		for (Wrapper wrapper : wrappers) {
			int index = (int) Math.min(this.domain - 1, (wrapper.getX() / width) * this.domain);
//			System.out.println("@" + index);
			this.interest[index] += wrapper.getInterest();
			maxInterest = Math.max(maxInterest, this.interest[index]);
		}
//		for (int i = 0; i < this.domain; i++) {
//			System.out.println(this.interest[i]);
//		}
	}
	
	public float getMaxInterest() {
		return maxInterest;
	}
}
