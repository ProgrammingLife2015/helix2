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
 * Computes the collapse value, based on the space left between nodes when
 * unwrapped.
 * 
 * @author Sam Smulders
 */
public class CalculateCollapseOnSpace extends WrapperOperation {
	private List<Float> list;
	
	/**
	 * Computes the distances between the nodes.
	 * 
	 * @param wrapper
	 *            the wrapped graph
	 */
	public void compute(Wrapper wrapper) {
		list = new ArrayList<>();
		list.add(Float.MAX_VALUE);
		calculate(wrapper, null);
		Collections.sort(list);
		Collections.sort(list, Collections.reverseOrder());
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		if (wrapper.canUnwrap()) {
			super.calculate(wrapper, container);
			wrapper.addCollapse(getSpaceLeft(wrapper));
			this.addToList(wrapper);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addCollapse(getSpaceLeft(wrapper));
		this.addToList(wrapper);
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		super.calculate(wrapper, container);
		wrapper.addCollapse(getSpaceLeft(wrapper));
		this.addToList(wrapper);
	}
	
	/**
	 * Adds the collapsed value for the increase of shown nodes when the given
	 * wrapper is unfolded.
	 * 
	 * @param wrapper
	 *            the wrapper
	 */
	private void addToList(CombineWrapper wrapper) {
		for (int i = wrapper.getNodeList().size(); i > 1; i--) {
			list.add(wrapper.getCollapse());
		}
	}
	
	/**
	 * Computes if there is enough space for a wrapper to unfold it.
	 * 
	 * @param wrapper
	 *            the wrapper of which to determine if there is enough space to
	 *            unfold it
	 * @return a value of the average space between nodes.
	 */
	private float getSpaceLeft(CombineWrapper wrapper) {
		List<Wrapper> list = new ArrayList<>(wrapper.getNodeList());
		Collections.sort(list, new XComparator());
		float avg = 0;
		int count = list.size();
		for (int i = list.size() - 2; i >= 0; i--) {
			float left = list.get(i + 1).getX() - list.get(i).getX();
			if (left == 0) {
				count--;
			} else {
				avg += left;
			}
		}
		return avg / count;
	}
	
	private class XComparator implements Comparator<Wrapper> {
		@Override
		public int compare(Wrapper w1, Wrapper w2) {
			return (int) Math.signum(w1.getX() - w2.getX());
		}
	}
	
	public List<Float> getCollapses() {
		return list;
	}
}
