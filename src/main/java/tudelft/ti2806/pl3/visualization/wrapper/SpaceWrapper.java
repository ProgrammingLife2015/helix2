package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Some node groups can't be wrapped inside a {@link VerticalWrapper} or
 * {@link HorizontalWrapper}. The {@link SpaceWrapper} wraps a group of nodes
 * where all nodes end and start at some point on the same nodes.
 * 
 * <p>
 * An example of a group of nodes which can be wrapped inside a
 * {@link SpaceWrapper} is the following graph:<br>
 * leftGraph - A<br>
 * A - B<br>
 * A - C<br>
 * B - C<br>
 * C - rightGraph<br>
 * With left- and rightGraph as an continuing graph.
 * 
 * <p>
 * These nodes can't and may not be able to be combined into a
 * {@link VerticalWrapper} or {@link HorizontalWrapper}, but all nodes end and
 * start on the same node at some point, because all nodes start in leftGraph
 * and all nodes end in rightGraph.
 * 
 * @author Sam Smulders
 *
 */
public class SpaceWrapper extends CombineWrapper {
	/**
	 * Construct an instance of {@link SpaceWrapper}.
	 * 
	 * <p>
	 * As additional precondition to the super class its preconditions, the nodes
	 * within the {@code nodeList} should not be able to be wrapped into a
	 * {@link VerticalWrapper} or {@link HorizontalWrapper}.
	 */
	public SpaceWrapper(List<NodeWrapper> nodePosList, boolean collapsed) {
		super(nodePosList, collapsed);
	}
	
	@Override
	public long getXStart() {
		long min = Long.MAX_VALUE;
		for (NodeWrapper node : nodeList) {
			min = Math.min(node.getXStart(), min);
		}
		return min;
	}
	
	@Override
	public long getXEnd() {
		long max = Long.MIN_VALUE;
		for (NodeWrapper node : nodeList) {
			max = Math.max(node.getXEnd(), max);
		}
		return max;
	}
	
	@Override
	public long getWidth() {
		return getXEnd() - getXStart();
	}
	
	@Override
	public List<Genome> getGenome() {
		List<Genome> genomes = new ArrayList<Genome>();
		for (NodeWrapper node : nodeList) {
			for (Genome genome : node.getGenome()) {
				if (!genomes.contains(genome)) {
					genomes.add(genome);
				}
			}
		}
		return genomes;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, NodeWrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public SpaceWrapper deepClone() {
		List<NodeWrapper> clonedList = new ArrayList<>(nodeList.size());
		nodeList.forEach(s -> clonedList.add(s.deepClone()));
		return new SpaceWrapper(clonedList,isCollapsed());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new SpaceWrapper(nodeList,isCollapsed());
	}
}
