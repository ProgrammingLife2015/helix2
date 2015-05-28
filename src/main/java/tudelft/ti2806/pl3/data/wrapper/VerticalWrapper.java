package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerticalWrapper extends CombineWrapper {

	public VerticalWrapper(List<Wrapper> nodePosList, boolean collapsed) {
		super(nodePosList, collapsed);
	}

	public VerticalWrapper(List<Wrapper> nodePosList) {
		super(nodePosList);
	}
	
	@Override
	public long getBasePairCount() {
		long max = Integer.MIN_VALUE;
		for (Wrapper node : nodeList) {
			max = Math.max(max, node.getBasePairCount());
		}
		return max;
	}
	
	@Override
	public Set<Genome> getGenome() {
		Set<Genome> genome = new HashSet<>();
		for (Wrapper node : nodeList) {
			genome.addAll(node.getGenome());
		}
		return genome;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public String getIdString() {
		return "V" + super.getIdString();
	}

	@Override
	public int getWidth() {
		return this.getNodeList().stream().map(Wrapper::getWidth).max(Integer::compare).get();
	}
}
