package tudelft.ti2806.pl3.visualization.wrapper.operation.interest;

import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

/**
 * For each {@link CombineWrapper} or {@link SingleWrapper}, add the max
 * interest value of their node (list).
 * 
 * @author Sam Smulders
 *
 */
public class CalculateAddMaxOfWrapped extends WrapperOperation {
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(NodeWrapper::getInterest).max(Integer::compare).get());
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(NodeWrapper::getInterest).max(Integer::compare).get());
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(NodeWrapper::getInterest).max(Integer::compare).get());
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, NodeWrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNode().getInterest());
	}
}
