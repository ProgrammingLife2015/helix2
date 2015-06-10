package tudelft.ti2806.pl3.data.wrapper.operation.collapse;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Computes the collapse value, based on the space left between nodes when unwrapped.
 * 
 * @author Sam Smulders
 */
public class CalculateCollapseOnSpace extends WrapperOperation {
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		if (wrapper.canUnwrap()) {
			super.calculate(wrapper, container);
			wrapper.addCollapse(getSpaceLeft(wrapper));
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addCollapse(getSpaceLeft(wrapper));
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addCollapse(getSpaceLeft(wrapper));
	}
	
	/**
	 * Computes if there is enough space for a wrapper to unfold it.
	 * 
	 * @param wrapper
	 *            the wrapper of which to determine if there is enough space to unfold it
	 * @return a value of the average space between nodes.
	 */
	float getSpaceLeft(CombineWrapper wrapper) {
		List<Wrapper> nodeList = new ArrayList<>(wrapper.getNodeList());
		Collections.sort(nodeList);
		float min = Float.MAX_VALUE;
		for (int i = nodeList.size() - 2; i >= 0; i--) {
			if (nodeList.get(i + 1).getPreviousNodesCount() == nodeList.get(i).getPreviousNodesCount()) {
				continue;
			}
			min = Math.min(Math.abs(nodeList.get(i + 1).getX() - nodeList.get(i).getX()), min);
		}
		return min;
	}
	
	public class XComparator implements Comparator<Wrapper> {
		@Override
		public int compare(Wrapper w1, Wrapper w2) {
			return (int) Math.signum(w1.getX() - w2.getX());
		}
	}
}
