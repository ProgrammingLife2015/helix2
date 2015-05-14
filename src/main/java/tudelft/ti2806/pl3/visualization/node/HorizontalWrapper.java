package tudelft.ti2806.pl3.visualization.node;

import java.util.List;

/**
 * A collection of horizontal combine able nodes.
 * 
 * @author Mathieu Post
 * @author Sam Smulders
 */
public class HorizontalWrapper extends CombineWrapper {
	
	/**
	 * An collection of {@link SNodes} which can be used as a single SNode.
	 * 
	 * @param nodePosList
	 *            a connected and sorted list of edges.
	 */
	public HorizontalWrapper(List<NodePositionWrapper> nodePosList) {
		super(nodePosList);
	}
	
	@Override
	public long getWidth() {
		long sum = 0;
		for (NodePositionWrapper node : nodeList) {
			sum += node.getWidth();
		}
		return sum;
	}
	
	@Override
	long getXStart() {
		return getFirst().getXStart();
	}
	
	@Override
	long getXEnd() {
		return getLast().getXEnd();
	}
}
