package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

/**
 * Some node groups can't be wrapped inside a {@link VerticalWrapper} or
 * {@link HorizontalWrapper}. The {@link SpaceWrapper} wraps a group of nodes
 * where all nodes end and start at some point on the same nodes and both, the
 * start and end node, contain the same set of genome.
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
	 * As additional precondition to the super class its preconditions, the
	 * nodes within the {@code nodeList} should not be able to be wrapped into a
	 * {@link VerticalWrapper} or {@link HorizontalWrapper}.
	 */
	public SpaceWrapper(List<Wrapper> nodePosList, boolean collapsed) {
		super(nodePosList, collapsed);
	}
	
	public SpaceWrapper(List<Wrapper> nodePosList) {
		super(nodePosList);
	}
	
	@Override
	public long getXStart() {
		long min = Long.MAX_VALUE;
		for (Wrapper node : nodeList) {
			min = Math.min(node.getXStart(), min);
		}
		return min;
	}
	
	@Override
	public long getXEnd() {
		long max = Long.MIN_VALUE;
		for (Wrapper node : nodeList) {
			max = Math.max(node.getXEnd(), max);
		}
		return max;
	}
	
	@Override
	public long getWidth() {
		return getXEnd() - getXStart();
	}
	
	@Override
	public Set<Genome> getGenome() {
		return this.getFirst().getGenome();
	}
	
	@Override
	public void calculate(WrapperOperation wrapperOperation,
			Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public String getIdString() {
		return "S" + super.getIdString();
	}
}
