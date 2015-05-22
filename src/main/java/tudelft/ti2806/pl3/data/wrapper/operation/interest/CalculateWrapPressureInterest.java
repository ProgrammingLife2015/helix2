package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Collection;

/**
 * Adds an high interest value when the number of genomes in nodes are equally
 * distributed over the nodes, a relative low interest if the genomes are not
 * well distributed over the nodes.
 * 
 * @author Sam Smulders
 *
 */
public class CalculateWrapPressureInterest extends WrapperOperation {
	private final int pressureMultiplier;
	
	public CalculateWrapPressureInterest(int pressureMultiplier) {
		this.pressureMultiplier = pressureMultiplier;
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(Wrapper::getGenome).map(Collection::size)
				.reduce(pressureMultiplier, (a, b) -> a * b));
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(Wrapper::getGenome).map(Collection::size)
				.reduce(pressureMultiplier, (a, b) -> a * b).intValue());
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addInterest(wrapper.getNode().getInterest());
	}
}
