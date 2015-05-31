package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

class TestWrapper extends Wrapper {
	@Override
	public long getBasePairCount() {
		return 0;
	}
	
	@Override
	public int getWidth() {
		return 0;
	}
	
	@Override
	public void calculateX() {
	}
	
	@Override
	public String getIdString() {
		return null;
	}
	
	@Override
	public Set<Genome> getGenome() {
		return null;
	}
	
	@Override
	public void calculate(WrapperOperation wrapperSequencer, Wrapper container) {
	}
	
	@Override
	public void collectDataNodes(List<DataNode> list) {
	}
}
