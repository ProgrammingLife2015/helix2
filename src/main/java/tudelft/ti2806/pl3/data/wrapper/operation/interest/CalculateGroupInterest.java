package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Adds interest to try to split the different groups.
 * 
 * <p>
 * If a node only contains nodes out one group, it gets no extra interest.<br>
 * If a node contains nodes from multiple groups, we want to try to split this
 * node and add extra interest. <br>
 * {@link DataNodeWrapper} is ignored because this node can't be split.
 * 
 * @author Sam Smulders
 *
 */
public class CalculateGroupInterest extends WrapperOperation {
	
	private final List<Set<Genome>> groups;
	private final int interest;
	
	public CalculateGroupInterest(List<Set<Genome>> groups, int groupInterest) {
		this.groups = groups;
		this.interest = groupInterest;
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		if (isInteresting(wrapper)) {
			wrapper.addInterest(interest);
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		if (isInteresting(wrapper)) {
			wrapper.addInterest(interest);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		if (isInteresting(wrapper)) {
			wrapper.addInterest(interest);
		}
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		if (isInteresting(wrapper)) {
			wrapper.addInterest(interest);
		}
	}
	
	/**
	 * An node is more interesting if a group contains nodes from two groups.
	 * 
	 * <p>
	 * This method searches through the sets of genome if there are at least
	 * genomes of two genome sets in this node.
	 * 
	 * @param wrapper
	 *            the node to test
	 * @return {@code true} if there are genomes in the node of at least two
	 *         sets of genome<br>
	 *         {@code false} else
	 */
	private boolean isInteresting(Wrapper wrapper) {
		Set<Genome> genome = wrapper.getGenome();
		boolean foundFirst = false;
		for (Set<Genome> group : groups) {
			if (!Collections.disjoint(group, genome)) {
				if (foundFirst) {
					return true;
				} else {
					foundFirst = true;
				}
			}
		}
		return false;
	}
}
