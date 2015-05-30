package tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;

public class UnwrapOnCollapse extends Unwrap {
	
	private final float spaceCondition;
	
	public UnwrapOnCollapse(float spaceCondition) {
		this.spaceCondition = spaceCondition;
	}
	
	@Override
	protected boolean isConditionMet(CombineWrapper node) {
		return node.getCollapse() >= spaceCondition;
	}
	
}
