package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	long basePairCount = -1;
	int width = -1;
	
	/**
	 * Construct an instance of {@link SpaceWrapper}.
	 * 
	 * <p>
	 * As additional precondition to the super class its preconditions, the
	 * nodes within the {@code nodeList} should not be able to be wrapped into a
	 * {@link VerticalWrapper} or {@link HorizontalWrapper}.
	 */
	public SpaceWrapper(List<Wrapper> nodePosList) {
		super(nodePosList);
	}
	
	@Override
	public long getBasePairCount() {
		if (basePairCount == -1) {
			Map<String, Long> widthMap = new HashMap<>();
			for (Wrapper node : this.getNodeList()) {
				long width = node.getBasePairCount();
				for (Genome genome : node.getGenome()) {
					Long value = widthMap.get(genome.getIdentifier());
					if (value == null) {
						value = width;
					} else {
						value += width;
					}
					widthMap.put(genome.getIdentifier(), value);
				}
			}
			basePairCount = widthMap.values().stream().max(Long::compare).get();
		}
		return basePairCount;
	}
	
	@Override
	public int getWidth() {
		if (width == -1) {
			width = 0;
			Map<String, Integer> widthMap = new HashMap<>();
			for (Wrapper node : this.getNodeList()) {
				int nodeWidth = node.getWidth();
				for (Genome genome : node.getGenome()) {
					Integer value = widthMap.get(genome.getIdentifier());
					if (value == null) {
						value = nodeWidth;
					} else {
						value += nodeWidth;
					}
					widthMap.put(genome.getIdentifier(), value);
				}
			}
			width = widthMap.values().stream().max(Integer::compare).get();
		}
		return width;
	}
	
	@Override
	public Set<Genome> getGenome() {
		return this.getFirst().getGenome();
	}
	
	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public String getIdString() {
		return "S" + super.getIdString();
	}
}
