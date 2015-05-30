package tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;

public class UnwrapOnInterest extends Unwrap {
	
	private final int interestCondition;
	
	public UnwrapOnInterest(int interestCondition) {
		this.interestCondition = interestCondition;
	}
	
	@Override
	protected boolean isConditionMet(CombineWrapper node) {
		return node.getInterest() >= interestCondition;
	}
	
}
