package tudelft.ti2806.pl3.data.wrapper.operation.collapse;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Chooses to unwrap nodes to try to split the different groups.
 * 
 * <p>
 * If a node only contains nodes out one group, it stays collapsed.<br>
 * If a node contains nodes from multiple groups, we unwrap it.
 * 
 * @author Sam Smulders
 */
public class CollapseGroups extends WrapperOperation {
	private List<Set<Genome>> groups;
	
	public CollapseGroups(List<Set<Genome>> groups) {
		this.groups = groups;
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		if (isIntresting(wrapper)) {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		if (isIntresting(wrapper)) {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		if (isIntresting(wrapper)) {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		}
	}
	
	boolean isIntresting(CombineWrapper wrapper) {
		Set<Genome> genome = wrapper.getGenome();
		boolean found = false;
		for (Set<Genome> group : groups) {
			if (!Collections.disjoint(group, genome)) {
				if (found) {
					return true;
				} else {
					found = true;
				}
			}
		}
		return false;
	}
}
