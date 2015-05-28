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
 * Decides what to fold or unfold, based on the average space left between nodes
 * inside a wrapped node.
 * 
 * @author Sam Smulders
 */
public class CollapseOnSpace extends WrapperOperation {
	private float space;
	
	public CollapseOnSpace(float space) {
		this.space = space;
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		if (hasSpaceLeft(wrapper)) {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		} else {
			wrapper.setShouldUnfold(false);
		}
	}
	
	@Override
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		if (hasSpaceLeft(wrapper)) {
			wrapper.setShouldUnfold(true);
			super.calculate(wrapper, container);
		} else {
			wrapper.setShouldUnfold(false);
		}
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		wrapper.setShouldUnfold(true);
		super.calculate(wrapper, container);
	}
	
	/**
	 * Calculates if there is enough space for a wrapper to unfold it.
	 * 
	 * @param wrapper
	 *            the wrapper of which to determine if there is enough space to
	 *            unfold it
	 * @return {@code true} if the node has enough space to unfold<br>
	 *         {@code false} if the node has not enough space to unforld, and
	 *         should stay folded
	 */
	boolean hasSpaceLeft(CombineWrapper wrapper) {
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
		return avg / count > space;
	}
	
	public class XComparator implements Comparator<Wrapper> {
		@Override
		public int compare(Wrapper w1, Wrapper w2) {
			return (int) Math.signum(w1.getX() - w2.getX());
		}
	}
}
