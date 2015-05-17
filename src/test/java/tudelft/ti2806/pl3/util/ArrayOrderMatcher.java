package tudelft.ti2806.pl3.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;

import java.util.Arrays;

public class ArrayOrderMatcher extends BaseMatcher<NodeWrapper[]> {
	
	private NodeWrapper[] order;
	
	public ArrayOrderMatcher(NodeWrapper[] order) {
		this.order = order;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("The given list its order should be in "
				+ Arrays.toString(order));
	}
	
	@Override
	public boolean matches(Object item) {
		if (item == null) {
			return false;
		}
		if (item.getClass() != NodeWrapper[].class) {
			return false;
		}
		NodeWrapper[] subList = (NodeWrapper[]) item;
		int index = 0;
		for (NodeWrapper node : order) {
			if (subList[index].equals(node)) {
				index++;
				if (index == subList.length) {
					return true;
				}
			}
		}
		return false;
	}
}
