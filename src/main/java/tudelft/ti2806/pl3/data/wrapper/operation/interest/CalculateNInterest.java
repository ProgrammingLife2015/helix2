package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

/**
 * Computes the interestingness based on the N percentage.
 * 
 * @author Sam Smulders
 */
public class CalculateNInterest extends WrapperOperation {
	
	/**
	 * The value of how interesting this {@link DataNode} property is. Giving it
	 * a negative number makes {@link DataNode}s with a high N percentage less
	 * interesting.
	 */
	private final int interest;
	
	public CalculateNInterest(int interest) {
		this.interest = interest;
	}
	
	@Override
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		wrapper.addInterest((int) (this.interest * computeRation(wrapper)));
	}
	
	/**
	 * Computes the ratio of N's in the {@link DataNode}s within the given
	 * wrapper.
	 * 
	 * @param wrapper
	 *            the wrapper
	 * @return the ratio, between 1.0 and 0.0
	 */
	private double computeRation(DataNodeWrapper wrapper) {
		long nCount = 0;
		long totalCount = 0;
		for (DataNode dataNode : wrapper.getDataNodes()) {
			totalCount += dataNode.getBasePairCount();
			for (byte b : dataNode.getContent()) {
				if (b == BasePair.N.storeByte) {
					nCount += 1;
				}
			}
		}
		return ((double) nCount) / totalCount;
	}
}
