package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

public class FixWrapper extends Wrapper {
	
	Set<Genome> genome;
	
	@Override
	public long getWidth() {
		return 0;
	}
	
	@Override
	public String getIdString() {
		return "[FIX]";
	}
	
	@Override
	public Set<Genome> getGenome() {
		return genome;
	}
	
	@Override
	public void calculate(WrapperOperation operation, Wrapper container) {
		operation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {

	}

	public void setGenome(Set<Genome> genome) {
		this.genome = genome;
	}

	@Override
	public void calculateX() {
		this.x = this.getPreviousNodesCount();
	}
	
}
