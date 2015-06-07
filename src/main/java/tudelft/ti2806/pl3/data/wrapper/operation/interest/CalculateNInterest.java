package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

public class CalculateNInterest extends WrapperOperation {
	private final int interestRatio;
	
	public CalculateNInterest(int interestRatio) {
		this.interestRatio = interestRatio;
	}
	
	@Override
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		wrapper.addInterest((int) (this.interestRatio * computeRation(wrapper)));
	}
	
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
