package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

public class SingleWrapper extends Wrapper {
	
	private Wrapper target;
	
	public SingleWrapper(Wrapper target) {
		this.target = target;
	}
	
	@Override
	public long getBasePairCount() {
		return target.getBasePairCount();
	}
	
	@Override
	public String getIdString() {
		return target.getIdString();
	}
	
	@Override
	public Set<Genome> getGenome() {
		return target.getGenome();
	}
	
	public Wrapper getNode() {
		return target;
	}
	
	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public void collectDataNodes(List<DataNode> list) {
		target.collectDataNodes(list);
	}
	
	@Override
	public void calculateX() {
		this.x = this.getNode().getX();
	}

	@Override
	public int getWidth() {
		return this.getNode().getWidth();
	}
}
