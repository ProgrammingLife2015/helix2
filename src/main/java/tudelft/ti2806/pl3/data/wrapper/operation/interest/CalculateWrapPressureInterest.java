package tudelft.ti2806.pl3.data.wrapper.operation.interest;

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
 */
public class CalculateWrapPressureInterest extends WrapperOperation {
	private final int pressureMultiplier;
	
	public CalculateWrapPressureInterest(int pressureMultiplier) {
		this.pressureMultiplier = pressureMultiplier;
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(Wrapper::getGenome).map(Collection::size)
				.reduce(this.pressureMultiplier, (a, b) -> a * b));
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		wrapper.addInterest(wrapper.getNodeList().stream()
				.map(Wrapper::getGenome).map(Collection::size)
				.reduce(this.pressureMultiplier, (a, b) -> a * b).intValue());
		super.calculate(wrapper, container);
	}
}
