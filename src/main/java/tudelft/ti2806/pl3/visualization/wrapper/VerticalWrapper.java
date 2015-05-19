package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerticalWrapper extends CombineWrapper {

	private float ySpace;
	
	public VerticalWrapper(List<NodeWrapper> nodePosList, boolean collapsed) {
		super(nodePosList, collapsed);
	}

	public VerticalWrapper(List<NodeWrapper> nodePosList) {
		super(nodePosList);
	}
	
	@Override
	public long getWidth() {
		long max = Integer.MIN_VALUE;
		for (NodeWrapper node : nodeList) {
			max = Math.max(max, node.getWidth());
		}
		return max;
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
	public Set<Genome> getGenome() {
		Set<Genome> genome = new HashSet<Genome>();
		for (NodeWrapper node : nodeList) {
			genome.addAll(node.getGenome());
		}
		return genome;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, NodeWrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public VerticalWrapper deepClone() {
		List<NodeWrapper> clonedList = new ArrayList<>(nodeList.size());
		nodeList.forEach(s -> clonedList.add(s.deepClone()));
		return new VerticalWrapper(clonedList,isCollapsed());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new VerticalWrapper(nodeList,isCollapsed());
	}
}
