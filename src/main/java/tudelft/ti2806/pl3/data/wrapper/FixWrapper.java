package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

public class FixWrapper extends Wrapper {
	
	Set<Genome> genome;
	long pos;
	
	public FixWrapper(long pos) {
		this.pos = pos;
	}
	
	@Override
	public long getXStart() {
		return pos;
	}
	
	@Override
	public long getXEnd() {
		return pos;
	}
	
	@Override
	public long getWidth() {
		return pos;
	}
	
	@Override
	public String getIdString() {
		return "[FIX" + pos + "]";
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
	
}
