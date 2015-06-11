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
	@Override
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		wrapper.multiplyInterest(computeRation(wrapper));
	}
	
	/**
	 * Computes the ratio of N's in the {@link DataNode}s within the given wrapper.
	 * 
	 * @param wrapper
	 *            the wrapper
	 * @return the ratio, between 1.0 and 0.0
	 */
	private float computeRation(DataNodeWrapper wrapper) {
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
		if (totalCount == 0) {
			return 1;
		}
		return ((float) totalCount - nCount) / totalCount;
	}
}
